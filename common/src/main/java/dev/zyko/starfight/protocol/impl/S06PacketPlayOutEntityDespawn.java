package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S06PacketPlayOutEntityDespawn implements Packet {

    private int entityId;

    public S06PacketPlayOutEntityDespawn() {
    }

    public S06PacketPlayOutEntityDespawn(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

}
