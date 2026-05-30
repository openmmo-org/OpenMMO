package de.fiereu.openmmo.server.game.session

import de.fiereu.network.SessionContext
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class SessionRegistryHandler(
    private val registry: SessionRegistry,
    private val session: SessionContext,
) : ChannelInboundHandlerAdapter() {

  override fun channelActive(ctx: ChannelHandlerContext) {
    registry.register(session)
    ctx.fireChannelActive()
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    registry.unregister(session)
    ctx.fireChannelInactive()
  }
}
