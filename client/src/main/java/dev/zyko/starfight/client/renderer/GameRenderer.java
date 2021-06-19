package dev.zyko.starfight.client.renderer;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;
import dev.zyko.starfight.client.renderer.model.Model;
import dev.zyko.starfight.client.renderer.model.impl.ModelSpaceship;
import dev.zyko.starfight.client.util.MathHelper;
import dev.zyko.starfight.client.util.TimeHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GameRenderer {

    private GuiScreen currentScreen;
    private TimeHelper frameTimer = new TimeHelper();
    private int framesPerSecond = 0, framesDrawn = 0;
    private long frameTime = System.nanoTime(), prevNanos, postNanos;

    private Model crosshairModel = new Model(StarfightClient.getInstance().getTextureManager().getTexture("ui/crosshair"), 0.1);
    private Model spaceshipModel = new ModelSpaceship(Color.CYAN.getRGB());

    public void renderGame(double partialTicks) {
        this.prevNanos = System.nanoTime();
        if(this.frameTimer.isDelayComplete(1000)) {
            this.framesPerSecond = framesDrawn - 1;
            this.framesDrawn = 0;
            this.frameTimer.updateSystemTime();
        }

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        this.spaceshipModel.applyModelTransformation();
        this.spaceshipModel.drawModel(-64/2, -64/2, 64, 64, partialTicks);

        this.renderOverlay(partialTicks);

        double[] mousePosition = StarfightClient.getInstance().getInputManager().getMousePosition();
        if(this.currentScreen != null) {
            this.currentScreen.drawScreen(mousePosition[0], mousePosition[1], partialTicks);
        }
        this.postNanos = System.nanoTime();
        this.frameTime = this.postNanos - this.prevNanos;
        this.framesDrawn++;
    }

    private void renderOverlay(double partialTicks) {
        double[] mouse = StarfightClient.getInstance().getInputManager().getMousePosition();
        GL11.glPushMatrix();
        // GL11.glColor4f(1,0,1,0);
        this.crosshairModel.drawModel(mouse[0] - 16, mouse[1] - 16, 32, 32, partialTicks);
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

}
