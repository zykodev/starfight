package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiOverlay;
import dev.zyko.starfight.client.gui.GuiScreen;

public class GuiOverlayIngameMenu extends GuiOverlay {

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        float screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        float screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/logo").drawCenteredString("Starfight", screenWidth / 2, screenHeight / 2, -1);
    }

    @Override
    public GuiScreen clone() {
        return new GuiOverlayIngameMenu();
    }
}
