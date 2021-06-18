package dev.zyko.starfight.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public abstract class Packet {

    private String signature;

    public abstract void write(ByteBuf byteBuf) throws IOException;
    public abstract void read(ByteBuf byteBuf) throws IOException;

    public String getSignature() {
        return signature;
    }

    public void sign(ByteBuf byteBuf, String signature) {
        PacketUtil.writeString(byteBuf, signature);
    }

    public void readSignature(ByteBuf byteBuf) {
        this.signature = PacketUtil.readString(byteBuf);
    }

}
