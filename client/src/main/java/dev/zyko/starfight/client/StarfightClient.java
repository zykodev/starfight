package dev.zyko.starfight.client;

import dev.zyko.starfight.client.netcode.ClientNetworkHandler;
import dev.zyko.starfight.client.netcode.encoding.ClientPacketDecoder;
import dev.zyko.starfight.client.netcode.encoding.ClientPacketEncoder;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class StarfightClient {

    public static final boolean EPOLL = Epoll.isAvailable();

    private static StarfightClient instance;
    private long pingTime;

    public StarfightClient() throws Exception {
        instance = this;
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            Channel ch = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("encoder", new ClientPacketEncoder()).addLast("decoder", new ClientPacketDecoder()).addLast("nethandler", new ClientNetworkHandler(ch));
                        }
                    }).connect("127.0.0.1", 8000).sync().channel();
            int i = 0;
            while(true) {
                if(i == 5) break;
                Thread.sleep(200);
                ch.writeAndFlush(new C01PacketKeepAlive(System.currentTimeMillis()), ch.voidPromise());
                i++;
            }
            ch.close().syncUninterruptibly();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            StarfightClient client = new StarfightClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StarfightClient getInstance() {
        return instance;
    }

    public long getPingTime() {
        return pingTime;
    }

    public void setPingTime(long pingTime) {
        this.pingTime = pingTime;
    }

}
