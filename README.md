# OpenMMO

[![](https://dcbadge.limes.pink/api/server/SQwGXyY2gz)](https://discord.gg/SQwGXyY2gz)

## ToC
- [Description](#description)
- [Configuration](#configuration)
- [Documentation](#wiki)
- [License](LICENSE)
- [Disclaimer](#disclaimer)

## Description

> OpenMMO is currently in development and not yet ready for any real use.

OpenMMO is an open-source implementation of the PokeMMO server.
The goal is it to provide a free and open-source alternative to the PokeMMO server.

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

## Wiki

The documentation wiki can be found [here](https://forge.fiereu.de/OpenMMO/OpenMMO/wiki).
Or you can navigate to it via the `docs` folder in this repository.

## Disclaimer
[PokeMMO](https://pokemmo.eu/) is not affiliated with this project in any way.
Hosting/Using a private server might be against the [PokeMMO ToS](https://pokemmo.com/tos/).
