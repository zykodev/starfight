package dev.zyko.starfight.server.world.entity;

public class EntityMovable extends Entity {

    protected double rotation;
    protected double lastPosX, lastPosY;

    public EntityMovable(int id, double posX, double posY, double width, double height, double rotation) {
        super(id, posX, posY, width, height);
        this.rotation = rotation;
        this.lastPosX = posX;
        this.lastPosY = posY;
    }

    @Override
    public void updateEntity() {
        this.posX += -Math.sin(Math.toRadians(this.rotation)) * 4;
        this.posY += Math.cos(Math.toRadians(this.rotation)) * 4;
    }

    @Override
    public void setPosX(double posX) {
        this.lastPosX = this.getPosX();
        super.setPosX(posX);
    }

    @Override
    public void setPosY(double posY) {
        this.lastPosY = this.getPosY();
        super.setPosY(posY);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
