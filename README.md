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

The map data is generated at build time from the [pret](https://github.com/pret)
decompilation projects, which are vendored as git submodules under `decomp/`.
Clone the repository with its submodules:

```bash
git clone --recurse-submodules <repo-url>
# or, for an existing clone:
git submodule update --init --recursive
```

Without the submodules the `:maps` build fails, because the generator has no
decomp data to read.

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

### Server key

The login and game servers share one private key and must be given the same one.
A local build generates it and puts it on the classpath, so development needs no
setup. Released archives ship no private key, so a deployment supplies its own
through either `OPENMMO_GAME_PRIVATE_KEY` (the PEM itself) or
`OPENMMO_GAME_PRIVATE_KEY_FILE` (a path to it). Generate one with
`./gradlew :keys:generateGame`.

## Releases

Releases are cut by [release-please](https://github.com/googleapis/release-please)
from the commit history, so pull request titles must follow
[Conventional Commits](https://www.conventionalcommits.org/). CI rejects titles
that do not, because pull requests are squash merged and the title becomes the
commit message on `master`.

`feat` bumps the minor version, `fix` the patch version. While the version is
below `1.0.0`, a breaking change (`feat!` or `BREAKING CHANGE`) bumps the minor
version instead of jumping to `1.0.0`.

On every push to `master`, release-please opens or updates a release pull
request with the new version and changelog. Merging it tags the release and
publishes the release notes. The version lives in `gradle.properties` and
applies to every module, do not edit it by hand.

## Wiki

The documentation wiki can be found [here](https://openmmo.readthedocs.io/en/latest/).
Or you can navigate to it via the `docs` folder in this repository.

## Disclaimer
[PokeMMO](https://pokemmo.eu/) is not affiliated with this project in any way.
Hosting/Using a private server might be against the [PokeMMO ToS](https://pokemmo.com/tos/).
