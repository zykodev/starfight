package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S05PacketPlayOutEntityPosition implements Packet {

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
