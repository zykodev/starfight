package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C04PacketPlayOutPlayerData extends Packet {

    private double rotation;

    public C04PacketPlayOutPlayerData() {}

    public C04PacketPlayOutPlayerData(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        byteBuf.writeDouble(this.rotation);
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.rotation = byteBuf.readDouble();
    }

    public double getRotation() {
        return rotation;
    }

}
