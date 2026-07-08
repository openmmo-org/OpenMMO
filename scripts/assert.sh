#!/usr/bin/env bash
# Server-side assertion helpers for the integration test harness.
# Pairs with PokeAgent-Bot's client-side "did it crash" drive: they perform an
# action against the burner client, we assert what the SERVER did about it.
#
# Usage:
#   scripts/assert.sh mark [login|game]
#       Print a line-count checkpoint for the given log (default: game).
#       Take a mark BEFORE the driven action, pass it to the checks below.
#
#   scripts/assert.sh check-clean --since N [--log login|game]
#       Fail (exit 1) if any new line since checkpoint N looks like a crash:
#       Exception / ERROR / FATAL / "Caused by:". WARN lines are not failures
#       (the codebase uses log.warn for expected bad-input cases, e.g. "no
#       session for channel") -- pass --strict to fail on WARN too.
#
#   scripts/assert.sh wait-for --since N --pattern REGEX [--log login|game] [--timeout SECS]
#       Poll (default 30s timeout) for a log line matching REGEX to appear
#       after checkpoint N. This is the "packet X was handled" proxy --
#       OpenMMO's services log a descriptive line per handled packet (see
#       LoginService.kt, DialogService.kt, etc.), so match on that until a
#       real protocol-level test client exists.
#
#   scripts/assert.sh db-query --container NAME --db NAME --user NAME --sql SQL
#       Run a read-only query against a Postgres container (login-db/game-db)
#       and print the result. For "state persisted" checks once handlers
#       actually write to Postgres (nothing does yet as of 2026-07-08 --
#       accounts are seeded in-memory, no Pokémon system persists state).
#
# All subcommands exit 0 on pass/success, 1 on fail/error, with a one-line
# reason on stderr for failures.

set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
LOG_DIR="$REPO_DIR/.devlogs"

log_path() {
  case "$1" in
    login) echo "$LOG_DIR/login-server.log" ;;
    game) echo "$LOG_DIR/game-server.log" ;;
    *) echo "ERROR: unknown log '$1' (expected login|game)" >&2; exit 1 ;;
  esac
}

cmd_mark() {
  local which="${1:-game}"
  local path
  path="$(log_path "$which")"
  if [ ! -f "$path" ]; then
    echo "0"
    return
  fi
  wc -l < "$path" | tr -d ' '
}

cmd_check_clean() {
  local since="" which="game" strict=0
  while [ $# -gt 0 ]; do
    case "$1" in
      --since) since="$2"; shift 2 ;;
      --log) which="$2"; shift 2 ;;
      --strict) strict=1; shift ;;
      *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
    esac
  done
  [ -n "$since" ] || { echo "ERROR: --since is required (use 'mark' to get a checkpoint)" >&2; exit 1; }
  local path
  path="$(log_path "$which")"
  [ -f "$path" ] || { echo "ERROR: log not found: $path" >&2; exit 1; }

  local pattern="Exception|ERROR|FATAL|Caused by:"
  [ "$strict" = "1" ] && pattern="$pattern|WARN"

  local hits
  hits=$(tail -n +"$((since + 1))" "$path" | grep -nE "$pattern" || true)
  if [ -n "$hits" ]; then
    echo "FAIL: found crash-like lines in $which log since checkpoint $since:" >&2
    echo "$hits" | head -10 >&2
    exit 1
  fi
  echo "PASS: no crash-like lines in $which log since checkpoint $since"
}

cmd_wait_for() {
  local since="" which="game" pattern="" timeout=30
  while [ $# -gt 0 ]; do
    case "$1" in
      --since) since="$2"; shift 2 ;;
      --log) which="$2"; shift 2 ;;
      --pattern) pattern="$2"; shift 2 ;;
      --timeout) timeout="$2"; shift 2 ;;
      *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
    esac
  done
  [ -n "$since" ] || { echo "ERROR: --since is required" >&2; exit 1; }
  [ -n "$pattern" ] || { echo "ERROR: --pattern is required" >&2; exit 1; }
  local path
  path="$(log_path "$which")"

  local deadline=$((SECONDS + timeout))
  while [ $SECONDS -lt $deadline ]; do
    local match
    match=$(tail -n +"$((since + 1))" "$path" 2>/dev/null | grep -nE "$pattern" | head -1 || true)
    if [ -n "$match" ]; then
      echo "PASS: matched '$pattern' in $which log: $match"
      return 0
    fi
    sleep 1
  done
  echo "FAIL: '$pattern' did not appear in $which log within ${timeout}s (since checkpoint $since)" >&2
  exit 1
}

cmd_db_query() {
  local container="" db="" user="" sql=""
  while [ $# -gt 0 ]; do
    case "$1" in
      --container) container="$2"; shift 2 ;;
      --db) db="$2"; shift 2 ;;
      --user) user="$2"; shift 2 ;;
      --sql) sql="$2"; shift 2 ;;
      *) echo "ERROR: unknown arg $1" >&2; exit 1 ;;
    esac
  done
  [ -n "$container" ] && [ -n "$db" ] && [ -n "$user" ] && [ -n "$sql" ] || {
    echo "ERROR: --container --db --user --sql are all required" >&2
    exit 1
  }
  # This is a test-assertion helper, not a general query tool -- reject
  # anything but a read. Belt-and-suspenders: also run inside an explicit
  # read-only transaction, so even a keyword this regex misses still can't
  # write (Postgres rejects writes in a READ ONLY transaction at the engine
  # level).
  if echo "$sql" | grep -qiE '\b(insert|update|delete|drop|alter|truncate|grant|revoke|create|copy|vacuum|call|do)\b'; then
    echo "ERROR: db-query is read-only; SQL looks like a write: $sql" >&2
    exit 1
  fi
  docker exec "$container" psql -U "$user" -d "$db" -At -q -c "BEGIN READ ONLY; $sql; COMMIT;"
}

case "${1:-}" in
  mark) shift; cmd_mark "$@" ;;
  check-clean) shift; cmd_check_clean "$@" ;;
  wait-for) shift; cmd_wait_for "$@" ;;
  db-query) shift; cmd_db_query "$@" ;;
  *)
    echo "Usage: $0 {mark|check-clean|wait-for|db-query} [args...]" >&2
    echo "See the header comment in this file for full usage." >&2
    exit 1
    ;;
esac
