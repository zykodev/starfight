package dev.zyko.starfight.server.world.entity;

public class EntitySpaceship extends EntityMovable {

    protected int health;
    protected String name;

    public EntitySpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, 64, 64, rotation);
        this.name = name;
        this.health = 3;
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

}
