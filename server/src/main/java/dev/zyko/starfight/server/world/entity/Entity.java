package dev.zyko.starfight.server.world.entity;

import java.util.Objects;

public abstract class Entity {

    protected int id;
    protected double posX, posY, width, height;

    public Entity(int id, double posX, double posY, double width, double height) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void updateEntity();

    public boolean isCollidingWith(Entity e) {
        return false;
    }

    public double distanceTo(Entity e) {
        double diffX = Math.abs(this.posX - e.posX);
        double diffY = Math.abs(this.posY - e.posY);
        return Math.hypot(diffX, diffY);
    }

    public double distanceTo(TileEntity e) {
        double diffX = Math.abs(this.posX - e.posX);
        double diffY = Math.abs(this.posY - e.posY);
        return Math.hypot(diffX, diffY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
