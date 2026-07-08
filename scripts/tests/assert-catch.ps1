# Concrete server-side assertion recipe for the catch handler (PR #8: 0x14
# SinglePokemonAddPacket + addCaughtPokemon; BattleService's catch-outcome
# path). See assert-catch.sh for full rationale -- same semantics,
# PowerShell twin.
#
# Usage: .\scripts\tests\assert-catch.ps1 [-Timeout SECS]

param(
    [int]$Timeout = 90
)

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$Assert = Join-Path (Split-Path -Parent $ScriptDir) "assert.ps1"

$pattern = "Caught species .* for character"

Write-Host "== catch (0x14 SinglePokemonAdd) assertion =="
$mark = & $Assert mark game
Write-Host "  checkpoint: game log line $mark"
Write-Host "  now perform the action (start a wild battle, throw a ball, catch it) -- polling up to ${Timeout}s..."

& $Assert wait-for -Since $mark -Log game -Pattern $pattern -Timeout $Timeout
if ($LASTEXITCODE -eq 0) {
    & $Assert check-clean -Since $mark -Log game
    exit $LASTEXITCODE
} else {
    Write-Error "FAIL: no catch was recorded -- either the action wasn't driven within the timeout, or the handler didn't fire. Check .devlogs\game-server.log around checkpoint $mark."
    exit 1
}
