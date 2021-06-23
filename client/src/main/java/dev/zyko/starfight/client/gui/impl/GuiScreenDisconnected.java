package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;

public class GuiScreenDisconnected extends GuiScreen {

    private String text;

    public GuiScreenDisconnected(String text) {
        this.text = text;
    }

    @Override
    protected void initializeComponents() {
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 300, h = 40;
        this.componentList.add(new GuiButton((float) screenWidth / 2 - w / 2.0F, (int) (screenHeight * 0.3F + 100F)+ 4 * h + 8, w, h, "Cancel", () -> {
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenConnect());
        }));
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
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredStringWithShadow(this.text, (float) screenWidth / 2, (float) screenHeight * 0.3F + 40, -1);
    }

    @Override
    public GuiScreen clone() {
        return new GuiScreenDisconnected(this.text);
    }

}
