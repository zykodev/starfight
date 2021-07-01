package dev.zyko.starfight.server.netcode;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.*;
import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.server.world.entity.Entity;
import dev.zyko.starfight.server.world.entity.EntityPlayerSpaceship;

public class ServerNetworkHandler extends Listener {

    @Override
    public void received(Connection connection, Object o) {
        if(!(o instanceof Packet)) return;
        PlayerConnection playerConnection = (PlayerConnection) connection;
        Packet packetRaw = (Packet) o;
        if(packetRaw instanceof C01PacketKeepAlive) {
            connection.sendTCP(new S01PacketKeepAlive(((C01PacketKeepAlive) packetRaw).getSystemTime()));
        }
        if(playerConnection.getPlayerSpaceship() == null) {
            if(packetRaw instanceof C03PacketConnect) {
                C03PacketConnect packet = (C03PacketConnect) packetRaw;
                if(packet.getVersion().equalsIgnoreCase(StarfightServer.VERSION)) {
                    for(Entity e : StarfightServer.getInstance().getWorld().getEntityList()) {
                        if(e instanceof EntityPlayerSpaceship) {
                            if(((EntityPlayerSpaceship) e).getName().equalsIgnoreCase(packet.getNickname())) {
                                connection.sendTCP(new S02PacketDisconnect("The name you entered is already in use. Please choose another name."));
                                connection.close();
                                return;
                            }
                        }
                    }
                    if(packet.getNickname().equalsIgnoreCase("forbidden")) {
                        connection.sendTCP(new S02PacketDisconnect("That name is forbidden."));
                        connection.close();
                        return;
                    }
                    double[] entitySpawnPosition = StarfightServer.getInstance().getWorld().getRandomSpawnPosition();
                    int entityId = StarfightServer.getInstance().getWorld().getNextEntityID();
                    EntityPlayerSpaceship entitySpaceship = new EntityPlayerSpaceship(entityId, entitySpawnPosition[0], entitySpawnPosition[1], 0, packet.getNickname(), playerConnection);
                    entitySpaceship.sendPacket(new S03PacketAcceptConnection(entityId, packet.getNickname(), entitySpawnPosition[0], entitySpawnPosition[1], StarfightServer.getInstance().getWorld().getRadius()));
                    StarfightServer.getInstance().getWorld().spawnEntity(entitySpaceship);
                } else {
                    connection.sendTCP(new S02PacketDisconnect("The client version does not match the server version. Please update."));
                    connection.close();
                }
            }
        } else {
            if(packetRaw instanceof C04PacketPlayOutPlayerData) {
                C04PacketPlayOutPlayerData packet = (C04PacketPlayOutPlayerData) packetRaw;
                playerConnection.getPlayerSpaceship().setRotation(packet.getRotation());
                playerConnection.getPlayerSpaceship().setShooting(packet.isShooting());
                playerConnection.getPlayerSpaceship().setUsingPowerup(packet.isUsingPowerup());
            }
            if(packetRaw instanceof C02PacketDisconnect) {
                StarfightServer.getInstance().getWorld().despawnEntity(playerConnection.getPlayerSpaceship());
                playerConnection.close();
            }
        }
        super.received(connection, o);
    }

    @Override
    public void disconnected(Connection connection) {
        PlayerConnection playerConnection = (PlayerConnection) connection;
        if(playerConnection.getPlayerSpaceship() != null)
            StarfightServer.getInstance().getWorld().despawnEntity(playerConnection.getPlayerSpaceship());
        super.disconnected(connection);
    }
}
