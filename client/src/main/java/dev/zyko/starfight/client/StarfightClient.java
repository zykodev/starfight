package dev.zyko.starfight.client;

import dev.zyko.starfight.client.display.DisplayManager;
import dev.zyko.starfight.client.entity.EntityPlayerSpaceship;
import dev.zyko.starfight.client.gui.impl.GuiOverlayIngameMenu;
import dev.zyko.starfight.client.gui.impl.GuiOverlayScoreboard;
import dev.zyko.starfight.client.gui.impl.GuiScreenMainMenu;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.netcode.NetworkManager;
import dev.zyko.starfight.client.renderer.GameRenderer;
import dev.zyko.starfight.client.renderer.font.FontManager;
import dev.zyko.starfight.client.renderer.model.ModelManager;
import dev.zyko.starfight.client.renderer.texture.TextureManager;
import dev.zyko.starfight.client.thread.GameTickThread;
import dev.zyko.starfight.client.util.IOHelper;
import dev.zyko.starfight.client.util.TextureHelper;
import dev.zyko.starfight.client.world.World;
import dev.zyko.starfight.util.TimeHelper;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Properties;

public class StarfightClient {

    public static final String VERSION = "alpha-indev";
    private static StarfightClient instance;

    private EntityPlayerSpaceship playerSpaceship;
    private World world;

    private NetworkManager networkManager;
    private DisplayManager displayManager;
    private GameRenderer gameRenderer;
    private InputManager inputManager;
    private TextureManager textureManager;
    private FontManager fontManager;
    private ModelManager modelManager;

    private TimeHelper gameTickTimer = new TimeHelper(48);
    private GameTickThread gameTickThread;

    /**
     * Initialisiert das Spiel und öffnet das Spiel-Fenster vor.
     *
     * @throws Exception, falls es bei diesem Vorgang ein Problem gab.
     */
    public StarfightClient() throws Exception {
        instance = this;
        this.networkManager = new NetworkManager();
        this.displayManager = new DisplayManager();
        this.textureManager = new TextureManager();
        this.modelManager = new ModelManager();
        this.fontManager = new FontManager();
        this.inputManager = new InputManager();
        this.displayManager.createDisplay(1280, 720, "Starfight (" + StarfightClient.VERSION + ")");
        this.textureManager.loadTextures();
        this.modelManager.loadModels();
        this.fontManager.loadFonts();
        File iconAsset = IOHelper.extractAsset("textures/spaceship.png");
        ByteBuffer buffer = TextureHelper.fileToBuffer(iconAsset.getAbsolutePath());
        this.displayManager.setIcon(buffer);
        this.gameRenderer = new GameRenderer();
        this.gameRenderer.getParticleRenderer().setup(0, 0, 1280, 720);
        this.gameRenderer.displayGuiScreen(new GuiScreenMainMenu());
        this.run();
    }

    public static void main(String[] args) {
        try {
            StarfightClient client = new StarfightClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StarfightClient getInstance() {
        return instance;
    }

    /**
     * Beendet das Spiel.
     */
    private void exit() {
        this.networkManager.disconnect();
        this.displayManager.destroyDisplay();
        this.gameTickThread.terminate();
    }

    /**
     * Startet das Spiel.
     */
    private void run() {
        this.gameTickThread = new GameTickThread();
        this.gameTickThread.setName("game-tick-thread");
        this.gameTickThread.setDaemon(true);
        this.gameTickThread.start();
        while (!this.displayManager.shouldWindowClose()) {
            this.displayManager.updateDisplay();
            this.gameRenderer.renderGame(this.gameTickTimer.getPartialTicks());
            this.displayManager.finishUpdate();
        }
        this.exit();
    }

    public void routeMouseInput(int button, int action, double x, double y) {
        if (this.gameRenderer.getCurrentScreen() != null) {
            switch (action) {
                case 1:
                    StarfightClient.getInstance().getGameRenderer().getCurrentScreen().mouseButtonPressed(button, x, y);
                    break;
                case 0:
                    StarfightClient.getInstance().getGameRenderer().getCurrentScreen().mouseButtonReleased(button, x, y);
                    break;
                default:
                    break;
            }
        }
    }

    public void routeCharInput(char c) {
        if (this.gameRenderer.getCurrentScreen() != null) {
            this.gameRenderer.getCurrentScreen().charInput(c);
        }
    }

    public void routeKeyInput(int keyCode, int action) {
        if (this.gameRenderer.getCurrentScreen() != null) {
            this.gameRenderer.getCurrentScreen().keyInput(keyCode, action);
        } else {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE && action == 1) {
                this.gameRenderer.displayGuiScreen(new GuiOverlayIngameMenu());
            }
            if (keyCode == GLFW.GLFW_KEY_TAB && action == 1) {
                this.gameRenderer.displayGuiScreen(new GuiOverlayScoreboard());
            }
        }
    }

    /**
     * Öffnet einen Link im Standard-Systembrowser.
     *
     * @param url die URL, welche geöffnet werden soll.
     *            <p>
     *            Notiz: Nur für Windows getestet.
     */
    public void openWebsite(String url) {
        try {
            String command = "";
            Properties properties = System.getProperties();
            String os = properties.getProperty("os.name").toLowerCase(Locale.ROOT);
            System.out.println(os);
            if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
                command = "exec xdg-open " + url;
            }
            if (os.indexOf("win") >= 0) {
                command = "cmd /c \"start " + url + "\"";
            }
            if (os.indexOf("mac") >= 0) {
                command = "open " + url;
            }
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public FontManager getFontManager() {
        return fontManager;
    }

    public TimeHelper getGameTickTimer() {
        return gameTickTimer;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ModelManager getModelManager() {
        return modelManager;
    }

    public GameTickThread getGameTickThread() {
        return gameTickThread;
    }

}
