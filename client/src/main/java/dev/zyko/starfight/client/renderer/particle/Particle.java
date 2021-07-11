package dev.zyko.starfight.client.renderer.particle;

public class Particle {

    private int color;
    private double rotation;
    private double posX, posY, lastX, lastY;

    public Particle(int color, double rotation, double posX, double posY) {
        this.color = color;
        this.rotation = rotation;
        this.posX = posX;
        this.posY = posY;
    }

    public void onTick() {
        this.lastX = this.posX;
        this.lastY = this.posY;
        this.posX += Math.sin(Math.toRadians(this.rotation)) * 0.5;
        this.posY += Math.cos(Math.toRadians(this.rotation)) * 0.5;
    }

    public int getColor() {
        return color;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

}
