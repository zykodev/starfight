package dev.zyko.starfight.client.input;

import dev.zyko.starfight.client.StarfightClient;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class InputManager {

    /**
     * IDs der einzelnen Maustasten für Lesbarkeit in konstanten Variablen abgebildet.
     */
    public static final int MOUSE_LEFT = 0, MOUSE_MIDDLE = 2, MOUSE_RIGHT = 1;

    /**
     * Überprüft, ob eine Maustaste gedrückt ist.
     *
     * @param button die ID der Maustaste, für welche der Zustand geprüft werden soll.
     * @return true, wenn die Taste gedrückt ist, ansonsten false.
     */
    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(StarfightClient.getInstance().getDisplayManager().getWindowId(), button) == GLFW.GLFW_TRUE;
    }

    /**
     * Gibt die aktuelle Mausposition in Form eines double-Arrays zurück.
     *
     * @return double[], welches an Index 0 die x-Koordinate und and Index 1 die y-Koordinate der Mausposition enthält.
     */
    public double[] getMousePosition() {
        double[] mX = new double[1];
        double[] mY = new double[1];
        GLFW.glfwGetCursorPos(StarfightClient.getInstance().getDisplayManager().getWindowId(), mX, mY);
        return new double[]{mX[0], mY[0]};
    }

    /**
     * Keyboard-Adapter, welcher bei OpenGL eingehangen werden kann, um Input abzufangen.
     */
    public static class KeyboardAdapter extends GLFWKeyCallback {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            StarfightClient.getInstance().routeKeyInput(key, action);
        }
    }

    /**
     * Maus-Adapter, welcher bei OpenGL eingehangen werden kann, um Input abzufangen.
     */
    public static class MouseAdapter extends GLFWMouseButtonCallback {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            double[] mousePos = StarfightClient.getInstance().getInputManager().getMousePosition();
            StarfightClient.getInstance().routeMouseInput(button, action, mousePos[0], mousePos[1]);
        }
    }

    /**
     * Keyboard-Adapter, welcher bei OpenGL eingehangen werden kann, um Input in Form von chars abzufangen.
     */
    public static class KeyboardCharAdapter extends GLFWCharCallback {
        @Override
        public void invoke(long window, int codepoint) {
            if (!Character.isValidCodePoint(codepoint)) return;
            char[] chars = Character.toChars(codepoint);
            for (char aChar : chars) {
                StarfightClient.getInstance().routeCharInput(aChar);
            }
        }
    }

}
