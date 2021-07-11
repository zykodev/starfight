package dev.zyko.starfight.client.display;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.input.InputManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class DisplayManager {

    private long windowId;
    private int width, height;

    /**
     * Öffnet ein neues Fenster, in welchem die Spielinhalte gerendert werden können.
     *
     * @param width  die Breite des Fensters.
     * @param height die Höhe des Fensters.
     * @param title  der Titel des Fensters.
     * @throws Exception, falls das Fenster nicht geöffnet werden konnte.
     */
    public void createDisplay(int width, int height, String title) throws Exception {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW failed to initialize, application stuck in illegal state.");
        }
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        this.windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (this.windowId == 0) {
            throw new IllegalStateException("GLFW failed to create a window to render to, application stuck in illegal state.");
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(this.windowId, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwShowWindow(this.windowId);
        GLFW.glfwMakeContextCurrent(this.windowId);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(0);
        GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        this.setViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
        GLFW.glfwSetKeyCallback(this.windowId, new InputManager.KeyboardAdapter());
        GLFW.glfwSetMouseButtonCallback(this.windowId, new InputManager.MouseAdapter());
        GLFW.glfwSetCharCallback(this.windowId, new InputManager.KeyboardCharAdapter());
    }

    /**
     * Aktualisiert das Fenster.
     * Beispielsweise wird das aktuell angezeigte Menü neu skaliert und Eingabe wird an die entsprechenden Event-Handler weitergeleitet.
     */
    public void updateDisplay() {
        GLFW.glfwPollEvents();
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(this.windowId, width, height);
        this.setViewport(0, 0, width[0], height[0]);
        int oldWidth = this.width;
        int oldHeight = this.height;
        this.width = width[0];
        this.height = height[0];
        if (this.height != oldHeight || this.width != oldWidth) {
            if (StarfightClient.getInstance().getGameRenderer().getCurrentScreen() != null) {
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(StarfightClient.getInstance().getGameRenderer().getCurrentScreen().clone());
            }
            StarfightClient.getInstance().getGameRenderer().getParticleRenderer().setup(0, 0, this.width, this.height);
        }
    }

    /**
     * Passt den Viewport vom OpenGL-Standard auf etwas neues, in diesem Fall besser handhabbares, an.
     *
     * @param x      die (virtuelle) x-Koordinate des Pixels oben links in der Ecke.
     * @param y      die (virtuelle) y-Koordinate des Pixels oben links in der Ecke.
     * @param width  die (virtuelle) x-Koordinate des Pixels unten rechts in der Ecke.
     * @param height die (virtuelle) y-Koordinate des Pixels unten rechts in der Ecke.
     */
    public void setViewport(int x, int y, int width, int height) {
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    /**
     * Das Spiel verwendet Double Buffering, weshalb nach jedem gerenderten Frame die beiden Framebuffer getauscht werden müssen.
     *
     * @see "https://de.wikipedia.org/wiki/Doppelpufferung"
     */
    public void finishUpdate() {
        GLFW.glfwSwapBuffers(this.windowId);
    }

    /**
     * Zerstört das Fenster und den OpenGL-Kontext.
     */
    public void destroyDisplay() {
        GLFW.glfwTerminate();
    }

    /**
     * Überprüft, ob das Fenster sich schließen soll. (bspw. durch Klicken auf das X)
     *
     * @return true, wenn das Fenster sich schließen soll, ansonsten false.
     */
    public boolean shouldWindowClose() {
        return GLFW.glfwWindowShouldClose(this.windowId);
    }

    public long getWindowId() {
        return windowId;
    }

    /**
     * Ändert das Fenster-Symbol
     *
     * @param image ein ByteBuffer, welcher die Bilddaten enthält.
     */
    public void setIcon(ByteBuffer image) {
        GLFWImage glfwImage = GLFWImage.malloc();
        GLFWImage.Buffer glfwImages = GLFWImage.malloc(1);
        glfwImage.set(32, 32, image);
        glfwImages.put(0, glfwImage);
        GLFW.glfwSetWindowIcon(this.windowId, glfwImages);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
