package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiOverlay;
import dev.zyko.starfight.data.ScoreboardEntry;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;

public class GuiOverlayScoreboard extends GuiOverlay {

    private DecimalFormat decimalFormat = new DecimalFormat("#.###");

    @Override
    public void keyInput(int keyCode, int action) {
        if(action == 1) {
            if(keyCode == GLFW.GLFW_KEY_TAB || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(null);
            }
        }
        super.keyInput(keyCode, action);
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        double backgroundX = screenWidth * 0.25D;
        double backgroundY = screenHeight * 0.25D;
        double backgroundWidth = screenWidth / 2.0D;
        double backgroundHeight = screenHeight / 2.0D;
        StarfightClient.getInstance().getGameRenderer().drawRectangle(backgroundX, backgroundY, backgroundWidth, backgroundHeight, 0x80111111);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/logo").drawCenteredStringWithShadow("Scoreboard", (float) backgroundWidth, (float) backgroundY + 30, -1);
        int yOffset = 70;
        for(ScoreboardEntry entry : StarfightClient.getInstance().getWorld().getScoreboard().getEntryList()) {
            if(entry.getPlayerRank() > 10 && !entry.getPlayerName().equalsIgnoreCase(StarfightClient.getInstance().getPlayerSpaceship().getName())) continue;
            StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString("#" + entry.getPlayerRank() + ": " + entry.getPlayerName() + " with a score of " + this.decimalFormat.format(entry.getPlayerScore()), (float) backgroundWidth, (float) backgroundY + 20 + yOffset, entry.getPlayerName().equalsIgnoreCase(StarfightClient.getInstance().getPlayerSpaceship().getName()) ? 0xFFEEEEFF : -1);
            yOffset += 20;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
