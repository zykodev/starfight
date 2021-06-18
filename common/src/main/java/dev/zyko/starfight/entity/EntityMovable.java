package dev.zyko.starfight.entity;

public class EntityMovable extends Entity {

    private double rotation;
    private double lastPosX, lastPosY;

    public EntityMovable(int id, double posX, double posY, double width, double height, double rotation) {
        super(id, posX, posY, width, height);
        this.rotation = rotation;
        this.lastPosX = posX;
        this.lastPosY = posY;
    }

    @Override
    public void updateEntity() {
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
}
