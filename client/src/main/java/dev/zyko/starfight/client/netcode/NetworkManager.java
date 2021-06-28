package dev.zyko.starfight.client.netcode;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.impl.GuiScreenDisconnected;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.C02PacketDisconnect;
import dev.zyko.starfight.protocol.impl.C03PacketConnect;

public class NetworkManager extends Client {

    public enum ConnectionStatus {
        CONNECTING,
        LOGGING_IN,
        RETRIEVING_WORLD_DATA,
        CONNECTED,
        OFFLINE
    }
    private ConnectionStatus status = ConnectionStatus.OFFLINE;

    public NetworkManager() {
        Log.set(Log.LEVEL_DEBUG);
        PacketRegistry.apply(this.getKryo());
        this.addListener(new ClientNetworkHandler());
        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
    }

    public void connect(String remoteAddress, String nickname) throws Exception {
        int port = 26800;
        String hostname = "";
        if(remoteAddress.contains(":")) {
            String[] components = remoteAddress.split(":");
            hostname = components[0];
            port = Integer.parseInt(components[1]);
        } else {
            hostname = remoteAddress;
        }
        this.start();
        final int finalPort = port;
        final String finalHostname = hostname;
        new Thread(() -> {
            try {
                this.status = ConnectionStatus.CONNECTING;
                this.connect(5000, finalHostname, finalPort);
                this.status = ConnectionStatus.LOGGING_IN;
                this.sendPacket(new C01PacketKeepAlive(System.currentTimeMillis()));
                this.sendPacket(new C03PacketConnect(nickname, StarfightClient.VERSION));
            } catch (Exception e) {
                this.stop();
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenDisconnected("Failed to connect to server: " + e.getMessage()));
            }
        }).start();
    }

    public void disconnect() {
        if(this.isConnected()) {
            this.sendPacket(new C02PacketDisconnect());
            this.close();
            this.stop();
        }
        this.status = ConnectionStatus.OFFLINE;
    }

    public void sendPacket(Packet packet) {
        if(this.isConnected()) {
            this.sendTCP(packet);
        }
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

}
