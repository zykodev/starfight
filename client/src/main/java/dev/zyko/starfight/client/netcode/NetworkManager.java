package dev.zyko.starfight.client.netcode;

import dev.zyko.starfight.client.netcode.encoding.ClientPacketDecoder;
import dev.zyko.starfight.client.netcode.encoding.ClientPacketEncoder;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.C02PacketDisconnect;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkManager {

    public static final boolean EPOLL = Epoll.isAvailable();
    private Channel channel;

    public void connect(String remoteAddress) throws Exception {
        int port = 26800;
        String hostname = "";
        if(remoteAddress.contains(":")) {
            String[] components = remoteAddress.split(":");
            hostname = components[0];
            port = Integer.parseInt(components[1]);
        } else {
            hostname = remoteAddress;
        }
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            this.channel = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("encoder", new ClientPacketEncoder()).addLast("decoder", new ClientPacketDecoder()).addLast("nethandler", new ClientNetworkHandler(ch));
                        }
                    }).connect("127.0.0.1", 8000).sync().channel();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void disconnect() {
        if(this.channel != null) {
            if(this.channel.isOpen() || this.channel.isActive()) {
                this.sendPacket(new C02PacketDisconnect());
                this.channel.close().syncUninterruptibly();
                this.channel = null;
            }
        }
    }

    public void sendPacket(Packet packet) {
        this.channel.writeAndFlush(packet, this.channel.voidPromise());
    }

    public boolean isConnected() {
        return this.channel != null && this.channel.isActive();
    }

}
