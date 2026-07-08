# Contributing to the PokeBROMMO OpenMMO fork

This is [TheBagBros/OpenMMO](https://github.com/TheBagBros/OpenMMO), a fork of
[openmmo-org/OpenMMO](https://github.com/openmmo-org/OpenMMO) — the server for
**PokeBROMMO** (frozen real PokéMMO client v31914 + this Kotlin server). Full
project context lives in the parent repo: `docs/CEO-NOTES.md` and
`docs/BROMMO-BUILD-PLAN.md`.

## Workflow: branch-per-system → PR → CEO merges

- One branch per Pokémon system you're implementing (`bag`, `party-storage`,
  `encounters`, `catching`, `battle`, `shops`, `trade`, `gtl`, `breeding`,
  `daycare`, ...). Branch off `master`.
- Open a PR into `master` when the handler compiles, runs, and the target
  action no longer crashes the patched client against your local server.
- `master` is protected: PRs require **1 approving review** before merge.
  The **packet registry** (`protocols.game`) is a shared surface — if your
  PR touches it, flag it clearly so the reviewer checks for opcode clashes.
- Don't force-push shared branches; don't rewrite `master` history.

## Local dev setup

Needs **two JDKs**: JDK 25 for the server, JDK 21 for ByteDex (capture
tooling, separate repo). Portable JDKs for this box live in
`../toolchains/jdk25` and `../toolchains/jdk21` (sibling of this clone, in
the PokeBROMMO project root) — reuse them, don't redownload.

```bash
# from this directory
export JAVA_HOME=../toolchains/jdk25/jdk-25.0.3+9   # adjust for your OS/shell
cp .env.example .env                                 # then edit DB creds/secret
docker compose --env-file .env up -d                  # login-db :20011, game-db :20021
./gradlew :server.login:run -x spotlessCheck          # login  -> 0.0.0.0:2106
./gradlew :server.game:run  -x spotlessCheck           # game   -> 0.0.0.0:7777 (parses maps; first run clones pokeemerald into maps/build/, one-time)
```

Test login: `admin` / `admin` (seeded).

**Known bumps:**
- If Gradle throws `The supplied javaHome seems to be invalid` referencing a
  path that no longer exists, a stale daemon registry is the cause — delete
  `~/.gradle/daemon/<version>/registry.bin` and retry.
- **Port conflict:** ByteDex's proxy also binds `2106`/`7777`/`7778` (it
  MITMs the real PokéMMO connection for packet capture). Stop this server
  before running the ByteDex proxy, and vice versa. Kill orphaned child JVMs
  by PID if a prior run didn't shut down cleanly.
- Pass `-Porg.gradle.java.installations.auto-download=false
  -Porg.gradle.java.installations.paths=<jdk21>,<jdk25>` when a task needs
  toolchain resolution across both JDKs (e.g. building ByteDex alongside this
  repo).

## Ban safety (absolute)

Nothing in this repo or its build should ever be pointed at a real PokéMMO
account/install. Capture tooling (ByteDex, a separate repo) targets the
**burner** install only. Never the main account.

## Packet spec format

Each system's decode→implement handoff is a spec, not prose: **name · opcode
(hex) · direction (C2S/S2C) · byte layout (offset, type, field) · enum/flag
values · notes**, cross-referenced to the matching codec class in
`protocols.game`/`protocols.login` and validated against a real capture
line. See `docs/CEO-NOTES.md` in the parent repo for the full format and
current system-ownership roster.
