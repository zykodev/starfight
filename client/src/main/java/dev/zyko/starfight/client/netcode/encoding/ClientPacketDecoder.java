package dev.zyko.starfight.client.netcode.encoding;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class ClientPacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("Received a packet.");
        Class<? extends Packet> packetClass = PacketRegistry.SERVER_PACKETS.get(in.readInt());
        if(packetClass == null) {
            throw new IOException("Received an invalid packet from server.");
        }
        Packet packet = packetClass.newInstance();
        packet.readSignature(in);
        packet.read(in);
        out.add(packet);
    }

}
