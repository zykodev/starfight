package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiComponent;

import java.awt.*;

public class GuiButton implements GuiComponent {

    private int posX, posY, width, height;
    private String text;
    private int additionalLightness = 0;
    private float xOffset = 0;

    public GuiButton(int posX, int posY, int width, int height, String text) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
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
        StarfightClient.getInstance().getGameRenderer().drawRectangle(this.posX - this.xOffset, this.posY, this.width + 2 * this.xOffset, this.height, hovered ? new Color(60 + addedLightness, 60 + addedLightness, 60 + addedLightness, 80).getRGB() : 0x99111111);
        if(hovered) {
            StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredStringWithShadow(this.text, this.posX + width / 2.0F - yOffset, this.posY + this.height / 2.0F - 8, -1);
        } else {
            StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString(this.text, this.posX + width / 2.0F, this.posY + this.height / 2.0F - 8, -1);
        }
    }

}
