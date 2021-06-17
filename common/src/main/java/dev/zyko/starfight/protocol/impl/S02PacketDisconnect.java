package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S02PacketDisconnect implements Packet {

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
    }

}
