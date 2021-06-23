package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiComponent;
import dev.zyko.starfight.client.input.InputManager;
import org.lwjgl.glfw.GLFW;

public class GuiTextBox extends GuiComponent {

    private String text = "";
    private int scrollIndex;
    private int cursorCounter;
    private boolean selected;

    public GuiTextBox(float posX, float posY, float width, float height) {
        super(posX, posY, width, height);
    }

    @Override
    public void drawComponent(double mouseX, double mouseY, double partialTicks) {
        this.updateScrollIndex();
        String textToDraw = this.text.substring(this.scrollIndex);
        double textWidth = StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").getStringWidth(textToDraw);
        StarfightClient.getInstance().getGameRenderer().drawRectangle(this.posX, this.posY, this.width, this.height, 0x99111111);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawString(textToDraw, this.posX + 3, this.posY + height / 2 - 9, -1);
        if(cursorCounter >= 0 && cursorCounter <= 31 && this.selected) {
            StarfightClient.getInstance().getGameRenderer().drawRectangle(this.posX + textWidth + 3, this.posY + height / 2 - 9, 1, 18, -1);
        }
    }

    private void updateScrollIndex() {
        double textWidth = StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").getStringWidth(this.text.substring(this.scrollIndex));
        if(textWidth > this.width - 6) {
            this.scrollIndex++;
            this.updateScrollIndex();
        }
    }

    @Override
    public void runTick(double mouseX, double mouseY) {
        this.cursorCounter++;
        if(this.cursorCounter > 64 || !this.selected) this.cursorCounter = 0;
    }

    @Override
    public void mouseButtonPressed(int button, double x, double y) {
        if(button == InputManager.MOUSE_LEFT) {
            this.selected = this.isHovered(x, y);
        }
    }

    @Override
    public void mouseButtonReleased(int button, double x, double y) {

    }

    @Override
    public void keyInput(int keyCode, int action) {
        if(!this.selected) return;
        if(keyCode == GLFW.GLFW_KEY_BACKSPACE && action > 0 && this.text.length() > 0) {
            this.text = this.text.substring(0, this.text.length() - 1);
            if(this.scrollIndex > 0)
                this.scrollIndex--;
        }
        this.cursorCounter = 0;
    }

    @Override
    public void charInput(char c) {
        if(!this.selected) return;
        this.text += c;
        this.cursorCounter = 32;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setText(String text) {
        this.text = text;
    }
}
