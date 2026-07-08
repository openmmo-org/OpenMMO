#!/usr/bin/env bash
# Concrete server-side assertion recipe for the wild-battle-open handler
# (PR #7: validated S2C 0x30 BattleOpenPacket codec; PR #10: BattleService
# wires it into a real wild-encounter flow). Wraps scripts/assert.sh with
# the exact log line that handler emits.
#
# What it asserts:
#   1. The server logged "Wild battle <id> started (<N>v<N>)" -- emitted by
#      BattleService right after sending the S2C 0x30 BattleOpenPacket for a
#      wild encounter. Closest proxy to "packet was emitted" until a
#      protocol-level test client exists.
#   2. No crash/exception was logged in the same window (check-clean).
#
# Usage:
#   scripts/tests/assert-battle-open.sh [--timeout SECS]
#
# Trigger the action (walk into grass until a wild encounter starts) any
# time after starting this script and before its timeout.

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

PATTERN="Wild battle .* started"

echo "== battle-open (S2C 0x30) assertion =="
mark=$("$ASSERT" mark game)
echo "  checkpoint: game log line $mark"
echo "  now perform the action (walk into grass until a wild battle starts) -- polling up to ${TIMEOUT}s..."

if "$ASSERT" wait-for --since "$mark" --log game --pattern "$PATTERN" --timeout "$TIMEOUT"; then
  "$ASSERT" check-clean --since "$mark" --log game
  exit $?
else
  echo "FAIL: no wild battle was started -- either the action wasn't driven within the timeout," >&2
  echo "  or the handler didn't fire. Check .devlogs/game-server.log around checkpoint $mark." >&2
  exit 1
fi
