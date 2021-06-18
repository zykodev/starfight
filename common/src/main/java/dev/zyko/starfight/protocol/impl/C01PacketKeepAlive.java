package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C01PacketKeepAlive extends Packet {

    private long systemTime;

    public C01PacketKeepAlive() {}
    public C01PacketKeepAlive(long systemTime) { this.systemTime = systemTime; }

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
