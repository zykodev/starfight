package dev.zyko.starfight.client.thread;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.netcode.NetworkManager;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.S01PacketKeepAlive;
import dev.zyko.starfight.util.TimeHelper;

public class GameTickThread extends Thread {

    private boolean terminated = false;
    private int updatesPerSecond = 0;

    /**
     * Aktualsiert die Game-Logik unabhängig von der Grafikperformance.
     * D.h. anstatt 200 Updates bei 200 Bildern pro Sekunde weiterhin 48 Updates pro Sekunde.
     * D.h. anstatt 10 Updates bei 10 Bildern pro Sekunde weiterhin 48 Updates pro Sekunde.
     * Wichtig für Multiplayer.
     */
    @Override
    public void run() {
        TimeHelper helper = new TimeHelper();
        int updates = 0;
        while (!this.terminated) {
            if (helper.isDelayComplete(1000)) {
                this.updatesPerSecond = updates;
                updates = 0;
                helper.updateSystemTime();
                NetworkManager networkManager = StarfightClient.getInstance().getNetworkManager();
                if(networkManager.isConnected()) {
                    networkManager.sendPacket(new C01PacketKeepAlive(System.currentTimeMillis()));
                }
            }
            if (StarfightClient.getInstance().getGameTickTimer().shouldTick()) {
                StarfightClient.getInstance().getGameTickTimer().updateSystemTime();
                if (StarfightClient.getInstance().getGameRenderer().getCurrentScreen() != null) {
                    StarfightClient.getInstance().getGameRenderer().getCurrentScreen().runTick(StarfightClient.getInstance().getInputManager().getMousePosition()[0], StarfightClient.getInstance().getInputManager().getMousePosition()[1]);
                }
                StarfightClient.getInstance().getGameRenderer().getParticleRenderer().tickParticles();
                if (StarfightClient.getInstance().getWorld() != null) {
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
