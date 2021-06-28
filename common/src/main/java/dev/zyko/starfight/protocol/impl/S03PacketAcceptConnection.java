package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S03PacketAcceptConnection implements Packet {

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
