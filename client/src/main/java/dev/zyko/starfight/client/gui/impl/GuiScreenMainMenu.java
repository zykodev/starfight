package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class GuiScreenMainMenu extends GuiScreen {

    private float time;

    @Override
    public void runTick(double mouseX, double mouseY) {
        this.time += 0.005F;
        super.runTick(mouseX, mouseY);
    }

    @Override
    protected void initializeComponents() {
        int screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        int screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 300;
        int h = 40;

        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F), w, h, "Connect to a Server", () -> {
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenConnect());
        }));
        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F) + h + 2, w, h, "Customize Spaceship", () -> {}));
        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F)+ 2 * h + 4, w, h, "About this game", () -> {
            StarfightClient.getInstance().openWebsite("https://starfight.zyko.dev/about");
        }));
        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F)+ 3 * h + 6, w, h, "Credits", () -> {}));
        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F)+ 4 * h + 8, w, h, "Exit Game", () -> {
            GLFW.glfwSetWindowShouldClose(StarfightClient.getInstance().getDisplayManager().getWindowId(), true);
        }));
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        int screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        int screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 350;
        int h = 40;
        /*
            StarfightClient.getInstance().getShaderManager().getShader("stars").bindShader();
            StarfightClient.getInstance().getShaderManager().getShader("stars").setUniform("time", this.time);
            StarfightClient.getInstance().getGameRenderer().drawRectangle(-screenWidth, -screenHeight, screenWidth * 2, screenHeight * 4, -1);
            StarfightClient.getInstance().getShaderManager().getShader("stars").unbindShader();
        */
        StarfightClient.getInstance().getGameRenderer().getParticleRenderer().drawParticles(partialTicks);
        StarfightClient.getInstance().getGameRenderer().drawRectangle((screenWidth - w) / 2, screenHeight * 0.3 - 60, w, 100 + 5 * h + 10 + 80, 0x80111111);
        super.drawScreen(mouseX, mouseY, partialTicks);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/logo").drawCenteredString("Starfight", StarfightClient.getInstance().getDisplayManager().getWidth() / 2.0F, StarfightClient.getInstance().getDisplayManager().getHeight() * 0.3F - 6, -1);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString("https://starfight.zyko.dev", StarfightClient.getInstance().getDisplayManager().getWidth() / 2.0F, StarfightClient.getInstance().getDisplayManager().getHeight() * 0.3F + 39, -1);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawString("v" + StarfightClient.VERSION, 1, StarfightClient.getInstance().getDisplayManager().getHeight() - 18, -1);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawString("Lennart Behr, 2021", StarfightClient.getInstance().getDisplayManager().getWidth() - StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").getStringWidth("Lennart Behr, 2021") - 1, StarfightClient.getInstance().getDisplayManager().getHeight() - 18, -1);
        GL11.glPushMatrix();
        GL11.glTranslated(screenWidth / 2 + StarfightClient.getInstance().getFontManager().getFontRenderer("ui/logo").getStringWidth("Starfight") / 2 + 6, screenHeight * 0.3D, 0);
        GL11.glRotated(230, 0, 0, 1);
        StarfightClient.getInstance().getTextureManager().getTexture("entity/spaceship").bindTexture();
        StarfightClient.getInstance().getGameRenderer().drawTexturableRectangle(-32, -32, 64, 64);
        StarfightClient.getInstance().getTextureManager().getTexture("entity/spaceship").unbindTexture();
        GL11.glPopMatrix();
    }

}
