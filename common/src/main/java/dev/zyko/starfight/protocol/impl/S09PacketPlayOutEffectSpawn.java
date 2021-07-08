package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S09PacketPlayOutEffectSpawn implements Packet {

    private int entityId;
    private double posX, posY;

    public S09PacketPlayOutEffectSpawn(int entityId, double posX, double posY) {
        this.entityId = entityId;
        this.posX = posX;
        this.posY = posY;
    }

    public S09PacketPlayOutEffectSpawn() {
    }

    public int getEntityId() {
        return entityId;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

}
