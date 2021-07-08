package dev.zyko.starfight.client.gui.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.GuiScreen;
import org.lwjgl.glfw.GLFW;

public class GuiScreenConnect extends GuiScreen {

    private GuiButton connectButton;
    private GuiTextBox serverAddressInput, nicknameInput;

    @Override
    protected void initializeComponents() {
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 300;
        int h = 40;
        this.serverAddressInput = new GuiTextBox((float) screenWidth / 2 - w / 2.0F, (int) (screenHeight * 0.3F + 100F) - 10, w, h);
        this.nicknameInput = new GuiTextBox((float) screenWidth / 2 - w / 2.0F, (int) (screenHeight * 0.3F + 100F) + 2 * h - 10, w, h);
        this.componentList.add(this.serverAddressInput);
        this.componentList.add(this.nicknameInput);
        this.componentList.add(this.connectButton = new GuiButton((float) screenWidth / 2 - w / 2.0F, (int) (screenHeight * 0.3F + 100F) + 3 * h + 6, w, h, "Connect", () -> {
            try {
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenConnecting());
                StarfightClient.getInstance().getNetworkManager().connect(this.serverAddressInput.getText(), this.nicknameInput.getText());
            } catch (Exception e) {
                e.printStackTrace();
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenConnect());
            }
        }));
        this.componentList.add(new GuiButton((float) screenWidth / 2 - w / 2.0F, (int) (screenHeight * 0.3F + 100F) + 4 * h + 8, w, h, "Back to Main Menu", () -> {
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenMainMenu());
        }));
        super.initializeComponents();
    }

    @Override
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        this.connectButton.setActive(!(this.serverAddressInput.getText().length() == 0 || this.nicknameInput.getText().length() == 0));
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        int w = 350;
        int h = 40;
        StarfightClient.getInstance().getGameRenderer().getParticleRenderer().drawParticles(partialTicks);
        StarfightClient.getInstance().getGameRenderer().drawRectangle((screenWidth - w) / 2, screenHeight * 0.3 - 60, w, 100 + 5 * h + 10 + 80, 0x80111111);
        super.drawScreen(mouseX, mouseY, partialTicks);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/logo").drawCenteredString("Connect", StarfightClient.getInstance().getDisplayManager().getWidth() / 2.0F, StarfightClient.getInstance().getDisplayManager().getHeight() * 0.3F - 6, -1);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString("Server Address", this.serverAddressInput.posX + this.serverAddressInput.width / 2, this.serverAddressInput.posY - 24, -1);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredString("Nickname", this.nicknameInput.posX + this.nicknameInput.width / 2, this.nicknameInput.posY - 24, -1);
    }

    @Override
    public void keyInput(int keyCode, int action) {
        super.keyInput(keyCode, action);
        if (keyCode == GLFW.GLFW_KEY_TAB && action == 1) {
            if (this.serverAddressInput.isSelected()) {
                this.serverAddressInput.setSelected(false);
                this.nicknameInput.setSelected(true);
            } else {
                if (this.nicknameInput.isSelected()) {
                    this.nicknameInput.setSelected(false);
                    this.serverAddressInput.setSelected(true);
                } else {
                    this.serverAddressInput.setSelected(true);
                }
            }
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER && action == 1) {
            this.connectButton.executeRunnable();
        }
    }

    @Override
    public GuiScreen clone() {
        GuiScreenConnect screenConnect = new GuiScreenConnect();
        screenConnect.nicknameInput.setText(this.nicknameInput.getText());
        screenConnect.serverAddressInput.setText(this.serverAddressInput.getText());
        return screenConnect;
    }
}
