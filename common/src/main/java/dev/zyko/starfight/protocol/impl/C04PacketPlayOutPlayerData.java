package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class C04PacketPlayOutPlayerData implements Packet {

    private double rotation;
    private boolean shooting, usingPowerup;

    public C04PacketPlayOutPlayerData() {}

    public C04PacketPlayOutPlayerData(double rotation, boolean shooting, boolean usingPowerup) {
        this.rotation = rotation;
        this.shooting = shooting;
        this.usingPowerup = usingPowerup;
    }

    public boolean isShooting() {
        return shooting;
    }

    public boolean isUsingPowerup() {
        return usingPowerup;
    }

    public double getRotation() {
        return rotation;
    }

}
