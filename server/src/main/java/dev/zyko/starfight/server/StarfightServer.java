package dev.zyko.starfight.server;

import dev.zyko.starfight.server.netcode.ServerNetworkHandler;
import dev.zyko.starfight.server.netcode.encoding.ServerPacketDecoder;
import dev.zyko.starfight.server.netcode.encoding.ServerPacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class StarfightServer {

    public static final boolean EPOLL = Epoll.isAvailable();

    public StarfightServer() throws Exception {
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("encoder", new ServerPacketEncoder()).addLast("decoder", new ServerPacketDecoder()).addLast("nethandler", new ServerNetworkHandler(ch));
                        }
                    })
                    .bind(8000).sync().channel().closeFuture().syncUninterruptibly();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            StarfightServer server = new StarfightServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
