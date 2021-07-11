package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.renderer.model.Model;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public abstract class Entity {

    /**
     * ID, damit jedes Entity sowohl vom Client als auch vom Server genau "benannt" werden kann
     */
    protected int id;

    /**
     * Position und Größe
     */
    protected double posX, posY, width, height;

    /**
     * Zu verwendende Textur
     */
    protected Model model;

    public Entity(int id, double posX, double posY, double width, double height, Model model) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.model = model;
    }

    /**
     * Aktualisiert Client-seitig die Daten von lokalen Entities.
     */
    public abstract void updateEntity();

    /**
     * Zeigt das Entity im Spiel an.
     *
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     * @param x            x-Koordinate, an welchem das Entity angezeigt werden soll.
     * @param y            y-Koordinate, an welchem das Entity angezeigt werden soll.
     * @param width        Breite des Entitys.
     * @param height       Höhe das Entitys.
     * @param rotation     Rotation des Entitys auf der x-y-Ebene.
     */
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        GL11.glRotated(rotation, 0, 0, 1);
        this.enableAdditionalFeatures();
        this.getModel().drawModel(-width / 2, -height / 2, width, height, partialTicks);
        this.disableAdditionalFeatures();
        GL11.glPopMatrix();
    }

    /**
     * Werden bei einem bestimmten Entity weitere OpenGL-Caps benötigt, so können diese hier durch Überschreiben der Methode implementiert werden.
     */
    protected void enableAdditionalFeatures() {
    }

    /**
     * Werden bei einem bestimmten Entity weitere OpenGL-Caps benötigt, so können diese hier durch Überschreiben der Methode implementiert werden.
     */
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
