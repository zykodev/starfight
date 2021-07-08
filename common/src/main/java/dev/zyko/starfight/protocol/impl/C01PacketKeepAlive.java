package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class C01PacketKeepAlive implements Packet {

    private long systemTime;

    public C01PacketKeepAlive() {
    }

    public C01PacketKeepAlive(long systemTime) {
        this.systemTime = systemTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

}
