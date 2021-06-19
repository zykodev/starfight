package dev.zyko.starfight.client;

import dev.zyko.starfight.client.display.DisplayManager;
import dev.zyko.starfight.client.gui.impl.GuiScreenMainMenu;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.netcode.NetworkManager;
import dev.zyko.starfight.client.renderer.GameRenderer;
import dev.zyko.starfight.client.renderer.shader.Shader;
import dev.zyko.starfight.client.renderer.shader.ShaderManager;
import dev.zyko.starfight.client.renderer.texture.TextureManager;
import dev.zyko.starfight.client.util.TimeHelper;
import dev.zyko.starfight.entity.EntityPlayerSpaceship;

import java.util.UUID;

public class StarfightClient {

    public static final String VERSION = "alpha-indev";
    public static final String SIGNATURE = UUID.randomUUID().toString().replace("-", "");

    private static StarfightClient instance;
    private NetworkManager networkManager;
    private DisplayManager displayManager;
    private GameRenderer gameRenderer;
    private InputManager inputManager;
    private EntityPlayerSpaceship playerSpaceship;
    private TextureManager textureManager;
    private ShaderManager shaderManager;

    private TimeHelper gameTickTimer = new TimeHelper(48);

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
        this.textureManager = new TextureManager();
        this.shaderManager = new ShaderManager();
        this.displayManager.createDisplay(1280, 720, "Starfight (" + StarfightClient.VERSION + ")");
        this.textureManager.loadTextures();
        this.displayManager.setIcon(this.textureManager.getTexture("entity/spaceship"));
        this.shaderManager.loadShaders();
        this.gameRenderer = new GameRenderer();
        this.inputManager = new InputManager();
        this.gameRenderer.displayGuiScreen(new GuiScreenMainMenu());
        this.run();
    }

    private void run() {
        TimeHelper timeHelper = new TimeHelper();
        int updates = 0;
        while(!this.displayManager.shouldWindowClose()) {
            if(timeHelper.isDelayComplete(1000)) {
                System.out.println("Updates last second: " + updates);
                System.out.println("Frames per second: " + this.gameRenderer.getFramesPerSecond());
                updates = 0;
                timeHelper.updateSystemTime();
            }
            if(this.gameTickTimer.shouldTick()) {
                this.gameTickTimer.updateSystemTime();
                updates++;
            }
            this.displayManager.updateDisplay();
            this.gameRenderer.renderGame(this.gameTickTimer.getPartialTicks());
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

    public void setPlayerSpaceship(EntityPlayerSpaceship playerSpaceship) {
        this.playerSpaceship = playerSpaceship;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public ShaderManager getShaderManager() {
        return shaderManager;
    }
}
