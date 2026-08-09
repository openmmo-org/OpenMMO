package de.fiereu.network.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent

internal class CloseOnReaderIdleHandler : ChannelInboundHandlerAdapter() {
  override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
    if (evt is IdleStateEvent && evt.state() == IdleState.READER_IDLE) {
      ctx.close()
      return
    }
    ctx.fireUserEventTriggered(evt)
  }
}
