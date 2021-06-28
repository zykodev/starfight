package dev.zyko.starfight.server.netcode;

import com.esotericsoftware.kryonet.Connection;
import dev.zyko.starfight.server.world.entity.EntityPlayerSpaceship;

public class PlayerConnection extends Connection {

    private EntityPlayerSpaceship playerSpaceship;

    public EntityPlayerSpaceship getPlayerSpaceship() {
        return playerSpaceship;
    }

    public void setPlayerSpaceship(EntityPlayerSpaceship playerSpaceship) {
        this.playerSpaceship = playerSpaceship;
    }
}
