package dev.zyko.starfight.client.gui;

public interface GuiComponent {

    void drawComponent(double mouseX, double mouseY, double partialTicks);

    void runTick(double mouseX, double mouseY);

    void mouseButtonPressed(int button, double x, double y);

    void mouseButtonReleased(int button, double x, double y);

}
