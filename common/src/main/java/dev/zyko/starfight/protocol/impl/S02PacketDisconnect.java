package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S02PacketDisconnect implements Packet {

    private String message;

    public S02PacketDisconnect() {
    }

    public S02PacketDisconnect(String message) {
        this.message = message;
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        PacketUtil.writeString(byteBuf, this.message);
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.message = PacketUtil.readString(byteBuf);
    }

    public String getMessage() {
        return message;
    }

}
