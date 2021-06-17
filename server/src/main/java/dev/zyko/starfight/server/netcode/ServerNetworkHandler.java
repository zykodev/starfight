package dev.zyko.starfight.server.netcode;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.S01PacketKeepAlive;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerNetworkHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;

    public ServerNetworkHandler(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if(msg instanceof C01PacketKeepAlive) {
            this.channel.writeAndFlush(new S01PacketKeepAlive(((C01PacketKeepAlive) msg).getSystemTime()), channel.voidPromise());
        }
    }

}
