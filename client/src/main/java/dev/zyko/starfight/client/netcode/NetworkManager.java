package dev.zyko.starfight.client.netcode;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.netcode.encoding.ClientPacketDecoder;
import dev.zyko.starfight.client.netcode.encoding.ClientPacketEncoder;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.C02PacketDisconnect;
import dev.zyko.starfight.protocol.impl.C03PacketConnect;
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

    public enum ConnectionStatus {
        CONNECTING,
        LOGGING_IN,
        RETRIEVING_WORLD_DATA,
        CONNECTED,
        OFFLINE
    }

    public static final boolean EPOLL = Epoll.isAvailable();
    private EventLoopGroup eventLoopGroup;
    private Channel channel;
    private ConnectionStatus status = ConnectionStatus.OFFLINE;

    public NetworkManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
    }

    public void connect(String remoteAddress, String nickname) throws Exception {
        int port = 26800;
        String hostname = "";
        if(remoteAddress.contains(":")) {
            String[] components = remoteAddress.split(":");
            hostname = components[0];
            port = Integer.parseInt(components[1]);
        } else {
            hostname = remoteAddress;
        }
        this.eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.status = ConnectionStatus.CONNECTING;
        this.channel = new Bootstrap()
                .group(eventLoopGroup)
                .channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast("encoder", new ClientPacketEncoder()).addLast("decoder", new ClientPacketDecoder()).addLast("nethandler", new ClientNetworkHandler(ch));
                    }
                }).connect(hostname, port).sync().channel();
        this.status = ConnectionStatus.LOGGING_IN;
        this.sendPacket(new C01PacketKeepAlive(System.currentTimeMillis()));
        this.sendPacket(new C03PacketConnect(nickname, StarfightClient.VERSION));
    }

    public void disconnect() {
        System.out.println("Disconnecting!");
        this.status = ConnectionStatus.OFFLINE;
        if(this.channel != null) {
            if(this.channel.isOpen() || this.channel.isActive()) {
                this.sendPacket(new C02PacketDisconnect());
                this.channel.close().syncUninterruptibly();
            }
            this.channel = null;
            this.eventLoopGroup.shutdownGracefully();
        }
    }

    public void sendPacket(Packet packet) {
        if(!this.isConnected()) return;
        this.channel.writeAndFlush(packet, this.channel.voidPromise());
    }

    public boolean isConnected() {
        return this.channel != null && this.channel.isActive();
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public Channel getChannel() {
        return channel;
    }
}
