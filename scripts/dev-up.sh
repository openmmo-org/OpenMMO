#!/usr/bin/env bash
# Boots the full local OpenMMO stack: Postgres (docker) + login server (:2106) + game server (:7777).
# Idempotent — safe to re-run. Run from anywhere; paths are resolved relative to this script.
#
# Usage: scripts/dev-up.sh [--kill-conflicting] [--stop]
#
# --kill-conflicting  If 2106/7777/7778 are held by another process (e.g. an orphaned
#                      ByteDex proxy or a stale gradlew run), kill it before starting.
#                      Without this flag the script just reports the conflict and exits.
# --stop               Stop the login/game gradlew processes and the Postgres containers.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
TOOLCHAINS_DIR="$(cd "$REPO_DIR/../toolchains" 2>/dev/null && pwd || true)"
LOG_DIR="$REPO_DIR/.devlogs"
mkdir -p "$LOG_DIR"

KILL_CONFLICTING=0
STOP=0
for arg in "$@"; do
  case "$arg" in
    --kill-conflicting) KILL_CONFLICTING=1 ;;
    --stop) STOP=1 ;;
    *) echo "unknown arg: $arg" >&2; exit 1 ;;
  esac
done

find_jdk25() {
  if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    echo "$JAVA_HOME"
    return
  fi
  if [ -n "$TOOLCHAINS_DIR" ]; then
    local candidate
    candidate=$(find "$TOOLCHAINS_DIR/jdk25" -maxdepth 1 -type d -name 'jdk-25*' 2>/dev/null | head -1)
    if [ -n "$candidate" ]; then
      echo "$candidate"
      return
    fi
  fi
  echo "ERROR: no JDK 25 found. Set JAVA_HOME, or place a portable JDK 25 at <PokeBROMMO>/toolchains/jdk25/" >&2
  exit 1
}

port_owner_pid() {
  netstat -ano 2>/dev/null | grep -E ":$1[[:space:]].*LISTENING" | awk '{print $NF}' | head -1
}

if [ "$STOP" = "1" ]; then
  echo "Stopping login/game servers (via gradlew --stop) and Postgres containers..."
  JAVA_HOME="$(find_jdk25)"
  export JAVA_HOME
  (cd "$REPO_DIR" && ./gradlew --stop) || true
  (cd "$REPO_DIR" && docker compose -p openmmo --env-file .env down) || true
  echo "Stopped."
  exit 0
fi

echo "== 1/5: resolving JDK 25 =="
JAVA_HOME="$(find_jdk25)"
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
echo "JAVA_HOME=$JAVA_HOME"

echo "== 2/5: checking ports 2106/7777/7778 =="
for port in 2106 7777 7778; do
  pid=$(port_owner_pid "$port" || true)
  if [ -n "${pid:-}" ]; then
    if [ "$KILL_CONFLICTING" = "1" ]; then
      echo "  port $port held by PID $pid — killing (--kill-conflicting)"
      powershell -NoProfile -Command "Stop-Process -Id $pid -Force" 2>/dev/null || taskkill //F //PID "$pid" || true
    else
      echo "  ERROR: port $port is held by PID $pid. This is usually an orphaned ByteDex proxy or a" >&2
      echo "  stale gradlew :run process — see CONTRIBUTING.md's known-bumps section. Re-run with" >&2
      echo "  --kill-conflicting to auto-kill it, or stop it yourself first." >&2
      exit 1
    fi
  fi
done

echo "== 3/5: starting Postgres (docker compose) =="
cd "$REPO_DIR"
if [ ! -f .env ]; then
  echo "  ERROR: .env missing. cp .env.example .env and fill in values first." >&2
  exit 1
fi
docker compose -p openmmo --env-file .env up -d
for db in login-db game-db; do
  echo -n "  waiting for $db to be healthy..."
  for _ in $(seq 1 30); do
    status=$(docker inspect --format '{{.State.Health.Status}}' "$db" 2>/dev/null || echo "unknown")
    if [ "$status" = "healthy" ] || [ "$status" = "unknown" ]; then break; fi
    sleep 1
  done
  echo " ok"
done

echo "== 4/5: starting login + game servers (background, logs in .devlogs/) =="
nohup ./gradlew :server.login:run -x spotlessCheck > "$LOG_DIR/login-server.log" 2>&1 &
disown
nohup ./gradlew :server.game:run -x spotlessCheck > "$LOG_DIR/game-server.log" 2>&1 &
disown

echo "== 5/5: waiting for both servers to report ready (up to 5 min; first run clones pokeemerald for maps, ~1-2min) =="
deadline=$((SECONDS + 300))
login_ready=0
game_ready=0
while [ $SECONDS -lt $deadline ]; do
  if [ "$login_ready" = "0" ] && grep -qi "listening on" "$LOG_DIR/login-server.log" 2>/dev/null; then
    login_ready=1
    echo "  login server: READY (:2106)"
  fi
  if [ "$game_ready" = "0" ] && grep -qi "listening on" "$LOG_DIR/game-server.log" 2>/dev/null; then
    game_ready=1
    echo "  game server: READY (:7777)"
  fi
  if grep -qi "FAILURE\|BUILD FAILED" "$LOG_DIR/login-server.log" "$LOG_DIR/game-server.log" 2>/dev/null; then
    echo "  ERROR: a server build failed. Check .devlogs/login-server.log / game-server.log" >&2
    exit 1
  fi
  [ "$login_ready" = "1" ] && [ "$game_ready" = "1" ] && break
  sleep 2
done

if [ "$login_ready" = "1" ] && [ "$game_ready" = "1" ]; then
  echo ""
  echo "Stack is up. login=admin/admin, connect the patched client at 127.0.0.1."
  echo "Logs: $LOG_DIR/login-server.log , $LOG_DIR/game-server.log"
else
  echo "TIMED OUT waiting for servers. Check .devlogs/*.log for progress/errors." >&2
  exit 1
fi
