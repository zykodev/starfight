package dev.zyko.starfight.client.thread;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.util.TimeHelper;

public class GameTickThread extends Thread {

    private boolean terminated = false;

    @Override
    public void run() {
        TimeHelper helper = new TimeHelper();
        int updates = 0;
        while(!this.terminated) {
            if(helper.isDelayComplete(1000)) {
                System.out.println("Updates: " + updates);
                updates = 0;
                helper.updateSystemTime();
            }
            if(StarfightClient.getInstance().getGameTickTimer().shouldTick()) {
                StarfightClient.getInstance().getGameTickTimer().updateSystemTime();
                if (StarfightClient.getInstance().getGameRenderer().getCurrentScreen() != null) {
                    StarfightClient.getInstance().getGameRenderer().getCurrentScreen().runTick(StarfightClient.getInstance().getInputManager().getMousePosition()[0], StarfightClient.getInstance().getInputManager().getMousePosition()[1]);
                }
                updates++;
            }
        }
        super.run();
    }

    public void terminate() {
        this.terminated = true;
    }

}
