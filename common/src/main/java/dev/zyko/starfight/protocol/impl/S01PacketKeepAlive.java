package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S01PacketKeepAlive implements Packet {

    private long systemTime;

    public S01PacketKeepAlive() {}
    public S01PacketKeepAlive(long systemTime) { this.systemTime = systemTime; }

    public long getSystemTime() {
        return systemTime;
    }

}
