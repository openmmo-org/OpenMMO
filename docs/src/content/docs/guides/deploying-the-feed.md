---
title: Deploying the feed and the launcher
description: How to publish the OpenMMO feed and build a launcher that trusts it.
---

Players run a launcher. The launcher downloads a PokeMMO client, patches it, and
starts it. To know which server to send the client to, it needs a feed.

The feed is a handful of static files on a web host. The launcher is built once
and points at that host. This guide sets up both.

For local work you need none of this. `./gradlew :launcher:dev` serves a feed on
loopback and starts the launcher against it. See
[Running the game client](../running-the-client/).

## What the feed holds

| File | Read by | Purpose |
|------|---------|---------|
| `main.xml` | the client | Which login server to use, and at which revision. |
| `main.sig256` | the client | Signature of `main.xml`. |
| `news.xml` | the client | The news panel on the client's login screen. |
| `game.public.pem` | the launcher | The server's root key, patched into the client. |
| `game.public.pem.sig256` | the launcher | Signature of `game.public.pem`. |
| `feed.public.pem` | nobody | Published so anyone can check the signatures. |

The client reads its files directly, because the launcher rewrites the PokeMMO
urls inside the client to point here. The launcher reads the server key so it
can patch it in.

## Why the files are signed

The client and the launcher each trust exactly one key, and that key is
compiled into them. Everything else is checked against it.

TLS alone would not be enough. TLS proves you are talking to the right web host,
but it does not prove the host is honest. Anyone who could write to the host
could hand out a different server address or a different server key, and the
client would follow it. A signature made with a key the host never holds closes
that gap. If a signature does not check out, the launcher refuses to patch and
the client refuses to start.

## Set the feed up once

### 1. Make the feed key pair

```bash
./gradlew :keys:generateFeed
```

This writes `keys/build/feed.private.pem` and `keys/build/feed.public.pem`.

Keep the private key out of git and somewhere safe. Losing it means every
launcher already out there stops trusting your feed, and the only fix is a new
launcher release.

The key is RSA-3072 and has to stay that size. The launcher writes it over
PokeMMO's key inside the client binary, and a replacement can be padded but
never shortened.

### 2. Create the repository

Put the files in the root of a repository and turn on GitHub Pages. Add a
`CNAME` file holding the domain, then add a DNS CNAME record pointing that
domain at the Pages host. Wait for the certificate to be issued before testing.

Add a `.gitattributes` with this line:

```
* -text
```

The files are signed byte for byte. Without that line git may rewrite line
endings on checkout, the bytes stop matching the signature, and every launcher
rejects the feed.

### 3. Write `main.xml`

Start from `launcher/src/main/resources/main_feed_template.xml` and fill in
three things:

- `ip` and `ip_cn`: the hostname of your login server
- `port`: the login server port, which is `2106` by default
- `revision`: the client revision, which must match a patch manifest in
  `launcher/manifests/`

Leave the updater and android entries alone. The client reads them but the
launcher does the updating, so the zeroed hashes are fine.

### 4. Sign it

```bash
openssl dgst -sha256 -sign feed.private.pem -out main.sig256 main.xml
```

Re-run this every time you edit `main.xml`. An old signature is the same to the
client as a forged one.

### 5. Publish the server key

Copy the login server's public root key into the repository as
`game.public.pem` and sign it the same way:

```bash
openssl dgst -sha256 -sign feed.private.pem -out game.public.pem.sig256 game.public.pem
```

Publish only the public half. The private key belongs on the server and nowhere
else.

### 6. Keep the names short

Use `main.xml` and `news.xml`, not longer names. The launcher rewrites urls that
are already compiled into the client, and a replacement can be padded but never
shortened. A longer name simply will not fit.

## Build the launcher

Put the published `feed.public.pem` in `keys/build/` so the build bakes the
right key in, then build with the feed origin:

```bash
./gradlew :launcher:packageDistributionForCurrentOS \
  -Popenmmo.feedOrigin=https://feed.openmmo.dev
```

Without the property the launcher points at the loopback dev feed, which is
what you want for development and never for a release.

Two keys go in at build time, and they behave differently. The feed key is
baked in and can only be changed by shipping a new launcher. The server key is
fetched from the feed on every run, so rotating it is a feed change and needs no
new release.

## Check it worked

Start the launcher and press Play. In the client log you want to see:

```
Loaded Main Feed from mirror 0
```

If you see `Error verifying SHA256withRSA feed signature` instead, the client
fetched your feed but did not trust it. Either `main.sig256` is stale, or the
launcher was built with a different `feed.public.pem` than the one that signed
it.

If the client loads the feed and then logs `Failed to verify signature` while
connecting, the feed and the launcher are fine but the server key is wrong.
Check that `game.public.pem` is the key the login server actually runs with.

## When things change

- **New client revision.** Add a manifest under `launcher/manifests/`, update
  `revision` in `main.xml`, and re-sign it.
- **Server key rotated.** Publish the new `game.public.pem` and its signature.
  Launchers pick it up on the next run.
- **Feed key rotated.** Re-sign every file and ship a new launcher release,
  because the old one still trusts the old key.
