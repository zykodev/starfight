package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S10PacketPlayOutPowerUpStatus implements Packet {

    private double type;
    private int duration;

    public S10PacketPlayOutPowerUpStatus() {
    }

    public S10PacketPlayOutPowerUpStatus(double type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public double getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

}
