# OpenMMO

[![](https://dcbadge.limes.pink/api/server/SQwGXyY2gz)](https://discord.gg/SQwGXyY2gz)

## ToC
- [Description](#description)
- [Building](#building)
- [Configuration](#configuration)
- [Releases](#releases)
- [Documentation](#wiki)
- [License](LICENSE)
- [Disclaimer](#disclaimer)

## Description

> OpenMMO is currently in development and not yet ready for any real use.

OpenMMO is an open-source implementation of the PokeMMO server.
The goal is it to provide a free and open-source alternative to the PokeMMO server.

## Building

Game data is generated at build time from the [pret](https://github.com/pret)
decompilation projects, which are vendored as git submodules under `decomp/`.
Clone the repository with its submodules:

```bash
git clone --recurse-submodules <repo-url>
# or, for an existing clone:
git submodule update --init --recursive
```

Without the submodules the `:codegen` build fails, because the generators have no
decomp data to read. The one exception is `decomp/pokeblack`, which holds the
item data as committed files rather than a submodule, because its source
repository is private.

### ROMs

A dialog id is a file offset into the retail GBA ROM. The decomp is byte-identical
to it, so the generator encodes a text from the decomp, finds those bytes in the
ROM, and packs the offset into the id. The decomp alone has no offsets.

Put the Emerald (`BPEE`, Hoenn) and FireRed (`BPRE`, Kanto) ROMs in `roms/`.
Filenames do not matter, each is identified by the game code in its GBA header.
The folder is **gitignored**, this project ships no ROMs.

Without them the build still succeeds and every dialog id is `0`, so CI passes but
the client shows the wrong text.

## Configuration

All local configuration and secrets live in a `.env` file at the repository
root. It is **gitignored**, never commit it. Use the tracked
[`.env.example`](.env.example) as the template:

```bash
cp .env.example .env          # then edit the values
docker compose up -d          # start all docker containers
./gradlew runAll              # build and run the servers
```

For local-only tweaks to the container setup, create a
`docker-compose.override.yml` (also gitignored). 
Docker Compose merges it automatically on `docker compose up`. 
For deployment,supply a proper `.env` and run `docker compose -f docker-compose.yml up -d` to skip any override.

### Game server address

The login server hands the client an address to reach the game server on. It
defaults to loopback, which serves a client on that same machine and nothing
else. Set `GAME_SERVER_PUBLIC_IPV4` for anything reachable from elsewhere,
`GAME_SERVER_PUBLIC_IPV6` if you serve one, and `GAME_SERVER_PORT` if it is not
7777. That is the whole of it for a normal deployment.

The same response also carries a local address and hostname, which every capture
of the retail server has as `127.0.0.1` and `localhost` no matter how public the
addresses beside them are. `GAME_SERVER_LOCAL_ADDRESS` and
`GAME_SERVER_LOCAL_HOSTNAME` exist to experiment with that, not to be pointed at
your public address.

An empty value is not the same as an unset one. It replaces the default with
nothing, and the server refuses to start rather than advertising an address no
client can reach.

### First account

A fresh login database has no users in it at all. Set `OPENMMO_ADMIN_USERNAME`
and `OPENMMO_ADMIN_PASSWORD` and the server creates that account on startup,
but only while the user table is still empty. It never touches an account that
already exists, so changing either value later does nothing and is not a way to
reset a password. Leave both unset and no account is created.

The password is stored as unsalted SHA-1, because that is what the client sends
and the server only ever compares what arrives.

### Server key

Both servers share one private key. A local build generates it, so development
needs no setup.

Released archives ship no keys. Generate a pair with
`./gradlew :keys:generateGame` and pass the private key to both servers through
`OPENMMO_GAME_PRIVATE_KEY` (the PEM) or `OPENMMO_GAME_PRIVATE_KEY_FILE` (a path
to it). Clients need a patched build carrying the matching public key.

## Releases

[release-please](https://github.com/googleapis/release-please) cuts releases from
the commit history. Pull requests are squash merged, so their titles become the
commit messages it reads and must follow
[Conventional Commits](https://www.conventionalcommits.org/). CI rejects titles
that do not.

`feat` bumps the minor version, `fix` the patch version. Below `1.0.0` a
breaking change bumps the minor version instead of jumping to `1.0.0`.

Every push to `master` opens or updates a release pull request. Merging it tags
the release and attaches the server archives. It also publishes a container
image per server, under the release version, its `major.minor` line and
`latest`:

```bash
docker pull ghcr.io/openmmo-org/openmmo-login:latest
docker pull ghcr.io/openmmo-org/openmmo-game:latest
```

Every push to `master` publishes a `dev` image alongside those, so `dev` is
always the current default branch rather than the last release. Each one also
gets a `sha-<short commit>` tag, which is what to pin when a build needs to stay
put. These are built from whatever was pushed and do not wait for the test suite,
so `dev` can be broken in a way `latest` is not.

```bash
docker pull ghcr.io/openmmo-org/openmmo-game:dev
```

`deploy/` holds a Compose stack per server, each with its own database, taking
the `dev` images by default. Copy `deploy/.env.example`, fill it in, and bring
the game side up first:

```bash
docker compose --env-file .env -f game.compose.yml up -d
docker compose --env-file .env -f login.compose.yml up -d
```

The private key is mounted as a Compose secret, so the file has to be readable
by uid 10001, which is what the servers run as. Only `2106` and `7777` are
published, and neither database is.

An image carries no keys and no `.env`, so pass the database settings, the
session secret, the private key and the game server address as environment
variables. Build one yourself with:

```bash
./gradlew :server.login:installDist
docker build -f server.login/Dockerfile -t openmmo-login .
```

The version lives in `gradle.properties` and applies to every module, do not
edit it by hand.

## Wiki

The documentation wiki can be found [here](https://docs.openmmo.dev/).
Or you can navigate to it via the `docs` folder in this repository.

## Disclaimer
[PokeMMO](https://pokemmo.eu/) is not affiliated with this project in any way.
Hosting/Using a private server might be against the [PokeMMO ToS](https://pokemmo.com/tos/).
