#!/usr/bin/env bash
# Concrete server-side assertion recipe for the bag-open handler (PR #15 --
# the original bag-crash fix that started this whole project). Wraps
# scripts/assert.sh with the exact log line that handler emits.
#
# What it asserts:
#   1. The server logged "Sending BagInventory for character <id>" --
#      emitted by BagService.onBagOpen right after sending both
#      BagInventoryPacket (0x70) responses (main + small container).
#      Closest proxy to "packet was emitted" until a protocol-level test
#      client exists. (This log line was added alongside this recipe --
#      BagService previously only logged its warn/error paths.)
#   2. No crash/exception was logged in the same window (check-clean).
#
# Note: container 0x0001 (the larger, still-undecoded entry shape) is
# intentionally not emitted yet -- this only confirms the two containers
# BagService currently sends, not full bag coverage.
#
# Usage:
#   scripts/tests/assert-bag-open.sh [--timeout SECS]
#
# Trigger the action (open the bag) any time after starting this script
# and before its timeout.

set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ASSERT="$SCRIPT_DIR/../assert.sh"

TIMEOUT=60
while [ $# -gt 0 ]; do
  case "$1" in
    --timeout) TIMEOUT="$2"; shift 2 ;;
    *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
  esac
done

PATTERN="Sending BagInventory for character"

echo "== bag-open (0x70 BagInventory) assertion =="
mark=$("$ASSERT" mark game)
echo "  checkpoint: game log line $mark"
echo "  now perform the action (open the bag) -- polling up to ${TIMEOUT}s..."

if "$ASSERT" wait-for --since "$mark" --log game --pattern "$PATTERN" --timeout "$TIMEOUT"; then
  "$ASSERT" check-clean --since "$mark" --log game
  exit $?
else
  echo "FAIL: no BagInventory packet was sent -- either the action wasn't driven within the timeout," >&2
  echo "  or the handler didn't fire. Check .devlogs/game-server.log around checkpoint $mark." >&2
  exit 1
fi
