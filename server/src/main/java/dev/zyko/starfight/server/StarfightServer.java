package dev.zyko.starfight.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.server.netcode.PlayerConnection;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;
import dev.zyko.starfight.server.thread.ServerTickThread;
import dev.zyko.starfight.server.world.World;

public class StarfightServer extends Server {

    public static final String VERSION = "alpha-indev";

    private static StarfightServer instance;
    private final World world;
    private StarfightLogger logger = new StarfightLogger();
    private ServerTickThread serverTickThread = new ServerTickThread();

    /**
     * Startet den Server.
     *
     * @throws Exception, falls der Server nicht gestartet werden konnte.
     */
    public StarfightServer() throws Exception {
        this.logger.log(this.getClass(), "Starting Starfight server on port 26800...");
        instance = this;
        this.world = new World(2000.0D);
        this.world.spawnPowerUps(25);
        this.serverTickThread.setName("server-tick-thread");
        this.serverTickThread.start();
        // Log.set(Log.LEVEL_DEBUG);
        this.prepareNetworking();
        this.bind(26800);
        while (true) {
            this.update(1);
        }
        // this.serverTickThread.terminate();
    }

    public static StarfightServer getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        try {
            StarfightServer server = new StarfightServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bereitet den Server vor dem eigentlichen Start vor.
     */
    private void prepareNetworking() {
        this.addListener(new ServerNetworkHandler());
        PacketRegistry.apply(this.getKryo());
    }

    public StarfightLogger getLogger() {
        return logger;
    }

    public World getWorld() {
        return world;
    }

    /**
     * Gibt ein neues Connection-Objekt bei einer neuen Verbindung zurück.
     *
     * @return ein neues PlayerConnection-Objekt.
     */
    @Override
    protected Connection newConnection() {
        return new PlayerConnection();
    }
}
