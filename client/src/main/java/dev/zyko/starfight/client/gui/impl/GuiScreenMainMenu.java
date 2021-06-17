package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.gui.GuiScreen;

public class GuiScreenMainMenu extends GuiScreen {

    @Override
    protected void initializeComponents() {
        this.componentList.add(new GuiButton(0, 0, 100, 20, "Connect"));
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
