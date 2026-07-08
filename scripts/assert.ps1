# Server-side assertion helpers for the integration test harness.
# Pairs with PokeAgent-Bot's client-side "did it crash" drive: they perform an
# action against the burner client, we assert what the SERVER did about it.
# See scripts/assert.sh for full usage docs -- same subcommands, same semantics.
#
# Usage: .\scripts\assert.ps1 <mark|check-clean|wait-for|db-query> [args...]
# Note: db-query uses -Database (not -Db) -- PowerShell reserves -Db as an
# ambiguous prefix of the built-in -Debug common parameter.

param(
    [Parameter(Mandatory = $true, Position = 0)]
    [ValidateSet("mark", "check-clean", "wait-for", "db-query")]
    [string]$Command,

    [string]$Since,
    [Parameter(Position = 1)]
    [string]$Log = "game",
    [string]$Pattern,
    [int]$Timeout = 30,
    [switch]$Strict,
    [string]$Container,
    [string]$Database,
    [string]$User,
    [string]$Sql
)

$ErrorActionPreference = "Continue"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RepoDir = Split-Path -Parent $ScriptDir
$LogDir = Join-Path $RepoDir ".devlogs"

function Get-LogPath([string]$which) {
    switch ($which) {
        "login" { return Join-Path $LogDir "login-server.log" }
        "game" { return Join-Path $LogDir "game-server.log" }
        default { Write-Error "unknown log '$which' (expected login|game)"; exit 1 }
    }
}

switch ($Command) {
    "mark" {
        $path = Get-LogPath $Log
        if (-not (Test-Path $path)) { Write-Output "0"; exit 0 }
        $count = (Get-Content $path | Measure-Object -Line).Lines
        Write-Output $count
        exit 0
    }
    "check-clean" {
        if (-not $Since) { Write-Error "-Since is required (use 'mark' to get a checkpoint)"; exit 1 }
        $path = Get-LogPath $Log
        if (-not (Test-Path $path)) { Write-Error "log not found: $path"; exit 1 }
        $pattern = "Exception|ERROR|FATAL|Caused by:"
        if ($Strict) { $pattern = "$pattern|WARN" }
        $lines = Get-Content $path
        $sinceInt = [int]$Since
        $newLines = if ($lines.Count -gt $sinceInt) { $lines[$sinceInt..($lines.Count - 1)] } else { @() }
        $hits = $newLines | Select-String -Pattern $pattern
        if ($hits) {
            Write-Error "FAIL: found crash-like lines in $Log log since checkpoint $Since"
            $hits | Select-Object -First 10 | ForEach-Object { Write-Host $_.Line }
            exit 1
        }
        Write-Output "PASS: no crash-like lines in $Log log since checkpoint $Since"
        exit 0
    }
    "wait-for" {
        if (-not $Since) { Write-Error "-Since is required"; exit 1 }
        if (-not $Pattern) { Write-Error "-Pattern is required"; exit 1 }
        $path = Get-LogPath $Log
        $sinceInt = [int]$Since
        $deadline = (Get-Date).AddSeconds($Timeout)
        while ((Get-Date) -lt $deadline) {
            if (Test-Path $path) {
                $lines = Get-Content $path
                if ($lines.Count -gt $sinceInt) {
                    $newLines = $lines[$sinceInt..($lines.Count - 1)]
                    $match = $newLines | Select-String -Pattern $Pattern | Select-Object -First 1
                    if ($match) {
                        Write-Output "PASS: matched '$Pattern' in $Log log: $($match.Line)"
                        exit 0
                    }
                }
            }
            Start-Sleep -Seconds 1
        }
        Write-Error "FAIL: '$Pattern' did not appear in $Log log within ${Timeout}s (since checkpoint $Since)"
        exit 1
    }
    "db-query" {
        if (-not $Container -or -not $Database -or -not $User -or -not $Sql) {
            Write-Error "-Container -Database -User -Sql are all required"
            exit 1
        }
        # This is a test-assertion helper, not a general query tool -- reject
        # anything but a read. Belt-and-suspenders: also run inside an
        # explicit read-only transaction, so even a keyword this regex misses
        # still can't write (Postgres rejects writes in a READ ONLY
        # transaction at the engine level).
        if ($Sql -imatch '\b(insert|update|delete|drop|alter|truncate|grant|revoke|create|copy|vacuum|call|do)\b') {
            Write-Error "db-query is read-only; SQL looks like a write: $Sql"
            exit 1
        }
        docker exec $Container psql -U $User -d $Database -At -q -c "BEGIN READ ONLY; $Sql; COMMIT;"
        exit $LASTEXITCODE
    }
}
