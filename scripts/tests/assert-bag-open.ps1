# Concrete server-side assertion recipe for the bag-open handler (PR #15 --
# the original bag-crash fix that started this whole project). See
# assert-bag-open.sh for full rationale -- same semantics, PowerShell twin.
#
# Usage: .\scripts\tests\assert-bag-open.ps1 [-Timeout SECS]

param(
    [int]$Timeout = 60
)

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$Assert = Join-Path (Split-Path -Parent $ScriptDir) "assert.ps1"

$pattern = "Sending BagInventory for character"

Write-Host "== bag-open (0x70 BagInventory) assertion =="
$mark = & $Assert mark game
Write-Host "  checkpoint: game log line $mark"
Write-Host "  now perform the action (open the bag) -- polling up to ${Timeout}s..."

& $Assert wait-for -Since $mark -Log game -Pattern $pattern -Timeout $Timeout
if ($LASTEXITCODE -eq 0) {
    & $Assert check-clean -Since $mark -Log game
    exit $LASTEXITCODE
} else {
    Write-Error "FAIL: no BagInventory packet was sent -- either the action wasn't driven within the timeout, or the handler didn't fire. Check .devlogs\game-server.log around checkpoint $mark."
    exit 1
}
