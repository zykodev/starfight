package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class C03PacketConnect implements Packet {

    private String nickname;
    private String version;

    public C03PacketConnect() {
    }

    public C03PacketConnect(String nickname, String version) {
        this.nickname = nickname;
        this.version = version;
    }

    public String getNickname() {
        return nickname;
    }

    public String getVersion() {
        return version;
    }

}
