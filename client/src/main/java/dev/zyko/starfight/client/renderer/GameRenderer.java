package dev.zyko.starfight.client.renderer;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiOverlay;
import dev.zyko.starfight.client.gui.GuiScreen;
import dev.zyko.starfight.client.renderer.font.FontRenderer;
import dev.zyko.starfight.client.renderer.model.Model;
import dev.zyko.starfight.client.renderer.particle.ParticleRenderer;
import dev.zyko.starfight.client.renderer.texture.Texture;
import dev.zyko.starfight.client.renderer.texture.TextureManager;
import dev.zyko.starfight.client.util.ColorUtil;
import dev.zyko.starfight.util.TimeHelper;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class GameRenderer {

    private final Model crosshairModel = new Model(StarfightClient.getInstance().getTextureManager().getTexture("ui/crosshair"), 0.1);
    private ParticleRenderer particleRenderer;
    private GuiScreen currentScreen;
    private TimeHelper frameTimer = new TimeHelper();
    private int framesPerSecond = 0, framesDrawn = 0;
    private long frameTime = System.nanoTime(), prevNanos, postNanos;
    private DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public GameRenderer() {
        this.particleRenderer = new ParticleRenderer();
    }

    /**
     * Rendert alles zum Spiel Zugehörige.
     *
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    public void renderGame(double partialTicks) {
        this.prevNanos = System.nanoTime();
        if (this.frameTimer.isDelayComplete(1000)) {
            this.framesPerSecond = framesDrawn - 1;
            this.framesDrawn = 0;
            this.frameTimer.updateSystemTime();
        }

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        this.renderOverlay(partialTicks);

        double[] mousePosition = StarfightClient.getInstance().getInputManager().getMousePosition();
        if (this.currentScreen != null) {
            if (this.currentScreen instanceof GuiOverlay) {
                if (StarfightClient.getInstance().getWorld() != null) {
                    StarfightClient.getInstance().getWorld().renderWorld(partialTicks);
                }
            }
            this.currentScreen.drawScreen(mousePosition[0], mousePosition[1], partialTicks);
        } else {
            if (StarfightClient.getInstance().getWorld() != null) {
                StarfightClient.getInstance().getWorld().renderWorld(partialTicks);
            }
        }

        this.renderDebugOverlay(partialTicks);
        this.renderPointer(partialTicks);
        this.postNanos = System.nanoTime();
        this.frameTime = this.postNanos - this.prevNanos;
        this.framesDrawn++;
    }

    private void renderOverlay(double partialTicks) {
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        if(StarfightClient.getInstance().getPlayerSpaceship() != null) {
            Texture texture = null;
            if(StarfightClient.getInstance().getPlayerSpaceship().getActivePowerUp() == 2.0D) {
                texture = StarfightClient.getInstance().getTextureManager().getTexture("entity/powerup_speed");
            }
            if(StarfightClient.getInstance().getPlayerSpaceship().getActivePowerUp() == 6.0D) {
                texture = StarfightClient.getInstance().getTextureManager().getTexture("entity/powerup_cdr");
            }
            this.drawRectangle(3, screenHeight - 3 - 64, 64, 64, 0x80111111);
            if(texture != null) {
                GL11.glPushMatrix();
                texture.bindTexture();
                this.drawTexturableRectangle(19, screenHeight - 3 - 64, 48, 48);
                texture.unbindTexture();
                GL11.glPopMatrix();
            }
            if(StarfightClient.getInstance().getPlayerSpaceship().getActivePowerUp() > 0) {
                double remainingSeconds = (double) StarfightClient.getInstance().getPlayerSpaceship().getRemainingPowerUpTicks() / 48.0D;
                StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawStringWithShadow(this.decimalFormat.format(remainingSeconds) + "s", 3, (float) (screenHeight - 3 - 18), -1);
            }
        }
    }

    /**
     * Zeigt den Mauszeiger an.
     *
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    private void renderPointer(double partialTicks) {
        double[] mouse = StarfightClient.getInstance().getInputManager().getMousePosition();
        GL11.glPushMatrix();
        // GL11.glColor4f(1,0,1,0);
        this.crosshairModel.drawModel(mouse[0] - 16, mouse[1] - 16, 32, 32, partialTicks);
        GL11.glPopMatrix();
    }

    /**
     * Zeigt Debug-Daten oben links in der Ecke an.
     *
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    private void renderDebugOverlay(double partialTicks) {
        GL11.glPushMatrix();
        FontRenderer fontRenderer = StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext");
        fontRenderer.drawString("client version " + StarfightClient.VERSION, 1, 1, -1);
        fontRenderer.drawString("fps: " + this.framesPerSecond, 1, 1 + 18, -1);
        fontRenderer.drawString("frametime (ms): " + (this.frameTime / 1000000.0D), 1, 1 + 18 + 18, -1);
        fontRenderer.drawString("updates/second: " + StarfightClient.getInstance().getGameTickThread().getUpdatesPerSecond(), 1, 1 + 18 + 18 + 18, -1);
        if (StarfightClient.getInstance().getPlayerSpaceship() != null) {
            fontRenderer.drawString("posX: " + StarfightClient.getInstance().getPlayerSpaceship().getPosX(), 1, 1 + 18 + 18 + 18 + 18, -1);
            fontRenderer.drawString("posY: " + StarfightClient.getInstance().getPlayerSpaceship().getPosY(), 1, 1 + 18 + 18 + 18 + 18 + 18, -1);
            fontRenderer.drawString("latency: " + StarfightClient.getInstance().getNetworkManager().getClientNetworkHandler().getLatency(), 1, 1 + 18 + 18 + 18 + 18 + 18 + 18, -1);
            fontRenderer.drawString("powerup: " + StarfightClient.getInstance().getPlayerSpaceship().getActivePowerUp() + ", " + StarfightClient.getInstance().getPlayerSpaceship().getRemainingPowerUpTicks(), 1, 1 + 18 + 18 + 18 + 18 + 18 + 18 + 18, -1);
        }
        GL11.glPopMatrix();
    }

    /**
     * Zeichnet ein Rechteck.
     *
     * @param x      x-Koordinate der Ecke des Rechtecks oben links.
     * @param y      y-Koordinate der Ecke des Rechtecks oben links.
     * @param width  Breite des Rechtecks.
     * @param height Höhe des Rechtecks.
     * @param color  Farbe des Rechtecks.
     */
    public void drawRectangle(double x, double y, double width, double height, int color) {
        float[] colorArray = ColorUtil.hexToRGBA(color);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x + width, y);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x, y + height);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
    }

    /**
     * Zeichnen einen nicht ausgefüllten Kreis.
     *
     * @param centerX   x-Koordinate des Mittelpunkts des Kreises.
     * @param centerY   y-Koordinate des Mittelpunkts des Kreises.
     * @param radius    Radius des Kreises.
     * @param color     Farbe des Linie.
     * @param thickness Dicke der Linie.
     */
    public void drawCircle(double centerX, double centerY, double radius, int color, float thickness) {
        float[] colorArray = ColorUtil.hexToRGBA(color);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
        GL11.glLineWidth(thickness);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            for (int i = 0; i <= 360; i++) {
                double vertexX = centerX + Math.sin(Math.toRadians(i)) * radius;
                double vertexY = centerY + Math.cos(Math.toRadians(i)) * radius;
                GL11.glVertex3d(vertexX, vertexY, 0);
            }
        }
        GL11.glEnd();
        GL11.glLineWidth(1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
    }

    /**
     * Zeichnet ein Rechteck, auf welches eine Textur angewendet werden kann.
     *
     * @param x      x-Koordinate der Ecke des Rechtecks oben links.
     * @param y      y-Koordinate der Ecke des Rechtecks oben links.
     * @param width  Breite des Rechtecks.
     * @param height Höhe des Rechtecks.
     */
    public void drawTexturableRectangle(double x, double y, double width, double height) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2d(x, y);

            GL11.glTexCoord2d(1, 0);
            GL11.glVertex2d(x + width, y);

            GL11.glTexCoord2d(1, 1);
            GL11.glVertex2d(x + width, y + height);

            GL11.glTexCoord2d(0, 1);
            GL11.glVertex2d(x, y + height);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void displayGuiScreen(GuiScreen guiScreen) {
        this.currentScreen = guiScreen;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public long getFrameTime() {
        return frameTime;
    }

    public GuiScreen getCurrentScreen() {
        return currentScreen;
    }

    public ParticleRenderer getParticleRenderer() {
        return particleRenderer;
    }

}
