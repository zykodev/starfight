package dev.zyko.starfight.server.thread;

import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.util.TimeHelper;

public class ServerTickThread extends Thread {

    private boolean terminated = false;
    private TimeHelper serverTickTimer = new TimeHelper(48);

    @Override
    public void run() {
        while(!this.terminated) {
            if(this.serverTickTimer.shouldTick()) {
                StarfightServer.getInstance().getWorld().tick();
                this.serverTickTimer.updateSystemTime();
            }
        }
        super.run();
    }

    public void terminate() {
        this.terminated = true;
    }

}
