package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class MonsterRecordBookPacket(
    val records: List<Pokemon>,
)

private val PokemonRecordListPrefixedU8: Codec<List<Pokemon>> =
    object : Codec<List<Pokemon>> {
      override fun read(buf: ReadBuffer): List<Pokemon> {
        val n = U8.read(buf)
        return List(n) { PokemonCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<Pokemon>) {
        U8.write(buf, value.size)
        value.forEach { PokemonCodec.write(buf, it) }
      }
    }

object MonsterRecordBookPacketCodec : PacketCodec<MonsterRecordBookPacket>() {
  override fun CodecScope<MonsterRecordBookPacket>.body(): MonsterRecordBookPacket {
    val records = field(PokemonRecordListPrefixedU8, MonsterRecordBookPacket::records)
    return MonsterRecordBookPacket(records)
  }
}
