package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S01PacketKeepAlive implements Packet {

    private long systemTime;

    public S01PacketKeepAlive() {}
    public S01PacketKeepAlive(long systemTime) { this.systemTime = systemTime; }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.systemTime = byteBuf.readLong();
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        byteBuf.writeLong(this.systemTime);
    }

    public long getSystemTime() {
        return systemTime;
    }

}
