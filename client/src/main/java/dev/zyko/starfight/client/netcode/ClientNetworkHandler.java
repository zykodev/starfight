package dev.zyko.starfight.client.netcode;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.S01PacketKeepAlive;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientNetworkHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;

    public ClientNetworkHandler(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if(msg instanceof S01PacketKeepAlive) {
            long timeDiff =  System.currentTimeMillis() - ((S01PacketKeepAlive) msg).getSystemTime();
            StarfightClient.getInstance().setPingTime(timeDiff);
            System.out.println("Ping: " + timeDiff);
        }
    }

}