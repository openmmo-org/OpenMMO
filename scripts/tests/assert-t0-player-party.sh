#!/usr/bin/env bash
# Concrete server-side assertion recipe for the T0 player-state + party
# handler (merged in PR #4: LoginService.kt's RequestPlayer path, backed by
# CharacterStore + PartyPokemonMapper). Wraps scripts/assert.sh with the
# exact log line that handler emits, so this can be dropped straight into
# an automated harness once a client-side driver (PokeAgent-Bot's bot, or a
# human) is available to trigger the action -- no further RE needed.
#
# What it asserts:
#   1. The server logged "Sending LoadEntity for character '<name>'" --
#      LoginService.kt sends LoadEntityPacket (player state + party) in
#      response to RequestPlayer. This is the closest proxy to "packet was
#      emitted" until a protocol-level test client exists (see
#      T0PlayerPartyPacketsTest.kt in protocols.game for the packet-level
#      unit test this pairs with).
#   2. No crash/exception was logged in the same window (check-clean).
#
# Usage:
#   scripts/tests/assert-t0-player-party.sh [--timeout SECS] [--character NAME]
#
# Trigger the action (character select / RequestPlayer) any time after
# starting this script and before its timeout -- it polls, it doesn't block
# on you starting first. Default timeout is generous (60s) to leave room
# for a human to click through login by hand.

set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ASSERT="$SCRIPT_DIR/../assert.sh"

TIMEOUT=60
CHARACTER=""
while [ $# -gt 0 ]; do
  case "$1" in
    --timeout) TIMEOUT="$2"; shift 2 ;;
    --character) CHARACTER="$2"; shift 2 ;;
    *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
  esac
done

PATTERN="Sending LoadEntity for character"
[ -n "$CHARACTER" ] && PATTERN="Sending LoadEntity for character '$CHARACTER'"

echo "== T0 player-state + party assertion =="
mark=$("$ASSERT" mark game)
echo "  checkpoint: game log line $mark"
echo "  now perform the action (character select / RequestPlayer) -- polling up to ${TIMEOUT}s..."

if "$ASSERT" wait-for --since "$mark" --log game --pattern "$PATTERN" --timeout "$TIMEOUT"; then
  "$ASSERT" check-clean --since "$mark" --log game
  exit $?
else
  echo "FAIL: no LoadEntity packet was sent -- either the action wasn't driven within the timeout," >&2
  echo "  or the handler didn't fire. Check .devlogs/game-server.log around checkpoint $mark." >&2
  exit 1
fi
