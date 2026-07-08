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
- `master` is protected: PRs required, direct pushes blocked (enforced for
  everyone, admins included). GitHub's native approval count is 0 — every
  agent here shares one GitHub identity, so self-approval isn't possible;
  the real review gate is process (Codex-Review clears on substance, CEO
  approves + merges). The **packet registry** (`protocols.game`) is a shared
  surface — if your PR touches it, flag it clearly so the reviewer checks
  for opcode clashes.
- Don't force-push shared branches; don't rewrite `master` history.

## IMPORTANT: this box's clones are SHARED, not per-agent

`F:\coding\games\PokeBROMMO\openmmo` is **one working directory shared by
everyone working on this machine** — it is not a personal checkout. Checking
out a different branch here changes what *everyone* sees, and can collide
with someone else's uncommitted work.

**Before switching branches or committing, run `git status` first.** If you
need your own branch checked out while someone else's WIP is sitting in the
shared clone, make yourself an isolated worktree instead of touching the
shared checkout:

```bash
git worktree add ../openmmo-<yourname-or-branch> -b <your-branch>
```

This gives you a separate directory with its own checkout (same `.git`
history, `git worktree list` from any of them shows all of them). Remember
to copy `.env` into it — it's gitignored, so worktrees don't share it.

`F:\coding\games\PokeBROMMO\openmmo-run` is a **dedicated worktree pinned to
`master`**, used for running the live dev servers via `scripts/dev-up.sh` /
`dev-up.ps1` — use it (not the shared main clone) when you just need a
running server to test against, so booting the stack never depends on
whatever branch someone else has checked out.

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

## Integration test harness: server-side assertions

`scripts/assert.sh` (+ `.ps1` twin) is the server-side half of the
integration harness — it pairs with a client-side "did it crash" check
(currently PokeAgent-Bot's burner-driving MCP tools) to verify a handler
actually did the right thing, not just that the client didn't crash.

Four subcommands, all exit 0 on pass / 1 on fail:
- `mark [login|game]` — print a log-line checkpoint. Take one *before* the
  driven action.
- `check-clean --since N [--log login|game] [--strict]` — fail if any new
  line since the checkpoint looks like a crash (`Exception`/`ERROR`/
  `FATAL`/`Caused by:`). `WARN` lines don't fail by default — the codebase
  uses `log.warn` for expected bad-input cases, not crashes; add `--strict`
  to fail on those too.
- `wait-for --since N --pattern REGEX [--log login|game] [--timeout SECS]`
  — poll for an expected log line to appear. This is the "packet X was
  handled" proxy: OpenMMO's services log a descriptive line per handled
  packet (see `LoginService.kt`, `DialogService.kt`), so match on that
  until a real protocol-level test client exists.
- `db-query --container NAME --db/-Database NAME --user NAME --sql SQL` —
  read-only Postgres query, for "state persisted" checks once a handler
  actually writes to Postgres (as of the T0 player/party handler, nothing
  does yet — accounts are seeded in-memory).

Worked example against the T0 player-state handler merged in PR #4 (bash;
PowerShell is the same shape with `-Since`/`-Log`/etc.):

```bash
mark=$(scripts/assert.sh mark game)
# ... drive the action: client requests player state (character select) ...
scripts/assert.sh wait-for --since "$mark" --pattern "Sending LoadEntity for character" --timeout 15
scripts/assert.sh check-clean --since "$mark"
```

Or use the ready-made recipe for this exact handler —
`scripts/tests/assert-t0-player-party.sh` (+ `.ps1`): takes its own
checkpoint, polls for the LoadEntity line (optionally scoped to a
`--character NAME`), then asserts clean logs. Run it, then drive the
action (by hand or via a bot) any time before its timeout.

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
- **KSP cross-drive bug (real fix: relocate `GRADLE_USER_HOME`):** if
  `GRADLE_USER_HOME` (Gradle's cache, `~/.gradle` by default) is on a
  different drive letter than this project, `:server.game:kspKotlin`/
  `:server.login:kspKotlin` fail: `IllegalArgumentException: this and base
  files have different roots: C:\...\dagger-...jar!\...Provider.class and
  F:\...\server.game`. KSP's AA worker calls Java's `Path.relativize()`
  between a Gradle-cache jar entry and the project directory, which throws
  if the two paths don't share a root (drive letter). **Disabling
  `configuration-cache` (already done, in `gradle.properties`) only masks
  this for builds where KSP would otherwise be skipped/cached** — the first
  time this bit us it looked fixed, but any change that expands the Dagger
  component graph (a new `@Inject` wire-up) forces KSP to actually process
  the dagger jar regardless of configuration-cache, and the same exception
  comes back. The **actual fix** on this box: `GRADLE_USER_HOME` is
  relocated to `F:\coding\games\PokeBROMMO\toolchains\gradle-home` (same
  drive as the project) — set as a Windows **user-level env var**, so it's
  a machine-config change, not something a repo file can carry. **This
  won't apply to a session that was already running when it was set** —
  Windows doesn't push env var changes into existing processes. If you hit
  the cross-drive error, export it explicitly for your session:
  - bash: `export GRADLE_USER_HOME="F:/coding/games/PokeBROMMO/toolchains/gradle-home"`
  - PowerShell: `$env:GRADLE_USER_HOME = "F:\coding\games\PokeBROMMO\toolchains\gradle-home"`

  A genuinely fresh shell/session picks up the persisted default with zero
  setup. If this machine's cache and project ever end up on the same drive
  again (or KSP fixes the underlying `relativize()` bug upstream), both
  this relocation and the configuration-cache disable can be reverted.
- **game-db health check occasionally slow:** `dev-up`'s Postgres
  healthcheck-tolerant probe has hit an intermittent multi-minute stall
  specifically on `game-db` (not `login-db`) during a live capture session,
  even though both containers respond in ~180ms in isolated testing —
  looks like an occasional Docker Desktop/WSL2 hiccup, not a code bug.
  `dev-up.ps1` now hard-caps each probe attempt at 5s via a background job
  (bash's fallback already tolerated failures), so this can no longer stall
  the whole script, but the root intermittent slowness itself is still an
  open low-priority follow-up if it recurs.
- **Feature worktrees go stale on infra-only fixes:** a worktree pinned to
  a feature branch (e.g. `openmmo` → `feat/domain-ports`) only gets
  `master`'s fixes if someone merges/rebases master into it — which nobody
  does for a personal feature branch. This bit us once: a stale copy of
  `dev-up.ps1` sitting in a feature worktree looked like a new bug when it
  was really just missing a day's worth of fixes. **Always invoke infra
  scripts (`dev-up`, `assert`) from `openmmo-run`** (pinned to `master`,
  always current) — never from a feature worktree.

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
