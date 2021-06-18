package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C03PacketConnect implements Packet {

    private String nickname;
    private String version;

    public C03PacketConnect() {}

    public C03PacketConnect(String nickname, String version) {
        this.nickname = nickname;
        this.version = version;
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.version = PacketUtil.readString(byteBuf);
        this.nickname = PacketUtil.readString(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        PacketUtil.writeString(byteBuf, this.version);
        PacketUtil.writeString(byteBuf, this.nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public String getVersion() {
        return version;
    }

}
