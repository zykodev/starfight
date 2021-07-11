package dev.zyko.starfight.client.netcode;

import com.esotericsoftware.kryonet.Client;
import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.gui.impl.GuiScreenDisconnected;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.PacketRegistry;
import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.C02PacketDisconnect;
import dev.zyko.starfight.protocol.impl.C03PacketConnect;

public class NetworkManager extends Client {

    /**
     * Aktueller Netzwerk-Status
     */
    private ConnectionStatus status = ConnectionStatus.OFFLINE;

    public NetworkManager() {
        // Log.set(Log.LEVEL_DEBUG);
        PacketRegistry.apply(this.getKryo());
        this.addListener(new ClientNetworkHandler());
        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
    }

    /**
     * Verbindet zu einem Server mit dem angegebenen Nicknamen.
     *
     * @param remoteAddress die Server-Adresse, zu welcher verbunden werden soll.
     * @param nickname      der Nickname, mit welchem verbunden werden soll.
     */
    public void connect(String remoteAddress, String nickname) {
        int port = 26800;
        String hostname = "";
        if (remoteAddress.contains(":")) {
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
                StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenDisconnected("Failed to connect to server:\n" + e.getMessage()));
            }
        }).start();
    }

    /**
     * Trennt die aktuelle Verbindung, sofern eine besteht und fährt sämtliche Netzwerkfunktionalität herunter.
     */
    public void disconnect() {
        if (this.isConnected()) {
            this.sendPacket(new C02PacketDisconnect());
            StarfightClient.getInstance().setWorld(null);
            StarfightClient.getInstance().setPlayerSpaceship(null);
            this.close();
            this.stop();
        }
        this.status = ConnectionStatus.OFFLINE;
    }

    /**
     * Sendet ein Paket an den Server, falls aktuell eine Verbindung besteht.
     *
     * @param packet das Paket, welches an der Server geschickt werden soll.
     */
    public void sendPacket(Packet packet) {
        if (this.isConnected()) {
            this.sendTCP(packet);
        }
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    /**
     * Stellt Verbindungsstatus dar.
     */
    public enum ConnectionStatus {
        CONNECTING("Connecting..."),
        LOGGING_IN("Logging in..."),
        RETRIEVING_WORLD_DATA("Retrieving world data..."),
        CONNECTED("Online"),
        OFFLINE("Offline");

        private String displayName;

        ConnectionStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}
