package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S03PacketAcceptConnection extends Packet {

    private int entityId;
    private String nickname;
    private double posX, posY, worldRadius;

    public S03PacketAcceptConnection() {
    }

    public S03PacketAcceptConnection(int entityId, String nickname, double posX, double posY, double worldRadius) {
        this.entityId = entityId;
        this.nickname = nickname;
        this.posX = posX;
        this.posY = posY;
        this.worldRadius = worldRadius;
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        this.entityId = byteBuf.readInt();
        this.nickname = PacketUtil.readString(byteBuf);
        this.posX = byteBuf.readDouble();
        this.posY = byteBuf.readDouble();
        this.worldRadius = byteBuf.readDouble();
    }

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        byteBuf.writeInt(this.entityId);
        PacketUtil.writeString(byteBuf, this.nickname);
        byteBuf.writeDouble(this.posX);
        byteBuf.writeDouble(this.posY);
        byteBuf.writeDouble(this.worldRadius);
    }

    public int getEntityId() {
        return entityId;
    }

    public String getNickname() {
        return nickname;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getWorldRadius() {
        return worldRadius;
    }
}
