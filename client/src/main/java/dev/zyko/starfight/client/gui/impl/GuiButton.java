package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.gui.GuiComponent;

public class GuiButton implements GuiComponent {

    private int posX, posY, width, height;
    private String text;

    public GuiButton(int posX, int posY, int width, int height, String text) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    @Override
    public void drawComponent(double mouseX, double mouseY, float partialTicks) {
        System.out.println(this.text);
    }

}
