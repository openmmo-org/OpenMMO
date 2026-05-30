package de.fiereu.network

import io.netty.bootstrap.Bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer

fun ServerBootstrap.installNetwork(
    identity: SessionIdentity.ServerRoot,
    applicationProtocol: Protocol,
    applicationHandlerFactory: () -> ProtocolHandler,
    options: PipelineOptions = PipelineOptions(),
): ServerBootstrap =
    childHandler(
        object : ChannelInitializer<Channel>() {
          override fun initChannel(ch: Channel) {
            installPipeline(
                pipeline = ch.pipeline(),
                side = Side.SERVER,
                identity = identity,
                applicationProtocol = applicationProtocol,
                applicationHandlerFactory = applicationHandlerFactory,
                options = options,
            )
          }
        },
    )

fun Bootstrap.installNetwork(
    identity: SessionIdentity.ClientTrust,
    applicationProtocol: Protocol,
    applicationHandlerFactory: () -> ProtocolHandler,
    options: PipelineOptions = PipelineOptions(),
): Bootstrap =
    handler(
        object : ChannelInitializer<Channel>() {
          override fun initChannel(ch: Channel) {
            installPipeline(
                pipeline = ch.pipeline(),
                side = Side.CLIENT,
                identity = identity,
                applicationProtocol = applicationProtocol,
                applicationHandlerFactory = applicationHandlerFactory,
                options = options,
            )
          }
        },
    )
