# Fixtures

Raw packet payloads from real captures, one file per packet.

    <server>/<direction>/<packet id>/<name>_<client version>.bin

Packet id is lowercase hex without a prefix. The version matters, the wire format changes between
clients, so carry it whenever the capture can be traced back to a session. A few older payloads
predate that rule and have no suffix. `_scrubbed` means player data was replaced before the payload
was committed, so it no longer matches anything in the archive.

`fixture` gives you the bytes, `fixtureBuffer` wraps them for a codec that reads from a buffer. Both
live in the `common.test` module.

```kotlin
val buf = fixtureBuffer("game/s2c/9b/monster_page_32710.bin")
GtlSearchPagePacketCodec.read(buf)
buf.remaining() shouldBe 0
```
