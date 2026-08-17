# Request from PokeMMO representatives

The following is the email received from Nicholas Byrd, Director of Operations
of Block & Chain Publishing, Inc., representing the rights holders of
[PokeMMO](https://pokemmo.eu/).

---

Hello Fiereu,

My name is Nicholas Byrd, I'm the Director of Operations of Block & Chain
Publishing, Inc., representing the rightsholders of <https://pokemmo.com/>.

I am writing to you directly, and before involving GitHub or lawyers, because I
would much rather resolve this with an email than with a takedown notice.

The request is that you stop working on our client. That covers taking it
apart, patching it, republishing it, and distributing tooling that allows
others to do the same. It does not cover the server, the protocol work, or your
repositories in general.

This email sets out what we do not object to, what we do, and what happens if
this is not resolved.

## What we are not asking you to stop

We are not asking you to stop writing a server. The server implementation in
OpenMMO, the protocol and network modules, the codegen, the build tooling and
the documentation are your own work. Reimplementing a protocol from observed
behaviour is a long-standing and legitimate activity. We are also not making
any claim over the pret decompilation submodules under `decomp/`, which are
neither yours nor ours.

Our concerns are limited to the client-related material set out below.

## What we are asking you to stop

Three things, in order of how seriously we view them.

### 1. Distributing our client

`openmmo-org/archive` publishes our client software. The release tagged
`base-32763` contains `32763.tar.gpg`, a 276 MB encrypted copy of client
revision 32763. The release tagged `r32898` contains
`32898-to-32763.xdelta`, a 127 MB delta that reconstructs revision 32763 from
revision 32898. The repository also holds verbatim copies of our published
feed files together with our own signatures, and copies of our feed signing
public keys under `keys/`.

Encrypting a copy does not change what it is. Nor does shipping a copy as a
delta: a patch file computed from our client carries our client's content, and
the proportion of the file that is ours does not determine that. A patch is
yours to distribute only if all of its contents are your own work.

Separately, there is `revs.pokelite.net`. It serves a public page headed
"PokeMMO Archive Repository" listing 29 complete builds of our client as ZIP
downloads, each with a published SHA256, revisions 29764 through 32898, dated
30 September 2025 through 14 August 2026. That is approximately 4.3 GB of our
software, unencrypted and available without an account. Revision 32898 on that
list is our current live build.

That host was linked to users from a channel in the OpenMMO Discord, which you
operate. The channel is no longer visible. We have screenshots of it.

### 2. Distributing tools that defeat the client's protections

The launcher's patching subsystem, and the manifests under
`launcher/manifests/`, exist to replace the signing keys compiled into our
client with keys you control, to redirect it away from our servers, and to make
it accept a substitute update feed. `FeedTls.kt` builds a trust store containing
your own certificate so the client will accept it. The manifests reproduce our
keys, and on `feat/manifest-32824` they reproduce excerpts of our compiled
code.

The launcher's delta patching serves the same end from another direction. It
moves a current client back to revision 32763 because that is the revision you
hold a patch set for, which defeats the minimum revision requirement we use to
retire builds with known problems.

### 3. ByteDex

I am aware you have likely abandoned ByteDex or otherwise moved further work
into a private repository. I raise it because of what the public repository
contains.

ByteDex launches our client suspended, writes into the running process to
replace our login, game and chat keys, redirects it to a local proxy, supplies
a substitute trust store, and then terminates the client's encrypted session
and opens its own session to our live production servers, forwarding traffic
between the two. `NodeListRewriter` takes the real game server address out of
our login response, hands the client a loopback address, and keeps ours so the
proxy can connect. `ProxySession.inject()` can insert arbitrary packets in that
live session in either direction.

This is interception and modification of a live session with our servers
rather than observation of your own traffic. The repository's README states
that use will result in a ban, so we take the position to be understood.

If the project is finished, taking the repository down would resolve this item.

## The legal position, briefly

You are in Germany, so the applicable law is German rather than the DMCA. The
relevant provisions are these.

Our client is a computer program protected under section 69a UrhG. Section 69c
UrhG reserves to us the rights of reproduction (Nr. 1), adaptation and
reproduction of the results of that adaptation (Nr. 2), distribution (Nr. 3),
and making available to the public (Nr. 4). Hosting our client on GitHub
engages Nr. 1, Nr. 3 and Nr. 4. A patch file derived from our client, and the
patched client that results from applying it, both engage Nr. 2, which
expressly covers “die Übersetzung, die Bearbeitung, das Arrangement und andere
Umarbeitungen eines Computerprogramms sowie die Vervielfältigung der erzielten
Ergebnisse”.

Hosting complete copies of our client, and distributing patches computed from
it, is the substance of this. Those acts directly engage section 69c.

We also consider section 69f Abs. 2 UrhG engaged by the tooling that defeats
the checks compiled into our client. That provision covers means intended
solely to facilitate the removal or circumvention of technical program
protection mechanisms and implements Art. 7(1)(c) of Directive 2009/24/EC. For
software the general regime in sections 95a ff. UrhG does not apply, the
Software Directive being *lex specialis*.

You may have section 69e UrhG in mind, the decompilation provision for
interoperability. Absatz 2 is the relevant limit. It permits obtaining
interoperability information; it does not permit using what was obtained for
other purposes, passing it to third parties beyond what interoperability
requires, or acts conflicting with the normal exploitation of the work. On any
reading it does not authorise republishing our client.

Our claims would be under section 97 UrhG for injunctive relief and damages,
with section 69f UrhG for surrender and destruction of the circumvention tools.

One further point that is not copyright at all. Your accounts accepted our
terms of service. Those terms provide that “You may not modify the Game
Software, except for files explicitly designated as modifiable”, and they
define an unauthorised third-party program as “any software not provided by us
whose purpose is to facilitate the violation of, or whose activity does violate,
this Agreement”. Patching our client and building tooling for others to do the
same is a breach of that agreement on its own terms, independently of anything
above.

## Identification

One factual note, so that any decision is made on accurate information.

This is not being sent to an anonymous GitHub account. Our service records
associate a number of PokeMMO accounts with this project. Four of them, by way
of illustration:

> <(most of) my cool accounts :D>

Two of those register under `fiereu.de`, the domain also used for
`bytedex.fiereu.de` and for the project's `de.fiereu.openmmo` namespace. The
last address is a plus-alias of the same mailbox as the first.

We have not attached the connection records and we are not publishing them.
They are retained because identifying the correct respondent is a necessary
step in any proceedings, and they would be produced to counsel or to a court
if matters reach that point.

## What we would like to happen

By **Friday 21 August 2026**:

1. **Cease all distribution of the PokeMMO client.**

   That means `openmmo-org/archive` deleted, including its releases and release
   assets. Making it private is not sufficient, because the repository exists
   to distribute our client and we are asking for it to be gone rather than
   paused.

   It also means any other location under your control where copies of our
   client are served, which includes `revs.pokelite.net`. If that host is not
   yours to take down, say so and tell us whose it is.

2. `openmmo-org/ByteDex` deleted or made private, and the archive at
   `bytedex.fiereu.de` taken down.

3. `launcher/manifests/` and the launcher's patching subsystem removed from
   `openmmo-org/OpenMMO`, including the manifest on
   `feat/manifest-32824`, along with our keys where they appear in source, for
   example in `PokemmoFeedKeys.kt`.

4. A note to the people who have forked these repositories asking them to do
   the same. There are currently 68 of them. We would prefer that request came
   from you rather than us identifying each fork to GitHub.

We are not asking for OpenMMO itself to be taken down, and the server work can
continue publicly.

## Going forward

Removing what is currently published does not resolve this by itself. We are
also asking you to stop, and to confirm in writing that you have. Specifically,
that there will be no further:

- distribution of our client or any part of it, in any form, whether encrypted,
  unencrypted, or as a patch or delta computed from it;
- publication of patch sets, manifests, byte signatures or other material
  whose function is to modify our client or to redirect it away from our
  servers;
- modification of our client for distribution to anyone else;
- interception or modification of sessions between our client and our servers;
- extraction, copying or redistribution of content from our client, whether
  shipped as-is, repacked, converted, or loaded at runtime by OpenMMO or by any
  loader written for it.

We are raising that last point specifically because we would rather say it now
than argue about it later. It is a different question from the rest of this
email. The non-program content of a client, its art, audio, text and data, is
protected in its own right and not as part of a computer program, and the
provisions that give you latitude with software do not apply.

To be clear about what that does not cover: we are not asking you to undertake
never to examine our client again. You are entitled to run software you have
obtained lawfully and to study how it works, and we are not asking you to give
that up. What we are asking you to stop is copying it, modifying it, publishing
the results, and interfering with its connection to our service.

## If we do not hear from you

We will file with GitHub over the material described above, and we will take
advice on legal proceedings in Germany. We would prefer to avoid that route,
which is why we are writing first.

If any of the above is factually wrong, tell me and I will correct it.

Regards,

**Nicholas Byrd**  
Director of Operations, Block & Chain Publishing, Inc.  
<legal@blockandchainpublishing.com>
