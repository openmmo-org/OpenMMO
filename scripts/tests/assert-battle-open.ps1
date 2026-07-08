# Concrete server-side assertion recipe for the wild-battle-open handler
# (PR #7: validated S2C 0x30 BattleOpenPacket codec; PR #10: BattleService
# wires it into a real wild-encounter flow). See assert-battle-open.sh for
# full rationale -- same semantics, PowerShell twin.
#
# Usage: .\scripts\tests\assert-battle-open.ps1 [-Timeout SECS]

param(
    [int]$Timeout = 60
)

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$Assert = Join-Path (Split-Path -Parent $ScriptDir) "assert.ps1"

$pattern = "Wild battle .* started"

Write-Host "== battle-open (S2C 0x30) assertion =="
$mark = & $Assert mark game
Write-Host "  checkpoint: game log line $mark"
Write-Host "  now perform the action (walk into grass until a wild battle starts) -- polling up to ${Timeout}s..."

& $Assert wait-for -Since $mark -Log game -Pattern $pattern -Timeout $Timeout
if ($LASTEXITCODE -eq 0) {
    & $Assert check-clean -Since $mark -Log game
    exit $LASTEXITCODE
} else {
    Write-Error "FAIL: no wild battle was started -- either the action wasn't driven within the timeout, or the handler didn't fire. Check .devlogs\game-server.log around checkpoint $mark."
    exit 1
}
