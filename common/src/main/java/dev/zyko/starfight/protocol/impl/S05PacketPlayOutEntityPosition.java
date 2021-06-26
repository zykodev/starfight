package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S05PacketPlayOutEntityPosition extends Packet {

    private int id;
    private double posX, posY, rotation;

    public S05PacketPlayOutEntityPosition() {
    }

    public S05PacketPlayOutEntityPosition(int id, double posX, double posY, double rotation) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        byteBuf.writeInt(this.id);
        byteBuf.writeDouble(this.posX);
        byteBuf.writeDouble(this.posY);
        byteBuf.writeDouble(this.rotation);
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.id = byteBuf.readInt();
        this.posX = byteBuf.readDouble();
        this.posY = byteBuf.readDouble();
        this.rotation = byteBuf.readDouble();
    }

    public int getId() {
        return id;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getRotation() {
        return rotation;
    }
}
