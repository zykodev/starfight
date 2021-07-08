package dev.zyko.starfight.client.renderer.model;

import dev.zyko.starfight.client.renderer.texture.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

public class Model {

    private Texture texture;
    protected double zAxis = 0;
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

    private DoubleBuffer createDoubleBuffer(double[] data) {
        DoubleBuffer doubleBuffer = BufferUtils.createDoubleBuffer(data.length);
        doubleBuffer.put(data);
        doubleBuffer.flip();
        return doubleBuffer;
    }

    private IntBuffer createIntBuffer(int[] data) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(data.length);
        intBuffer.put(data);
        intBuffer.flip();
        return intBuffer;
    }

}
