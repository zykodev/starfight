package dev.zyko.starfight.client.renderer.font;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.util.ColorUtil;
import dev.zyko.starfight.client.util.IOHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class FontRenderer {

    private final int charMallocSize = 96;
    private final int fontTextureWidth = 512;
    private final int fontTextureHeight = fontTextureWidth;
    private final int bakeFirstChar = 32;
    private final int glyphCount = charMallocSize;

    protected final STBTTBakedChar.Buffer charData;
    protected final STBTTFontinfo fontInfo;
    protected final int height, textureId;
    protected final float ascent, descent, gap;
    protected final String name;

    public FontRenderer(String fontName, InputStream fontData, int height) {
        this.name = fontName;
        this.height = height;
        this.charData = STBTTBakedChar.malloc(this.charMallocSize);
        this.fontInfo = STBTTFontinfo.create();
        int textureId = 0;
        float ascent = 0, descent = 0, gap = 0;
        try {
            ByteBuffer fontBuffer = IOHelper.streamToByteBuffer(fontData);
            ByteBuffer textureBuffer = BufferUtils.createByteBuffer(this.fontTextureHeight * this.fontTextureWidth);
            int result = STBTruetype.stbtt_BakeFontBitmap(fontBuffer, this.height, textureBuffer, this.fontTextureWidth, this.fontTextureHeight, this.bakeFirstChar, this.charData);
            if(result < 1) {
                System.out.println("Failed to create font \"" + fontName + "\".");
            }
            try (MemoryStack memoryStack = MemoryStack.stackPush()) {
                STBTruetype.stbtt_InitFont(this.fontInfo, fontBuffer);
                float pixelScale = STBTruetype.stbtt_ScaleForPixelHeight(this.fontInfo, this.height);
                IntBuffer bufAscent = memoryStack.ints(0);
                IntBuffer bufDescent = memoryStack.ints(0);
                IntBuffer bufLineGap = memoryStack.ints(0);
                STBTruetype.stbtt_GetFontVMetrics(fontInfo, bufAscent, bufDescent, bufLineGap);
                ascent = bufAscent.get(0) * pixelScale;
                descent = bufDescent.get(0) * pixelScale;
                gap = bufLineGap.get(0) * pixelScale;
            }
            textureId = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_ALPHA, this.fontTextureWidth, this.fontTextureHeight, 0, GL11.GL_ALPHA, GL11.GL_UNSIGNED_BYTE, textureBuffer);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.textureId = textureId;
        this.ascent = ascent;
        this.gap = gap;
        this.descent = descent;
    }

    public STBTTBakedChar.Buffer getCharData() {
        return charData;
    }

    public float getAscent() {
        return ascent;
    }

    public void drawString(String text, float x, float y, int color) {
        float[] cArray = ColorUtil.hexToRGBA(color);
        int fontSize = this.height;
        y += this.getAscent();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer bufX = stack.floats(x);
            FloatBuffer bufY = stack.floats(y);

            STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);
            STBTTBakedChar.Buffer charData = this.getCharData();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);

            GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glColor4f(cArray[0], cArray[1], cArray[2], cArray[3]);

            int firstCP = this.bakeFirstChar;
            int lastCP = this.bakeFirstChar + this.glyphCount - 1;
            for (int i = 0; i < text.length(); i++) {
                int codePoint = text.codePointAt(i);
                if (codePoint == '\n') {
                    bufX.put(0, x);
                    bufY.put(0, y + bufY.get(0) + fontSize);
                    continue;
                } else if (codePoint < firstCP || codePoint > lastCP) {
                    continue;
                }
                STBTruetype.stbtt_GetBakedQuad(charData,
                        this.fontTextureWidth, this.fontTextureHeight,
                        codePoint - firstCP,
                        bufX, bufY, q, true);


                GL11.glTexCoord2f(q.s0(), q.t0());
                GL11.glVertex2f(q.x0(), q.y0());

                GL11.glTexCoord2f(q.s0(), q.t1());
                GL11.glVertex2f(q.x0(), q.y1());

                GL11.glTexCoord2f(q.s1(), q.t1());
                GL11.glVertex2f(q.x1(), q.y1());

                GL11.glTexCoord2f(q.s1(), q.t1());
                GL11.glVertex2f(q.x1(), q.y1());

                GL11.glTexCoord2f(q.s1(), q.t0());
                GL11.glVertex2f(q.x1(), q.y0());

                GL11.glTexCoord2f(q.s0(), q.t0());
                GL11.glVertex2f(q.x0(), q.y0());
            }
            GL11.glEnd();
            GL11.glColor4f(1,1,1,1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    public float getStringWidth(String text) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer bufX = stack.floats(0);
            FloatBuffer bufY = stack.floats(0);
            STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);
            STBTTBakedChar.Buffer charData = this.getCharData();
            int firstCP = this.bakeFirstChar;
            int lastCP = this.bakeFirstChar + this.glyphCount - 1;
            for (int i = 0; i < text.length(); i++) {
                int codePoint = text.codePointAt(i);
                if (codePoint == '\n') {
                    bufX.put(0, 0);
                    bufY.put(0, 0 + bufY.get(0) + this.height);
                    continue;
                } else if (codePoint < firstCP || codePoint > lastCP) {
                    continue;
                }
                STBTruetype.stbtt_GetBakedQuad(charData,
                        this.fontTextureWidth, this.fontTextureHeight,
                        codePoint - firstCP,
                        bufX, bufY, q, true);
            }
            return bufX.get() + 1;
        }
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(text, x + 1.5F, y + 1.5F, new Color(color).darker().darker().darker().getRGB());
        this.drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        y -= 1;
        double w = this.getStringWidth(text);
        x -= w / 2.0F;
        this.drawString(text, x, y, color);
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawCenteredString(text, x + 1.5F, y + 1.5F, new Color(color).darker().darker().darker().getRGB());
        this.drawCenteredString(text, x, y, color);
    }

}
