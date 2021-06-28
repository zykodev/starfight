package dev.zyko.starfight.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.server.netcode.PlayerConnection;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;
import dev.zyko.starfight.server.thread.ServerTickThread;
import dev.zyko.starfight.server.world.World;
import dev.zyko.starfight.server.world.entity.EntityPowerUp;

import java.util.UUID;

public class StarfightServer extends Server {

    public static final String VERSION = "alpha-indev";
    public static final String SIGNATURE = UUID.randomUUID().toString().replace("-", "");

    private static StarfightServer instance;
    private StarfightLogger logger = new StarfightLogger();
    private ServerTickThread serverTickThread = new ServerTickThread();

    private final World world;

    public StarfightServer() throws Exception {
        this.logger.log(this.getClass(), "Starting Starfight server on port 26800...");
        instance = this;
        this.world = new World(2000.0D);
        this.world.spawnEntity(new EntityPowerUp(this.world.getNextEntityID(), 100, 100, EntityPowerUp.TYPE_SPEED));
        this.serverTickThread.setName("server-tick-thread");
        this.serverTickThread.start();
        Log.set(Log.LEVEL_DEBUG);
        this.prepareNetworking();
        this.bind(26800);
        while(true) {
            this.update(1);
        }
        // this.serverTickThread.terminate();
    }

    private void prepareNetworking() {
        this.addListener(new ServerNetworkHandler());
        PacketRegistry.apply(this.getKryo());
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
