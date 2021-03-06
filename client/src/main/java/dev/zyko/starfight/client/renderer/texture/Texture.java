package dev.zyko.starfight.client.renderer.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Texture {

    private int textureId;
    private int width, height;

    /**
     * Liest eine Textur aus einem InputStream ein.
     *
     * @param inputStream der zu lesende InputStream
     * @throws Exception, falls der Stream nicht gelesen werden konnte.
     */
    public Texture(InputStream inputStream) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        inputStream.close();
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        int[] imageData = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelData = imageData[x * width + y];
                buffer.put((byte) ((pixelData >> 16) & 0xFF));
                buffer.put((byte) ((pixelData >> 8) & 0xFF));
                buffer.put((byte) ((pixelData) & 0xFF));
                buffer.put((byte) ((pixelData >> 24) & 0xFF));
            }
        }
        buffer.flip();

        this.textureId = GL20.glGenTextures();
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, this.textureId);
        GL20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGBA, width, height, 0, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, buffer);
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
    }

    /**
     * Sagt OpenGL, dass für das nächste zu rendernde Element diese Textur verwendet werden soll.
     */
    public void bindTexture() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, this.textureId);
    }

    /**
     * Sagt OpenGL, dass nun keine Textur mehr verwendet werden soll.
     */
    public void unbindTexture() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
    }

}
