package dev.zyko.starfight.server.thread;

import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.util.TimeHelper;

public class ServerTickThread extends Thread {

    private boolean terminated = false;
    private TimeHelper serverTickTimer = new TimeHelper(48), tickCountTimer = new TimeHelper();
    private int updates, updatesLastSecond;

    @Override
    public void run() {
        while(!this.terminated) {
            if(this.tickCountTimer.isDelayComplete(1000)) {
                this.updatesLastSecond = this.updates;
                this.updates = 0;
                this.tickCountTimer.updateSystemTime();
                StarfightServer.getInstance().getLogger().setLevel(StarfightLogger.Level.DEBUG).log(this.getClass(), "Tile entity status: " + StarfightServer.getInstance().getWorld().getTileEntityList().size() + "(+" + StarfightServer.getInstance().getWorld().getUnloadedTileEntityList().size() + ")");
                StarfightServer.getInstance().getLogger().setLevel(StarfightLogger.Level.DEBUG).log(this.getClass(), "Updates per second: " + this.updatesLastSecond);
            }
            if(this.serverTickTimer.shouldTick()) {
                StarfightServer.getInstance().getWorld().tick();
                this.serverTickTimer.updateSystemTime();
                this.updates++;
            }
        }
        super.run();
    }

    public void terminate() {
        this.terminated = true;
    }

}
