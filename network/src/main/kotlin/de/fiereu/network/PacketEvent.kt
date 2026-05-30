package de.fiereu.network

import io.netty.channel.ChannelFuture

data class PacketEvent<T : Any>(
    val packet: T,
    val session: SessionContext,
)

fun PacketEvent<*>.respond(packet: Any): ChannelFuture = session.send(packet)

fun PacketEvent<*>.disconnect(reason: () -> String = { "no reason" }) = session.close(reason)
