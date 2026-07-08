# Concrete server-side assertion recipe for the T0 player-state + party
# handler (merged in PR #4: LoginService.kt's RequestPlayer path, backed by
# CharacterStore + PartyPokemonMapper). See assert-t0-player-party.sh for
# full rationale -- same semantics, PowerShell twin.
#
# Usage: .\scripts\tests\assert-t0-player-party.ps1 [-Timeout SECS] [-Character NAME]

param(
    [int]$Timeout = 60,
    [string]$Character
)

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$Assert = Join-Path (Split-Path -Parent $ScriptDir) "assert.ps1"

$pattern = "Sending LoadEntity for character"
if ($Character) { $pattern = "Sending LoadEntity for character '$Character'" }

Write-Host "== T0 player-state + party assertion =="
$mark = & $Assert mark game
Write-Host "  checkpoint: game log line $mark"
Write-Host "  now perform the action (character select / RequestPlayer) -- polling up to ${Timeout}s..."

& $Assert wait-for -Since $mark -Log game -Pattern $pattern -Timeout $Timeout
if ($LASTEXITCODE -eq 0) {
    & $Assert check-clean -Since $mark -Log game
    exit $LASTEXITCODE
} else {
    Write-Error "FAIL: no LoadEntity packet was sent -- either the action wasn't driven within the timeout, or the handler didn't fire. Check .devlogs\game-server.log around checkpoint $mark."
    exit 1
}
