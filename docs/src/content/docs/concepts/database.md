---
title: The database
description: How persistence works and how dev data is seeded.
---

OpenMMO uses two PostgreSQL databases, matching the two servers:

- **login-db**: accounts (`users` table), owned by `server.login`.
- **game-db**: characters, pokemon, and items, owned by `server.game`.

They stay separate on purpose. The game server never looks up accounts, it
trusts the `userId` handed over through the session token. That is also why
`characters.user_id` has no foreign key: it points into the other database.

Both are wired the same way: HikariCP for connection pooling, Flyway for
migrations, and jOOQ for queries.

## Migrations are the source of truth

The schema lives as plain SQL in each server's
`src/main/resources/db/migration/` (`V1__...`, `V2__...`). Those files are
used twice:

- **At build time** jOOQ parses them with `DDLDatabase` and generates Kotlin
  classes into the module's `jooq` source set. No database or Docker is
  needed to build.
- **At server start** `main()` runs Flyway against the real database before
  anything listens. A missing or broken database stops the server right there.

To change the schema, add a new `V<next>__short_name.sql` file and rebuild.
Never edit an already merged migration. Keep the DDL standard SQL, the jOOQ
parser does not know exotic Postgres extensions.

## Memory is the live version

The game server does not query the database during gameplay.
`CharacterStore` loads a character when its player logs in and all reads and
writes hit memory. Changes mark the character dirty, and a background flusher
writes dirty characters back after a short debounce. Warps and disconnects
flush immediately, and a disconnect also evicts the character from memory
once its last write succeeded. So the database always trails memory by a few
seconds at most, and only connected players are cached.

## Dev data seeding

Dev seed data (the `admin`/`test` accounts and the `Test`/`Test2` characters)
does NOT belong in `db/migration`. Anything there runs in every environment,
including production. Seeds live in a separate Flyway location:

- Files go into `src/main/resources/db/dev/`.
- They are repeatable migrations (`R__seed_something.sql`), not versioned
  ones, so they never conflict with real schema versions.
- Every statement must be idempotent, because repeatable migrations run again
  whenever their file changes. Use `ON CONFLICT DO NOTHING` on inserts.
- Fixed ids in seed files must keep the entity tag in the low 16 bits
  (0x9000 for characters, 0xC000 for monsters). Runtime ids carry a timestamp
  head, so low fixed values can never collide with them.

The `db/dev` location is only applied when `db.seedDev` is enabled. It
defaults to true for local development and is turned off in production with
the `LOGIN_DB_SEED_DEV=false` and `GAME_DB_SEED_DEV=false` environment
variables.

To add more dev data, extend an existing `R__` file or add a new one, keep it
idempotent, and restart the server. Flyway reapplies the changed file on the
next start.
