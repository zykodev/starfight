package dev.zyko.starfight.client.util;

import org.joml.Vector2d;

public class MathHelper {

    /**
     * Berechnet den Winkel zwischen der Geraden aus zwei Punkten und der x-Achse.
     *
     * @param x1 x-Koordinate des 1. Punktes.
     * @param y1 y-Koordinate des 1. Punktes.
     * @param x2 x-Koordinate des 2. Punktes.
     * @param y2 y-Koordinate des 2. Punktes.
     * @return den errechneten Winkel.
     */
    public static double calculateHorizontalAngle(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        if (diffX == 0) {
            if (diffY > 0) {
                return -90;
            } else {
                return 90;
            }
        }
        Vector2d vec1 = new Vector2d(diffX, 0);
        Vector2d vec2 = new Vector2d(diffX, diffY);
        return (diffX > 0 ? 180 : 0) + Math.toDegrees(vec1.angle(vec2));
    }

}
