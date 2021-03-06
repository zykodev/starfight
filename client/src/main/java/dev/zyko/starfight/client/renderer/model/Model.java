package dev.zyko.starfight.client.renderer.model;

import dev.zyko.starfight.client.renderer.texture.Texture;
import org.lwjgl.opengl.GL11;

public class Model {

    protected double zAxis = 0;
    private Texture texture;
    private double[][] textureCoordinates;

    public Model(Texture texture, double[][] textureCoordinates, double zAxis) {
        this.texture = texture;
        this.textureCoordinates = textureCoordinates;
        this.zAxis = zAxis;
    }

    public Model(Texture texture, double zAxis) {
        this(texture, new double[][]{
                {0, 0}, {1, 0}, {1, 1}, {0, 1}
        }, 0);
    }

    public Model(Texture texture) {
        this(texture, new double[][]{
                {0, 0}, {1, 0}, {1, 1}, {0, 1}
        }, 0);
    }

    /**
     * Rendert das Model auf den Bildschirm.
     *
     * @param x            x-Koordinate, an welcher das Model angezeigt werden soll.
     * @param y            y-Koordinate, an welcher das Model angezeigt werden soll.
     * @param width        Breite des Models.
     * @param height       Höhe des Models.
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    public void drawModel(double x, double y, double width, double height, double partialTicks) {
        this.texture.bindTexture();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2d(this.textureCoordinates[0][0], this.textureCoordinates[0][1]);
            GL11.glVertex3d(x, y, this.zAxis);

            GL11.glTexCoord2d(this.textureCoordinates[1][0], this.textureCoordinates[1][1]);
            GL11.glVertex3d(x + width, y, this.zAxis);

            GL11.glTexCoord2d(this.textureCoordinates[2][0], this.textureCoordinates[2][1]);
            GL11.glVertex3d(x + width, y + height, this.zAxis);

            GL11.glTexCoord2d(this.textureCoordinates[3][0], this.textureCoordinates[3][1]);
            GL11.glVertex3d(x, y + height, this.zAxis);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        this.texture.unbindTexture();
    }

}
