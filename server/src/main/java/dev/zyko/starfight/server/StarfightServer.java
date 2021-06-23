package dev.zyko.starfight.server;

import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;
import dev.zyko.starfight.server.netcode.encoding.ServerPacketDecoder;
import dev.zyko.starfight.server.netcode.encoding.ServerPacketEncoder;
import dev.zyko.starfight.server.world.World;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.UUID;

public class StarfightServer {

    public static final String VERSION = "alpha-indev";
    public static final boolean EPOLL = Epoll.isAvailable();
    public static final String SIGNATURE = UUID.randomUUID().toString().replace("-", "");

    private static StarfightServer instance;
    private StarfightLogger logger = new StarfightLogger();

    private final World world;

    public StarfightServer() throws Exception {
        this.logger.log(this.getClass(), "Starting Starfight server on port 26800...");
        instance = this;
        this.world = new World(2000.0D);
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            logger.log(this.getClass(), "[+] Connection: " + ch.remoteAddress().toString());
                            ch.pipeline().addLast("encoder", new ServerPacketEncoder()).addLast("decoder", new ServerPacketDecoder()).addLast("nethandler", new ServerNetworkHandler(ch));
                        }

                        @Override
                        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                            logger.log(this.getClass(), "[-] Connection: " + ctx.channel().remoteAddress().toString());
                            super.channelUnregistered(ctx);
                        }
                    })
                    .bind(26800).sync().channel().closeFuture().syncUninterruptibly();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static StarfightServer getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        try {
            StarfightServer server = new StarfightServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StarfightLogger getLogger() {
        return logger;
    }

    public World getWorld() {
        return world;
    }

}
