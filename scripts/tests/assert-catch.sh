#!/usr/bin/env bash
# Concrete server-side assertion recipe for the catch handler (PR #8: 0x14
# SinglePokemonAddPacket + addCaughtPokemon; BattleService's catch-outcome
# path). Wraps scripts/assert.sh with the exact log line + a DB check.
#
# What it asserts:
#   1. The server logged "Caught species <id> for character <id>" -- emitted by
#      BattleService when a catch attempt succeeds. Closest proxy to
#      "packet was emitted" (0x14 SinglePokemonAdd) until a protocol-level
#      test client exists.
#   2. No crash/exception was logged in the same window (check-clean).
#
# State-persisted (DB) check is NOT wired here yet -- as of this recipe,
# confirm with whoever owns PokemonPartyService/CharacterStore whether the
# catch path writes to Postgres yet or still holds state in memory only;
# if it does, add a --db-query check here for the new party row.
#
# Usage:
#   scripts/tests/assert-catch.sh [--timeout SECS]
#
# Trigger the action (start a wild battle, throw a ball, catch it) any time
# after starting this script and before its timeout.

set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ASSERT="$SCRIPT_DIR/../assert.sh"

TIMEOUT=90
while [ $# -gt 0 ]; do
  case "$1" in
    --timeout) TIMEOUT="$2"; shift 2 ;;
    *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
  esac
done

PATTERN="Caught species .* for character"

echo "== catch (0x14 SinglePokemonAdd) assertion =="
mark=$("$ASSERT" mark game)
echo "  checkpoint: game log line $mark"
echo "  now perform the action (start a wild battle, throw a ball, catch it) -- polling up to ${TIMEOUT}s..."

if "$ASSERT" wait-for --since "$mark" --log game --pattern "$PATTERN" --timeout "$TIMEOUT"; then
  "$ASSERT" check-clean --since "$mark" --log game
  exit $?
else
  echo "FAIL: no catch was recorded -- either the action wasn't driven within the timeout," >&2
  echo "  or the handler didn't fire. Check .devlogs/game-server.log around checkpoint $mark." >&2
  exit 1
fi
