package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityFramesUpdatePacket(
    val entityId: Long,
    val frames: GroupListFrameSet,
)

private val FramesFrameCodec: Codec<GroupListFrame> =
    object : PacketCodec<GroupListFrame>() {
      override fun CodecScope<GroupListFrame>.body(): GroupListFrame {
        val typeId = field(S16LE) { it.typeId }
        val packed = field(S16LE) { it.packed }
        val byteA = field(S8) { it.byteA }
        val byteB = field(S8) { it.byteB }
        val packed2 = field(S16LE) { it.packed2 }
        return GroupListFrame(typeId, packed, byteA, byteB, packed2)
      }
    }

private val FramesSetCodec: Codec<GroupListFrameSet> =
    object : PacketCodec<GroupListFrameSet>() {
      override fun CodecScope<GroupListFrameSet>.body(): GroupListFrameSet {
        val count = field(U8) { it.frames.size }
        val listType = if (count >= 1) field(S8) { it.listType ?: 0 } else null
        val frames = (0 until count).map { i -> field(FramesFrameCodec) { it.frames[i] } }
        return GroupListFrameSet(listType, frames)
      }
    }

object EntityFramesUpdatePacketCodec : PacketCodec<EntityFramesUpdatePacket>() {
  override fun CodecScope<EntityFramesUpdatePacket>.body(): EntityFramesUpdatePacket {
    val entityId = field(S64LE) { it.entityId }
    val frames = field(FramesSetCodec) { it.frames }
    return EntityFramesUpdatePacket(entityId, frames)
  }
}
