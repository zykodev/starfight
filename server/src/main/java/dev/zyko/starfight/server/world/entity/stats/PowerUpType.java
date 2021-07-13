package dev.zyko.starfight.server.world.entity.stats;

public enum PowerUpType {

    SPEED(48 * 3, 2.0D), HEALTH(0, 4.0D), CDR(48 * 5, 6.0D), NONE(0, -1.0D);

    int duration;
    double type;

    PowerUpType(int duration, double type) {
        this.duration = duration;
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public double getType() {
        return type;
    }

}
