#!/usr/bin/env bash
# Jar members are listed because a key has only ever travelled inside one, never loose.
set -euo pipefail

found=$(
  find server.login/build/install server.game/build/install -type f
  find server.login/build/install server.game/build/install -name '*.jar' -exec unzip -Z1 {} \;
  for archive in server.login/build/distributions/*.tar server.game/build/distributions/*.tar; do
    if [ -f "$archive" ]; then tar -tf "$archive"; fi
  done
)

if grep -q '\.pem$' <<<"$found"; then
  echo "::error::a published artifact bundles a key"
  grep '\.pem$' <<<"$found"
  exit 1
fi
echo "No keys in $(grep -c . <<<"$found") packaged entries."
