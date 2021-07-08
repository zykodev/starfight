package dev.zyko.starfight.client.util;

public class ColorUtil {

    public static float[] hexToRGBA(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        int a = (color >> 24) & 0xFF;
        return new float[]{r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F};
    }

}
