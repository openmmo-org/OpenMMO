package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SceneObjectFrame(
    val valueA: Short,
    val valueB: Short,
    val valueC: Int,
    val flag: Boolean,
)

data class SceneObjectState(
    val objectId: Long,
    val type: Byte,
    val frames: List<SceneObjectFrame>,
)

data class SceneObjectStatesPacket(
    val objects: List<SceneObjectState>,
)

private val SceneObjectFrameCodec: Codec<SceneObjectFrame> =
    object : PacketCodec<SceneObjectFrame>() {
      override fun CodecScope<SceneObjectFrame>.body(): SceneObjectFrame {
        val valueA = field(S16LE) { it.valueA }
        val valueB = field(S16LE) { it.valueB }
        val valueC = field(S32LE) { it.valueC }
        val flag = field(U8) { if (it.flag) 1 else 0 } == 1
        return SceneObjectFrame(valueA, valueB, valueC, flag)
      }
    }

private val SceneObjectStateCodec: Codec<SceneObjectState> =
    object : PacketCodec<SceneObjectState>() {
      override fun CodecScope<SceneObjectState>.body(): SceneObjectState {
        val objectId = field(S64LE) { it.objectId }
        val type = field(S8) { it.type }
        val frames = field(SceneObjectFrameCodec.listPrefixed(U8)) { it.frames }
        return SceneObjectState(objectId, type, frames)
      }
    }

object SceneObjectStatesPacketCodec : PacketCodec<SceneObjectStatesPacket>() {
  override fun CodecScope<SceneObjectStatesPacket>.body(): SceneObjectStatesPacket {
    val objects = field(SceneObjectStateCodec.listPrefixed(U8)) { it.objects }
    return SceneObjectStatesPacket(objects)
  }
}
