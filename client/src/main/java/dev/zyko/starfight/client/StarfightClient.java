package dev.zyko.starfight.client;

import dev.zyko.starfight.client.display.DisplayManager;
import dev.zyko.starfight.client.gui.impl.GuiScreenMainMenu;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.netcode.NetworkManager;
import dev.zyko.starfight.client.renderer.GameRenderer;
import dev.zyko.starfight.entity.EntityPlayerSpaceship;

public class StarfightClient {

    public static final String VERSION = "alpha-indev";

    private static StarfightClient instance;
    private NetworkManager networkManager;
    private DisplayManager displayManager;
    private GameRenderer gameRenderer;
    private InputManager inputManager;
    private EntityPlayerSpaceship playerSpaceship;

    public static void main(String[] args) {
        try {
            StarfightClient client = new StarfightClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StarfightClient() throws Exception {
        instance = this;
        this.networkManager = new NetworkManager();
        this.displayManager = new DisplayManager();
        this.displayManager.createDisplay(1280, 720, "Starfight (" + StarfightClient.VERSION + ")");
        this.gameRenderer = new GameRenderer();
        this.inputManager = new InputManager();
        this.gameRenderer.displayGuiScreen(new GuiScreenMainMenu());
        this.run();
    }

    private void run() {
        while(!this.displayManager.shouldWindowClose()) {
            this.displayManager.updateDisplay();
            this.gameRenderer.renderGame(0);
            this.displayManager.finishUpdate();
        }
        this.displayManager.destroyDisplay();
    }

    public static StarfightClient getInstance() {
        return instance;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    public EntityPlayerSpaceship getPlayerSpaceship() {
        return playerSpaceship;
    }

}
