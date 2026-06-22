package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

internal sealed class BattleMessageArgPayload

internal object EmptyArgPayload : BattleMessageArgPayload()

internal data class LongArgPayload(val value: Long) : BattleMessageArgPayload()

internal data class IntArgPayload(val value: Int) : BattleMessageArgPayload()

internal data class StringArgPayload(val value: String) : BattleMessageArgPayload()

internal data class ShortListArgPayload(val values: List<Short>) : BattleMessageArgPayload()

internal data class BattleMessageArg(
    val id: Byte,
    val type: Byte,
    val hasExtra: Boolean,
    val extra: Byte,
    val payload: BattleMessageArgPayload,
)

internal val BattleMessageArgCodec: Codec<BattleMessageArg> =
    object : Codec<BattleMessageArg> {
      override fun read(buf: ReadBuffer): BattleMessageArg {
        val id = S8.read(buf)
        var type = S8.read(buf)
        var hasExtra = false
        var extra: Byte = 0
        if (type.toInt() and 0x80 != 0) {
          type = (type.toInt() and 0x7F).toByte()
          hasExtra = true
          extra = S8.read(buf)
        }
        val t = type.toInt()
        val payload: BattleMessageArgPayload =
            when {
              t == 5 -> StringArgPayload(Utf16LeNullTerminated.read(buf))
              t == 28 -> EmptyArgPayload
              t == 30 -> LongArgPayload(S64LE.read(buf))
              t == 9 || t == 10 || t == 17 -> IntArgPayload(S32LE.read(buf))
              t == 18 -> StringArgPayload(Utf16LeNullTerminated.read(buf))
              else -> {
                val n = U8.read(buf)
                ShortListArgPayload((0 until n).map { S16LE.read(buf) })
              }
            }
        return BattleMessageArg(id, type, hasExtra, extra, payload)
      }

      override fun write(buf: WriteBuffer, value: BattleMessageArg) {
        S8.write(buf, value.id)
        val rawType = if (value.hasExtra) (value.type.toInt() or 0x80).toByte() else value.type
        S8.write(buf, rawType)
        if (value.hasExtra) S8.write(buf, value.extra)
        val t = value.type.toInt()
        when {
          t == 5 -> Utf16LeNullTerminated.write(buf, (value.payload as StringArgPayload).value)
          t == 28 -> {}
          t == 30 -> S64LE.write(buf, (value.payload as LongArgPayload).value)
          t == 9 || t == 10 || t == 17 -> S32LE.write(buf, (value.payload as IntArgPayload).value)
          t == 18 -> Utf16LeNullTerminated.write(buf, (value.payload as StringArgPayload).value)
          else -> {
            val values = (value.payload as ShortListArgPayload).values
            U8.write(buf, values.size)
            for (s in values) S16LE.write(buf, s)
          }
        }
      }
    }

sealed class BattleSerializedEntry

internal object NullSerializedEntry : BattleSerializedEntry()

internal data class CreatureDataEntry(val value: Int, val args: List<BattleMessageArg>) :
    BattleSerializedEntry()

internal data class CreatureMovesEntry(val value: Byte, val moves: List<Short>) :
    BattleSerializedEntry()

internal data class MoveAnimationEntry(val a: Byte, val b: Byte, val moveId: Short) :
    BattleSerializedEntry()

internal data class TextCreatureEntry(val a: Byte, val typeId: Byte, val x: Short, val y: Short) :
    BattleSerializedEntry()

internal object EmptyCreatureDataEntry : BattleSerializedEntry()

internal val BattleSerializedEntryCodec: Codec<BattleSerializedEntry> =
    object : Codec<BattleSerializedEntry> {
      override fun read(buf: ReadBuffer): BattleSerializedEntry {
        return when (val tag = S8.read(buf).toInt()) {
          -1 -> NullSerializedEntry
          0 -> {
            val value = S32LE.read(buf)
            val n = U8.read(buf)
            CreatureDataEntry(value, (0 until n).map { BattleMessageArgCodec.read(buf) })
          }

          1 -> {
            val v = S8.read(buf)
            val n = U8.read(buf)
            CreatureMovesEntry(v, (0 until n).map { S16LE.read(buf) })
          }

          2 -> {
            val moveId = S16LE.read(buf)
            val a = S8.read(buf)
            val b = S8.read(buf)
            MoveAnimationEntry(a, b, moveId)
          }

          3 -> {
            val a = S8.read(buf)
            val typeId = S8.read(buf)
            val x = S16LE.read(buf)
            val y = S16LE.read(buf)
            TextCreatureEntry(a, typeId, x, y)
          }

          else -> EmptyCreatureDataEntry
        }
      }

      override fun write(buf: WriteBuffer, value: BattleSerializedEntry) {
        when (value) {
          is NullSerializedEntry -> S8.write(buf, (-1).toByte())
          is CreatureDataEntry -> {
            S8.write(buf, 0)
            S32LE.write(buf, value.value)
            U8.write(buf, value.args.size)
            for (a in value.args) BattleMessageArgCodec.write(buf, a)
          }

          is CreatureMovesEntry -> {
            S8.write(buf, 1)
            S8.write(buf, value.value)
            U8.write(buf, value.moves.size)
            for (m in value.moves) S16LE.write(buf, m)
          }

          is MoveAnimationEntry -> {
            S8.write(buf, 2)
            S16LE.write(buf, value.moveId)
            S8.write(buf, value.a)
            S8.write(buf, value.b)
          }

          is TextCreatureEntry -> {
            S8.write(buf, 3)
            S8.write(buf, value.a)
            S8.write(buf, value.typeId)
            S16LE.write(buf, value.x)
            S16LE.write(buf, value.y)
          }

          is EmptyCreatureDataEntry ->
              throw MalformedPacketException("cannot encode default battle entry")
        }
      }
    }

data class BattleBulkStatePacket(
    val phase: Byte,
    val firstGroup: List<BattleSerializedEntry>,
    val secondGroup: List<BattleSerializedEntry>,
    val valueA: Int,
    val valueB: Int,
    val flag: Byte,
    val thirdGroup: List<BattleSerializedEntry>,
)

object BattleBulkStatePacketCodec : PacketCodec<BattleBulkStatePacket>() {
  override fun CodecScope<BattleBulkStatePacket>.body(): BattleBulkStatePacket {
    val phase = field(S8) { it.phase }
    val firstGroup = field(BattleSerializedEntryCodec.listPrefixed(U8)) { it.firstGroup }
    val secondGroup = field(BattleSerializedEntryCodec.listPrefixed(U8)) { it.secondGroup }
    val valueA = field(S32LE) { it.valueA }
    val valueB = field(S32LE) { it.valueB }
    val flag = field(S8) { it.flag }
    val thirdGroup = field(BattleSerializedEntryCodec.listPrefixed(U8)) { it.thirdGroup }
    return BattleBulkStatePacket(phase, firstGroup, secondGroup, valueA, valueB, flag, thirdGroup)
  }
}
