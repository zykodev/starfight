package dev.zyko.starfight.client.util;

public class TimeHelper {

    private long lastSystemTime = System.currentTimeMillis();

    public void updateSystemTime() {
        this.lastSystemTime = System.currentTimeMillis();
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastSystemTime >= delay;
    }

}
