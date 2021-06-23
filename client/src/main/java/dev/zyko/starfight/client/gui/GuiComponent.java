package dev.zyko.starfight.client.gui;

public abstract class GuiComponent {

    public float posX, posY, width, height;

    public GuiComponent(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void drawComponent(double mouseX, double mouseY, double partialTicks);

    public abstract void runTick(double mouseX, double mouseY);

    public abstract void mouseButtonPressed(int button, double x, double y);

    public abstract void mouseButtonReleased(int button, double x, double y);

    public abstract void charInput(char c);

    public abstract void keyInput(int keyCode, int action);

    public boolean isHovered(double mouseX, double mouseY) {
        return this.posX <= mouseX && this.posX + this.width >= mouseX && this.posY <= mouseY && this.posY + this.height >= mouseY;
    }

}
