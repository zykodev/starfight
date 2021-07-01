package dev.zyko.starfight.server.world.entity;

public class EntityPowerUp extends TileEntity {

    public static final double TYPE_SPEED = 2.0D;
    public static final double TYPE_HEALTH = 4.0D;
    public static final double TYPE_CDR = 6.0D;

    private double type;

    public EntityPowerUp(int id, double posX, double posY, double type) {
        super(id, posX, posY, 64, 64);
        this.type = type;
    }

    public double getType() {
        return type;
    }

}
