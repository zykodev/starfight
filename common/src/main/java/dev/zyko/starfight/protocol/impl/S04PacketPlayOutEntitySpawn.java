package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S04PacketPlayOutEntitySpawn implements Packet {

    public static final int SPACESHIP = 1;
    public static final int POWER_UP = 2;
    public static final int PROJECTILE = 3;

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

    public int getEntityId() {
        return entityId;
    }

    public int getType() {
        return type;
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

    public String getName() {
        return name;
    }
}
