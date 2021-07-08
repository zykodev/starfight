package dev.zyko.starfight.server.world.entity.stats;

public enum PowerUpType {

    SPEED(48 * 3), HEALTH(0), CDR(48 * 5), NONE(0);

    int duration;

    PowerUpType(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
