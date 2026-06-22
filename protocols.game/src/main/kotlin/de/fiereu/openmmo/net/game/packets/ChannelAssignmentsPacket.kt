package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChannelTypeBinding(val chatType: Short, val channel: Byte)

data class ChannelGroupBinding(val channel: Byte, val members: List<Short>)

data class ChannelAssignmentsPacket(
    val typeBindings: List<ChannelTypeBinding>,
    val groupBindings: List<ChannelGroupBinding>,
)

private val ChannelTypeBindingCodec: Codec<ChannelTypeBinding> =
    object : PacketCodec<ChannelTypeBinding>() {
      override fun CodecScope<ChannelTypeBinding>.body(): ChannelTypeBinding {
        val chatType = field(S16LE) { it.chatType }
        val channel = field(S8) { it.channel }
        return ChannelTypeBinding(chatType, channel)
      }
    }

private val ChannelGroupBindingCodec: Codec<ChannelGroupBinding> =
    object : PacketCodec<ChannelGroupBinding>() {
      override fun CodecScope<ChannelGroupBinding>.body(): ChannelGroupBinding {
        val channel = field(S8) { it.channel }
        val members = field(S16LE.listPrefixed(U16LE)) { it.members }
        return ChannelGroupBinding(channel, members)
      }
    }

object ChannelAssignmentsPacketCodec : PacketCodec<ChannelAssignmentsPacket>() {
  override fun CodecScope<ChannelAssignmentsPacket>.body(): ChannelAssignmentsPacket {
    val typeBindings = field(ChannelTypeBindingCodec.listPrefixed(U16LE)) { it.typeBindings }
    val groupBindings = field(ChannelGroupBindingCodec.listPrefixed(U8)) { it.groupBindings }
    return ChannelAssignmentsPacket(typeBindings, groupBindings)
  }
}
