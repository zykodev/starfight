package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.protocol.Packet;

public class S02PacketDisconnect implements Packet {

    private String message;

    public S02PacketDisconnect() {
    }

    public S02PacketDisconnect(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
