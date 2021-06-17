package dev.zyko.starfight.client.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class DisplayManager {

    private long windowId;

    public void createDisplay(int width, int height, String title) throws Exception {
        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW failed to initialize, application stuck in illegal state.");
        }
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        this.windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if(this.windowId == 0) {
            throw new IllegalStateException("GLFW failed to create a window to render to, application stuck in illegal state.");
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(this.windowId, (videoMode.width() + width) / 2, (videoMode.height() + height) / 2);
        GLFW.glfwShowWindow(this.windowId);
    }

    public void updateDisplay() {
        GLFW.glfwPollEvents();
    }

    public void destroyDisplay() {
        GLFW.glfwTerminate();
    }

    public boolean shouldWindowClose() {
        return GLFW.glfwWindowShouldClose(this.windowId);
    }

}
