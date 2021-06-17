package dev.zyko.starfight.client.input;

import dev.zyko.starfight.client.StarfightClient;
import org.lwjgl.glfw.GLFW;

public class InputManager {

    public static final int MOUSE_LEFT = 0, MOUSE_MIDDLE = 2, MOUSE_RIGHT = 1;

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(StarfightClient.getInstance().getDisplayManager().getWindowId(), key) == GLFW.GLFW_TRUE;
    }

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(StarfightClient.getInstance().getDisplayManager().getWindowId(), button) == GLFW.GLFW_TRUE;
    }

    public double[] getMousePosition() {
        double[] mousePosition = new double[2];
        GLFW.glfwGetCursorPos(StarfightClient.getInstance().getDisplayManager().getWindowId(), mousePosition, mousePosition);
        return mousePosition;
    }

}
