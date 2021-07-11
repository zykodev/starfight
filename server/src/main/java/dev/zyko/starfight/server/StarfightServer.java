package dev.zyko.starfight.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.server.netcode.PlayerConnection;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;
import dev.zyko.starfight.server.thread.ServerTickThread;
import dev.zyko.starfight.server.world.World;

import java.util.UUID;

public class StarfightServer extends Server {

    public static final String VERSION = "alpha-indev";
    public static final String SIGNATURE = UUID.randomUUID().toString().replace("-", "");

    private static StarfightServer instance;
    private final World world;
    private StarfightLogger logger = new StarfightLogger();
    private ServerTickThread serverTickThread = new ServerTickThread();

    public StarfightServer() throws Exception {
        this.logger.log(this.getClass(), "Starting Starfight server on port 26800...");
        instance = this;
        this.world = new World(4000.0D);
        this.world.spawnPowerUps();
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

    @Override
    protected Connection newConnection() {
        return new PlayerConnection();
    }
}
