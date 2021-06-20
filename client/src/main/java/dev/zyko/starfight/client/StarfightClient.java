package dev.zyko.starfight.client;

import dev.zyko.starfight.client.display.DisplayManager;
import dev.zyko.starfight.client.gui.impl.GuiScreenMainMenu;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.netcode.NetworkManager;
import dev.zyko.starfight.client.renderer.GameRenderer;
import dev.zyko.starfight.client.renderer.font.FontManager;
import dev.zyko.starfight.client.renderer.shader.ShaderManager;
import dev.zyko.starfight.client.renderer.texture.TextureManager;
import dev.zyko.starfight.client.thread.GameTickThread;
import dev.zyko.starfight.client.util.IOHelper;
import dev.zyko.starfight.client.util.TextureHelper;
import dev.zyko.starfight.client.util.TimeHelper;
import dev.zyko.starfight.entity.EntityPlayerSpaceship;

import java.io.File;
import java.nio.ByteBuffer;
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
    private FontManager fontManager;

    private TimeHelper gameTickTimer = new TimeHelper(48);

    private GameTickThread gameTickThread;

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
        this.fontManager = new FontManager();
        this.shaderManager = new ShaderManager();
        this.displayManager.createDisplay(1280, 720, "Starfight (" + StarfightClient.VERSION + ")");
        this.textureManager.loadTextures();
        this.fontManager.loadFonts();
        File iconAsset = IOHelper.extractAsset("textures/spaceship.png");
        ByteBuffer buffer = TextureHelper.fileToBuffer(iconAsset.getAbsolutePath());
        this.displayManager.setIcon(buffer);
        this.shaderManager.loadShaders();
        this.gameRenderer = new GameRenderer();
        this.inputManager = new InputManager();
        this.gameRenderer.displayGuiScreen(new GuiScreenMainMenu());
        this.run();
    }

    private void exit() {
        this.displayManager.destroyDisplay();
        this.gameTickThread.terminate();
    }

    private void run() {
        this.gameTickThread = new GameTickThread();
        this.gameTickThread.start();
        TimeHelper fpsLock = new TimeHelper();
        while(!this.displayManager.shouldWindowClose()) {
            this.displayManager.updateDisplay();
            this.gameRenderer.renderGame(this.gameTickTimer.getPartialTicks());
            this.displayManager.finishUpdate();
            while(!fpsLock.isDelayComplete(1000.0D / 203.0D)) {}
            fpsLock.updateSystemTime();
        }
        this.exit();
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

    public FontManager getFontManager() {
        return fontManager;
    }

    public TimeHelper getGameTickTimer() {
        return gameTickTimer;
    }

}
