package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S04PacketPlayOutEntitySpawn extends Packet {

    public static final int SPACESHIP = 1;

    private int entityId, type;
    private double posX, posY, rotation;
    private String name;

    public S04PacketPlayOutEntitySpawn(int entityId, int type, double posX, double posY, double rotation, String name) {
        this.entityId = entityId;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
        this.name = name;
    }

    public S04PacketPlayOutEntitySpawn() {
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.entityId = byteBuf.readInt();
        this.type = byteBuf.readInt();
        this.posX = byteBuf.readDouble();
        this.posY = byteBuf.readDouble();
        this.rotation = byteBuf.readDouble();
        this.name = PacketUtil.readString(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        byteBuf.writeInt(this.entityId);
        byteBuf.writeInt(this.type);
        byteBuf.writeDouble(this.posX);
        byteBuf.writeDouble(this.posY);
        byteBuf.writeDouble(this.rotation);
        PacketUtil.writeString(byteBuf, this.name);
    }

}
