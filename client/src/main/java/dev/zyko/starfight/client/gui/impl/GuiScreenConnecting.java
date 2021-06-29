package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;

public class GuiScreenConnecting extends GuiScreen {

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 350;
        int h = 40;
        StarfightClient.getInstance().getGameRenderer().getParticleRenderer().drawParticles(partialTicks);
        StarfightClient.getInstance().getGameRenderer().drawRectangle((screenWidth - w) / 2, screenHeight * 0.3 - 60, w, 100 + 5 * h + 10 + 80, 0x80111111);
        super.drawScreen(mouseX, mouseY, partialTicks);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredStringWithShadow(StarfightClient.getInstance().getNetworkManager().getStatus().getDisplayName(), (float) screenWidth / 2, (float) screenHeight * 0.3F + 40, -1);
    }

    @Override
    public GuiScreen clone() {
        return new GuiScreenConnecting();
    }

}
