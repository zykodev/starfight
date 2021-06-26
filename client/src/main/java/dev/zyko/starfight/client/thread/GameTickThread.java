package dev.zyko.starfight.client.thread;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.util.TimeHelper;

public class GameTickThread extends Thread {

    private boolean terminated = false;
    private int updatesPerSecond = 0;

    @Override
    public void run() {
        TimeHelper helper = new TimeHelper();
        int updates = 0;
        while(!this.terminated) {
            if(helper.isDelayComplete(1000)) {
                this.updatesPerSecond = updates;
                updates = 0;
                helper.updateSystemTime();
            }
            if(StarfightClient.getInstance().getGameTickTimer().shouldTick()) {
                StarfightClient.getInstance().getGameTickTimer().updateSystemTime();
                if (StarfightClient.getInstance().getGameRenderer().getCurrentScreen() != null) {
                    StarfightClient.getInstance().getGameRenderer().getCurrentScreen().runTick(StarfightClient.getInstance().getInputManager().getMousePosition()[0], StarfightClient.getInstance().getInputManager().getMousePosition()[1]);
                }
                StarfightClient.getInstance().getGameRenderer().getParticleRenderer().tickParticles();
                if(StarfightClient.getInstance().getWorld() != null) {
                    StarfightClient.getInstance().getWorld().tickWorld();
                }
                updates++;
            }
        }
        super.run();
    }

    public int getUpdatesPerSecond() {
        return updatesPerSecond;
    }

    public void terminate() {
        this.terminated = true;
    }

}
