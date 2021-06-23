package dev.zyko.starfight.client.input;

import dev.zyko.starfight.client.StarfightClient;
import org.lwjgl.glfw.*;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class InputManager {

    public static final int MOUSE_LEFT = 0, MOUSE_MIDDLE = 2, MOUSE_RIGHT = 1;

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(StarfightClient.getInstance().getDisplayManager().getWindowId(), button) == GLFW.GLFW_TRUE;
    }

    public double[] getMousePosition() {
        double[] mX = new double[1];
        double[] mY = new double[1];
        GLFW.glfwGetCursorPos(StarfightClient.getInstance().getDisplayManager().getWindowId(), mX, mY);
        return new double[] { mX[0], mY[0] };
    }

    public static class KeyboardAdapter extends GLFWKeyCallback {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            StarfightClient.getInstance().routeKeyInput(key, action);
        }
    }

    public static class MouseAdapter extends GLFWMouseButtonCallback {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            double[] mousePos = StarfightClient.getInstance().getInputManager().getMousePosition();
            StarfightClient.getInstance().routeMouseInput(button, action, mousePos[0], mousePos[1]);
        }
    }

    public static class KeyboardCharAdapter extends GLFWCharCallback {
        @Override
        public void invoke(long window, int codepoint) {
            if(!Character.isValidCodePoint(codepoint)) return;
            char[] chars = Character.toChars(codepoint);
            for (char aChar : chars) {
                StarfightClient.getInstance().routeCharInput(aChar);
            }
        }
    }

}
