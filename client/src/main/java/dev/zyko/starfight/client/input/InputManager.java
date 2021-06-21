package dev.zyko.starfight.client.input;

import dev.zyko.starfight.client.StarfightClient;
import org.lwjgl.glfw.*;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class InputManager {

    public HashMap<Integer, Boolean> keyMap = new HashMap<>();
    public static final int MOUSE_LEFT = 0, MOUSE_MIDDLE = 2, MOUSE_RIGHT = 1;

    public boolean isKeyDown(int key) {
        return this.keyMap.get(key);
    }

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(StarfightClient.getInstance().getDisplayManager().getWindowId(), button) == GLFW.GLFW_TRUE;
    }

    public double[] getMousePosition() {
        double[] mX = new double[1];
        double[] mY = new double[1];
        GLFW.glfwGetCursorPos(StarfightClient.getInstance().getDisplayManager().getWindowId(), mX, mY);
        return new double[] { mX[0], mY[0] };
    }

    public void updateKeyState(int key, boolean state) {
        if(this.keyMap.containsKey(key)) {
            this.keyMap.replace(key, state);
        } else {
            this.keyMap.put(key, state);
        }
    }

    public static class KeyboardAdapter extends GLFWKeyCallback {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            StarfightClient.getInstance().getInputManager().updateKeyState(key, action >= 1);
        }
    }

    public static class MouseAdapter extends GLFWMouseButtonCallback {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            double[] mousePos = StarfightClient.getInstance().getInputManager().getMousePosition();
            StarfightClient.getInstance().routeMouseInput(button, action, mousePos[0], mousePos[1]);
        }
    }

}
