package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class C04PacketPlayOutPlayerData implements Packet {

    private double rotation;

    public C04PacketPlayOutPlayerData() {}

    public C04PacketPlayOutPlayerData(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

}
