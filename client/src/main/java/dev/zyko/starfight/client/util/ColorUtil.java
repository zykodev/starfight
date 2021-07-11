package dev.zyko.starfight.client.util;

public class ColorUtil {

    /**
     * Teilt einen HEX-ARGB-Code (0xAARRGGBB)
     *
     * @param color der Hex-Code, welcher analysiert werden soll.
     * @return ein float-Array, welches an Index 0 den Rot-Wert, an Index 1 den Grün-Wert, and Index 2 den Blau-Wert und an Index 3 den Alpha-Wert enthält.
     */
    public static float[] hexToRGBA(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        int a = (color >> 24) & 0xFF;
        return new float[]{r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F};
    }

}
