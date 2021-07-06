package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiOverlay;
import dev.zyko.starfight.client.gui.GuiScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class GuiOverlayIngameMenu extends GuiOverlay {

    @Override
    protected void initializeComponents() {
        int screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        int screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 300;
        int h = 40;

        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F)+ 3 * h + 6, w, h, "Back to game", () -> {
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(null);
        }));
        this.componentList.add(new GuiButton((screenWidth - w) / 2, (int) (screenHeight * 0.3F + 100F)+ 4 * h + 8, w, h, "Disconnect", () -> {
            StarfightClient.getInstance().getNetworkManager().disconnect();
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenMainMenu());
        }));
        super.initializeComponents();
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        int screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        int screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 350;
        int h = 40;
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

    @Override
    public GuiScreen clone() {
        return new GuiOverlayIngameMenu();
    }

    @Override
    public void keyInput(int keyCode, int action) {
        if(keyCode == GLFW.GLFW_KEY_ESCAPE && action == 1) {
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(null);
        }
        super.keyInput(keyCode, action);
    }

}
