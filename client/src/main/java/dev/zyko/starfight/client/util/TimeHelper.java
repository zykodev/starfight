package dev.zyko.starfight.client.util;

public class TimeHelper {

    private double ticksPerSecond, tickRate;
    private long lastSystemTime = System.currentTimeMillis();

    public TimeHelper(int ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
        this.tickRate = 1000.0D / this.ticksPerSecond;
    }

    public TimeHelper() {
        this(32);
    }

    public void updateSystemTime() {
        this.lastSystemTime = System.currentTimeMillis();
    }

    public boolean isDelayComplete(double delay) {
        return System.currentTimeMillis() - this.lastSystemTime >= delay;
    }

    public double getPartialTicks() {
        double delta = System.currentTimeMillis() - this.lastSystemTime;
        return delta / this.ticksPerSecond;
    }

    public boolean shouldTick() {
        return this.isDelayComplete(this.tickRate);
    }

    public double getTicksPerSecond() {
        return ticksPerSecond;
    }
}
