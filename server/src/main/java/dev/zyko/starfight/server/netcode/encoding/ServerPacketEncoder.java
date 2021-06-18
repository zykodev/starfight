package dev.zyko.starfight.server.netcode.encoding;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.server.StarfightServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public class ServerPacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        int packetId = PacketRegistry.SERVER_PACKETS.indexOf(msg.getClass());
        if(packetId == -1) {
            throw new IOException("Tried to encode a packet with an unknown id.");
        }
        out.writeInt(packetId);
        msg.sign(out, StarfightServer.SIGNATURE);
        msg.write(out);
    }

}
