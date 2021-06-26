package dev.zyko.starfight.server.world.entity;

import dev.zyko.starfight.server.netcode.ServerNetworkHandler;

public class EntityPlayerSpaceship extends EntitySpaceship {

    private ServerNetworkHandler netHandler;

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name, ServerNetworkHandler netHandler) {
        super(id, posX, posY, rotation, name);
        this.name = name;
        this.health = 3;
        this.netHandler = netHandler;
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

    public ServerNetworkHandler getNetHandler() {
        return netHandler;
    }

}
