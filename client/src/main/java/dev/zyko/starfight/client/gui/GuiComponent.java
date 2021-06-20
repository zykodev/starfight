package dev.zyko.starfight.client.gui;

public interface GuiComponent {

    void drawComponent(double mouseX, double mouseY, double partialTicks);

    void runTick(double mouseX, double mouseY);

}
