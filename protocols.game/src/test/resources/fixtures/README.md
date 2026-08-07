# Fixtures

Raw packet payloads from real captures, one file per packet.

    <server>/<direction>/<packet id>/<name>_<client version>.bin

Packet id is lowercase hex without a prefix. The version matters: the wire format changes between
clients.

```kotlin
val buf = fixture("game/s2c/9b/monster_page_32710.bin")
GtlSearchPagePacketCodec.read(buf)
buf.remaining() shouldBe 0
```
