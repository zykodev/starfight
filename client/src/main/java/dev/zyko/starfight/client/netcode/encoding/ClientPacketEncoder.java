package dev.zyko.starfight.client.netcode.encoding;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public class ClientPacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        int packetId = PacketRegistry.CLIENT_PACKETS.indexOf(msg.getClass());
        if(packetId == -1) {
            throw new IOException("Tried to encode a packet with an unknown id.");
        }
        out.writeInt(packetId);
        msg.sign(out, StarfightClient.SIGNATURE);
        msg.write(out);
    }

}
