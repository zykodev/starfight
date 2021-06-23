package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiComponent;
import dev.zyko.starfight.client.input.InputManager;

import java.awt.*;

public class GuiButton extends GuiComponent {

    private String text;
    private boolean active = true;
    private int additionalLightness = 0;
    private float xOffset = 0;
    private ButtonRunnable runnable;

    public GuiButton(float posX, float posY, float width, float height, String text, ButtonRunnable runnable) {
        super(posX, posY, width, height);
        this.text = text;
        this.runnable = runnable;
    }

    @Override
    public void mouseButtonPressed(int button, double x, double y) {
        boolean hovered = x >= this.posX - this.xOffset && x <= this.posX + width + this.xOffset && y >= this.posY && y <= this.posY + height;
        if(button == InputManager.MOUSE_LEFT && hovered && this.isActive()) this.runnable.run();
    }

    @Override
    public void mouseButtonReleased(int button, double x, double y) {
    }

    @Override
    public void runTick(double mouseX, double mouseY) {
        boolean hovered = mouseX >= this.posX - this.xOffset && mouseX <= this.posX + width + this.xOffset && mouseY >= this.posY && mouseY <= this.posY + height;
        float maxOffset = 15;
        float diffOffset = maxOffset - this.xOffset;
        if(this.xOffset <= maxOffset && hovered) {
            this.xOffset += diffOffset / 4;
            if(this.xOffset > maxOffset - 0.5) this.xOffset = maxOffset;
        }
        if(this.xOffset > 0 && !hovered) {
            this.xOffset -= (maxOffset - diffOffset) / 4;
            if(this.xOffset < 0.5) this.xOffset = 0;
        }
        additionalLightness++;
    }

    @Override
    public void drawComponent(double mouseX, double mouseY, double partialTicks) {
        boolean hovered = mouseX >= this.posX - this.xOffset && mouseX <= this.posX + width + this.xOffset && mouseY >= this.posY && mouseY <= this.posY + height;
        if(!hovered) {
            this.additionalLightness = 0;
        }
        int addedLightness = (int) (Math.cos(((2 * Math.PI) / StarfightClient.getInstance().getGameTickTimer().getTicksPerSecond()) * this.additionalLightness) * 30 + 30);
        float yOffset = (float) (Math.sin(((2 * Math.PI) / StarfightClient.getInstance().getGameTickTimer().getTicksPerSecond()) * this.additionalLightness)) * 3.0F;
        if(this.active) {
            StarfightClient.getInstance().getGameRenderer().drawRectangle(this.posX - this.xOffset, this.posY, this.width + 2 * this.xOffset, this.height, hovered ? new Color(60 + addedLightness, 60 + addedLightness, 60 + addedLightness, 80).getRGB() : 0x99111111);
        } else {
            StarfightClient.getInstance().getGameRenderer().drawRectangle(this.posX, this.posY, this.width, this.height, 0x99111111);
        }
        if(hovered && this.isActive()) {
            StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredStringWithShadow(this.text, this.posX + width / 2.0F - yOffset, this.posY + this.height / 2.0F - 8, -1);
        } else {
            StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString(this.text, this.posX + width / 2.0F, this.posY + this.height / 2.0F - 8, this.active ? -1 : 0xFF666666);
        }
    }

    @Override
    public void keyInput(int keyCode, int action) {
    }

    @Override
    public void charInput(char c) {
    }

    public void executeRunnable() {
        if(!this.isActive()) return;
        this.runnable.run();
    }

    public interface ButtonRunnable {
        void run();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
