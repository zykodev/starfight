package dev.zyko.starfight.server.world.entity;

import com.esotericsoftware.kryonet.Connection;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.server.netcode.PlayerConnection;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;

public class EntityPlayerSpaceship extends EntitySpaceship {

    private PlayerConnection connection;

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name, PlayerConnection playerConnection) {
        super(id, posX, posY, rotation, name);
        this.name = name;
        this.health = 3;
        this.connection = playerConnection;
        this.connection.setPlayerSpaceship(this);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    public String getName() {
        return name;
    }

    public PlayerConnection getConnection() {
        return connection;
    }

    public void sendPacket(Packet packet) {
        if(this.connection.isConnected()) {
            this.connection.sendTCP(packet);
        }
    }

}
