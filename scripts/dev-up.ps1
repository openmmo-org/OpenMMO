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

# NOTE: deliberately NOT setting $ErrorActionPreference = "Stop" globally.
# Native tools (docker, docker compose) routinely write normal progress/status
# text to stderr; under strict Stop preference PowerShell 5.1 promotes that
# into a terminating NativeCommandError even on success (bit us once already
# -- docker compose's "Network ... Creating" progress line killed the script
# mid-boot). Cmdlet-level checks below use explicit -ErrorAction / $LASTEXITCODE
# instead, and every failure path already calls `exit 1` explicitly.
$ErrorActionPreference = "Continue"

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

function Find-GradleUserHome {
    # Don't override a deliberately-set GRADLE_USER_HOME.
    if ($env:GRADLE_USER_HOME) {
        return $env:GRADLE_USER_HOME
    }
    # Relies on a persisted Windows user-level env var, which does NOT
    # propagate into already-running shells/sessions (this bit us for real
    # -- a session started before the var was set silently kept using the
    # default ~\.gradle, re-triggering the KSP cross-drive bug). Resolve it
    # explicitly here instead of trusting inheritance.
    $candidate = Join-Path $ToolchainsDir "gradle-home"
    if (Test-Path $candidate) {
        return $candidate
    }
    return $null
}

if ($Stop) {
    Write-Host "Stopping login/game servers (via gradlew --stop) and Postgres containers..."
    $env:JAVA_HOME = Find-Jdk25
    $gradleUserHome = Find-GradleUserHome
    if ($gradleUserHome) { $env:GRADLE_USER_HOME = $gradleUserHome }
    Push-Location $RepoDir
    try { & .\gradlew.bat --stop } catch {}
    # Direct invocation, not cmd /c -- see the matching comment at the "up -d"
    # call below for why: cmd.exe nesting silently swallows output/exit code
    # on this box and can hang the whole script. $ErrorActionPreference is
    # already "Continue" so a direct call handles stderr chatter fine.
    & docker compose -p openmmo --env-file .env down 2>&1 | ForEach-Object { Write-Host $_ }
    Pop-Location
    Write-Host "Stopped."
    exit 0
}

Write-Host "== 1/5: resolving JDK 25 =="
$env:JAVA_HOME = Find-Jdk25
$env:PATH = "$($env:JAVA_HOME)\bin;$($env:PATH)"
Write-Host "JAVA_HOME=$($env:JAVA_HOME)"

$gradleUserHome = Find-GradleUserHome
if ($gradleUserHome) {
    $env:GRADLE_USER_HOME = $gradleUserHome
    Write-Host "GRADLE_USER_HOME=$($env:GRADLE_USER_HOME)"
}

# Gradle's daemon registry accumulates one entry per stopped daemon and is
# never auto-pruned. On a box with many agents/worktrees restarting servers
# all day, this grows large enough that starting fresh daemons against it
# stalls past any reasonable timeout with ZERO output (no crash, no
# stacktrace -- just silence). Bit us for real once already. Clear it
# proactively if it's gotten big.
$wrapperProps = Join-Path $RepoDir "gradle\wrapper\gradle-wrapper.properties"
if (Test-Path $wrapperProps) {
    $versionMatch = Select-String -Path $wrapperProps -Pattern 'gradle-([0-9.]+)-bin' | Select-Object -First 1
    if ($versionMatch) {
        $gradleVersion = $versionMatch.Matches[0].Groups[1].Value
        $registry = Join-Path $env:USERPROFILE ".gradle\daemon\$gradleVersion\registry.bin"
        if (Test-Path $registry) {
            Push-Location $RepoDir
            $statusOut = & .\gradlew.bat --status 2>$null
            Pop-Location
            $stoppedCount = ($statusOut | Select-String "STOPPED").Count
            if ($stoppedCount -gt 15) {
                Write-Host "  $stoppedCount stopped daemons registered -- clearing the registry (known stall cause)"
                Remove-Item -Force -ErrorAction SilentlyContinue $registry, "$registry.lock"
            }
        }
    }
}

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
# Direct native invocation, NOT cmd /c: this used to be routed through
# cmd.exe to dodge a PowerShell 5.1 quirk where stderr progress chatter gets
# wrapped as NativeCommandErrors and taints the exit code -- but on this box
# that cmd.exe nesting itself silently swallows all output and $LASTEXITCODE
# (confirmed in isolation: even `cmd /c "echo hello"` returns nothing here),
# and the whole script hung right at this line with zero further output.
# $ErrorActionPreference is already "Continue" (set above) so a direct call
# handles the original stderr-chatter concern fine without the nested-cmd
# stall risk.
$dockerUpOutput = & docker compose -p openmmo --env-file .env up -d 2>&1
$dockerUpExitCode = $LASTEXITCODE
$dockerUpOutput | ForEach-Object { Write-Host $_ }
if ($dockerUpExitCode -ne 0) {
    Write-Error "docker compose up failed (exit $dockerUpExitCode). Check Docker Desktop is running."
    Pop-Location
    exit 1
}
foreach ($db in "login-db", "game-db") {
    Write-Host -NoNewline "  waiting for $db to be healthy..."
    $confirmed = $false
    for ($i = 0; $i -lt 30; $i++) {
        # docker-compose.yml declares no HEALTHCHECK for these services, so
        # `docker inspect --format '{{.State.Health.Status}}'` fails with a
        # template-parsing error (no .State.Health key) instead of returning
        # empty. That's a legitimate, fast "ok, no healthcheck to wait on".
        #
        # Hard per-call timeout: a `docker inspect` call that itself hangs
        # (Docker Desktop/WSL2 hiccup, daemon contention) would otherwise
        # defeat this loop's whole 30-iteration budget by blocking on one
        # iteration indefinitely -- seen in the wild as an unexplained
        # multi-minute stall on one specific container while its sibling
        # passed instantly. CRITICAL: a timed-out job is NOT the same as an
        # empty result -- treating "the probe never came back" as "ok" would
        # defeat the entire point of the check (Codex-Review caught this).
        # Only a job that actually COMPLETED with an empty/healthy result
        # counts as ok; a timeout must retry, never silently pass.
        $job = Start-Job -ScriptBlock {
            param($container)
            & docker inspect --format "{{.State.Health.Status}}" $container 2>$null
        } -ArgumentList $db
        $completedJob = Wait-Job -Job $job -Timeout 5
        if ($completedJob) {
            $status = Receive-Job -Job $job
            Remove-Job -Job $job -Force -ErrorAction SilentlyContinue
            if ($status -eq "healthy" -or -not $status) { $confirmed = $true; break }
        } else {
            Remove-Job -Job $job -Force -ErrorAction SilentlyContinue
            Write-Host -NoNewline " (stall, retry $($i + 1))"
        }
        Start-Sleep -Seconds 1
    }
    if ($confirmed) {
        Write-Host " ok"
    } else {
        Write-Warning "  $db health check never completed cleanly after 30 attempts (Docker inspect kept stalling) -- proceeding anyway, but this is the known intermittent-slowness issue, not a pass"
    }
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
    # Explicit exit 0: PowerShell's default process exit code reflects whether
    # ANY error record was written during the session (e.g. docker's routine
    # stderr chatter), not just whether this script's own logic succeeded.
    exit 0
} else {
    Write-Error "TIMED OUT waiting for servers. Check .devlogs\*.log for progress/errors."
    exit 1
}
