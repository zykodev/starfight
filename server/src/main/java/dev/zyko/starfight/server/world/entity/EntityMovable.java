package dev.zyko.starfight.server.world.entity;

import dev.zyko.starfight.server.StarfightServer;

public class EntityMovable extends Entity {

    protected double rotation;
    protected double lastPosX, lastPosY;
    private double speed = 1;

    public EntityMovable(int id, double posX, double posY, double width, double height, double rotation) {
        super(id, posX, posY, width, height);
        this.rotation = rotation;
        this.lastPosX = posX;
        this.lastPosY = posY;
    }

    @Override
    public void updateEntity() {
        double newPosX = this.posX - Math.sin(Math.toRadians(this.rotation)) * 4 * speed;
        double newPosY = this.posY + Math.cos(Math.toRadians(this.rotation)) * 4 * speed;
        double distance = Math.hypot(Math.abs(newPosX), Math.abs(newPosY));
        if (distance >= StarfightServer.getInstance().getWorld().getRadius()) {
            return;
        }
        this.posX = newPosX;
        this.posY = newPosY;
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

    public double getLastPosX() {
        return lastPosX;
    }

    public double getLastPosY() {
        return lastPosY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
