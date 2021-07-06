package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S07PacketPlayOutEntityHealth implements Packet {

    private int entityId, health;

    public S07PacketPlayOutEntityHealth(int entityId, int health) {
        this.entityId = entityId;
        this.health = health;
    }

    public S07PacketPlayOutEntityHealth() {
    }

    public int getEntityId() {
        return entityId;
    }

    public int getHealth() {
        return health;
    }

}
