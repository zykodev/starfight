package dev.zyko.starfight.client.renderer;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;
import dev.zyko.starfight.client.util.TimeHelper;
import org.lwjgl.opengl.GL11;

public class GameRenderer {

    private GuiScreen currentScreen;
    private TimeHelper frameTimer = new TimeHelper();
    private int framesPerSecond = 0, framesDrawn = 0;
    private long frameTime = System.nanoTime(), prevNanos, postNanos;

    public void renderGame(float partialTicks) {
        this.prevNanos = System.nanoTime();
        if(this.frameTimer.isDelayComplete(1000)) {
            this.framesPerSecond = framesDrawn - 1;
            this.framesDrawn = 0;
            this.frameTimer.updateSystemTime();
        }
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        double[] mousePosition = StarfightClient.getInstance().getInputManager().getMousePosition();
        if(this.currentScreen != null) {
            this.currentScreen.drawScreen(mousePosition[0], mousePosition[1], partialTicks);
        }
        this.postNanos = System.nanoTime();
        this.frameTime = this.postNanos - this.prevNanos;
        this.framesDrawn++;
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
