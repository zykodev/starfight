package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.renderer.model.Model;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public abstract class Entity {

    protected int id;
    protected double posX, posY, width, height;
    protected Model model;

    public Entity(int id, double posX, double posY, double width, double height, Model model) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.model = model;
    }

    public abstract void updateEntity();

    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        GL11.glRotated(rotation, 0, 0, 1);
        this.enableAdditionalFeatures();
        this.getModel().drawModel(-width / 2, -height / 2, width, height, partialTicks);
        this.disableAdditionalFeatures();
        GL11.glPopMatrix();
    }

    protected void enableAdditionalFeatures() {
    }

    protected void disableAdditionalFeatures() {
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

    public double getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
