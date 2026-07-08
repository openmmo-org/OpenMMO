# Boots the full local OpenMMO stack: Postgres (docker) + login server (:2106) + game server (:7777).
# Idempotent -- safe to re-run.
#
# Usage: .\scripts\dev-up.ps1 [-KillConflicting] [-Stop]
#
# -KillConflicting  If 2106/7777/7778 are held by another process (e.g. an orphaned
#                    ByteDex proxy or a stale gradlew run), kill it before starting.
#                    Without this switch the script just reports the conflict and exits.
# -Stop             Stop the login/game gradlew processes and the Postgres containers.

param(
    [switch]$KillConflicting,
    [switch]$Stop
)

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RepoDir = Split-Path -Parent $ScriptDir
$ToolchainsDir = Join-Path (Split-Path -Parent $RepoDir) "toolchains"
$LogDir = Join-Path $RepoDir ".devlogs"
New-Item -ItemType Directory -Force -Path $LogDir | Out-Null

function Find-Jdk25 {
    if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME "bin\java.exe"))) {
        return $env:JAVA_HOME
    }
    $candidate = Get-ChildItem -Path (Join-Path $ToolchainsDir "jdk25") -Directory -Filter "jdk-25*" -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($candidate) {
        return $candidate.FullName
    }
    Write-Error "No JDK 25 found. Set `$env:JAVA_HOME, or place a portable JDK 25 at <PokeBROMMO>\toolchains\jdk25\"
    exit 1
}

function Get-PortOwnerPid([int]$Port) {
    $line = netstat -ano | Select-String -Pattern ":$Port\s.*LISTENING"
    if ($line) {
        return ($line.Line -split '\s+')[-1]
    }
    return $null
}

if ($Stop) {
    Write-Host "Stopping login/game servers (via gradlew --stop) and Postgres containers..."
    $env:JAVA_HOME = Find-Jdk25
    Push-Location $RepoDir
    try { & .\gradlew.bat --stop } catch {}
    try { docker compose --env-file .env down } catch {}
    Pop-Location
    Write-Host "Stopped."
    exit 0
}

Write-Host "== 1/5: resolving JDK 25 =="
$env:JAVA_HOME = Find-Jdk25
$env:PATH = "$($env:JAVA_HOME)\bin;$($env:PATH)"
Write-Host "JAVA_HOME=$($env:JAVA_HOME)"

Write-Host "== 2/5: checking ports 2106/7777/7778 =="
foreach ($port in 2106, 7777, 7778) {
    $pid_ = Get-PortOwnerPid -Port $port
    if ($pid_) {
        if ($KillConflicting) {
            Write-Host "  port $port held by PID $pid_ -- killing (-KillConflicting)"
            Stop-Process -Id $pid_ -Force -ErrorAction SilentlyContinue
        } else {
            Write-Error "port $port is held by PID $pid_. This is usually an orphaned ByteDex proxy or a stale gradlew :run process -- see CONTRIBUTING.md's known-bumps section. Re-run with -KillConflicting to auto-kill it, or stop it yourself first."
            exit 1
        }
    }
}

Write-Host "== 3/5: starting Postgres (docker compose) =="
Push-Location $RepoDir
if (-not (Test-Path ".env")) {
    Write-Error ".env missing. Copy .env.example to .env and fill in values first."
    exit 1
}
docker compose --env-file .env up -d
foreach ($db in "login-db", "game-db") {
    Write-Host -NoNewline "  waiting for $db to be healthy..."
    for ($i = 0; $i -lt 30; $i++) {
        $status = docker inspect --format '{{.State.Health.Status}}' $db 2>$null
        if ($status -eq "healthy" -or -not $status) { break }
        Start-Sleep -Seconds 1
    }
    Write-Host " ok"
}

Write-Host "== 4/5: starting login + game servers (background, logs in .devlogs\) =="
$loginLog = Join-Path $LogDir "login-server.log"
$gameLog = Join-Path $LogDir "game-server.log"
Start-Process -FilePath ".\gradlew.bat" -ArgumentList ":server.login:run", "-x", "spotlessCheck" `
    -RedirectStandardOutput $loginLog -RedirectStandardError "$loginLog.err" -WindowStyle Hidden
Start-Process -FilePath ".\gradlew.bat" -ArgumentList ":server.game:run", "-x", "spotlessCheck" `
    -RedirectStandardOutput $gameLog -RedirectStandardError "$gameLog.err" -WindowStyle Hidden

Write-Host "== 5/5: waiting for both servers to report ready (up to 5 min; first run clones pokeemerald for maps, ~1-2min) =="
$deadline = (Get-Date).AddSeconds(300)
$loginReady = $false
$gameReady = $false
while ((Get-Date) -lt $deadline) {
    if (-not $loginReady -and (Test-Path $loginLog) -and (Select-String -Path $loginLog -Pattern "listening on" -Quiet)) {
        $loginReady = $true
        Write-Host "  login server: READY (:2106)"
    }
    if (-not $gameReady -and (Test-Path $gameLog) -and (Select-String -Path $gameLog -Pattern "listening on" -Quiet)) {
        $gameReady = $true
        Write-Host "  game server: READY (:7777)"
    }
    foreach ($log in @($loginLog, $gameLog, "$loginLog.err", "$gameLog.err")) {
        if ((Test-Path $log) -and (Select-String -Path $log -Pattern "FAILURE|BUILD FAILED" -Quiet)) {
            Write-Error "a server build failed. Check $log"
            exit 1
        }
    }
    if ($loginReady -and $gameReady) { break }
    Start-Sleep -Seconds 2
}
Pop-Location

if ($loginReady -and $gameReady) {
    Write-Host ""
    Write-Host "Stack is up. login=admin/admin, connect the patched client at 127.0.0.1."
    Write-Host "Logs: $loginLog , $gameLog"
} else {
    Write-Error "TIMED OUT waiting for servers. Check .devlogs\*.log for progress/errors."
    exit 1
}
