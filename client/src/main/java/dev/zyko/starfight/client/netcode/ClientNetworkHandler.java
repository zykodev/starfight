package dev.zyko.starfight.client.netcode;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.entity.*;
import dev.zyko.starfight.client.gui.impl.GuiScreenDisconnected;
import dev.zyko.starfight.client.world.World;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.*;

public class ClientNetworkHandler extends Listener {

    /**
     * Latenz zwischen Server und Client.
     */
    private long latency;

    /**
     * Verarbeitet empfangene Pakete.
     *
     * @param connection die Verbindung, auf welcher die Daten empfangen wurden.
     * @param o          die Daten, die empfangen wurden.
     */
    @Override
    public void received(Connection connection, Object o) {
        if (!(o instanceof Packet)) return;
        Packet msg = (Packet) o;
        if (msg instanceof S01PacketKeepAlive) {
            this.latency = System.currentTimeMillis() - ((S01PacketKeepAlive) msg).getSystemTime();
        }
        if (msg instanceof S03PacketAcceptConnection) {
            StarfightClient.getInstance().getNetworkManager().setStatus(NetworkManager.ConnectionStatus.CONNECTED);
            S03PacketAcceptConnection packet = (S03PacketAcceptConnection) msg;
            World world = new World(packet.getWorldRadius());
            EntityPlayerSpaceship entityPlayerSpaceship = new EntityPlayerSpaceship(packet.getEntityId(), packet.getPosX(), packet.getPosY(), 0, packet.getNickname());
            world.loadEntity(entityPlayerSpaceship);
            StarfightClient.getInstance().setWorld(world);
            StarfightClient.getInstance().setPlayerSpaceship(entityPlayerSpaceship);
            double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
            double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
            StarfightClient.getInstance().getGameRenderer().getParticleRenderer().setup(packet.getPosX() - screenWidth / 2, packet.getPosY() - screenHeight / 2, screenWidth, screenHeight);
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(null);
        }
        if (msg instanceof S02PacketDisconnect) {
            StarfightClient.getInstance().getNetworkManager().setStatus(NetworkManager.ConnectionStatus.OFFLINE);
            S02PacketDisconnect packet = (S02PacketDisconnect) msg;
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenDisconnected(packet.getMessage()));
        }
        if (msg instanceof S04PacketPlayOutEntitySpawn) {
            S04PacketPlayOutEntitySpawn packet = (S04PacketPlayOutEntitySpawn) msg;
            if (packet.getType() == S04PacketPlayOutEntitySpawn.SPACESHIP) {
                EntitySpaceship entitySpaceship = new EntitySpaceship(packet.getEntityId(), packet.getPosX(), packet.getPosY(), packet.getRotation(), packet.getName());
                StarfightClient.getInstance().getWorld().loadEntity(entitySpaceship);
            }
            if (packet.getType() == S04PacketPlayOutEntitySpawn.POWER_UP) {
                EntityPowerUp entityPowerUp = new EntityPowerUp(packet.getEntityId(), packet.getPosX(), packet.getPosY(), packet.getRotation());
                StarfightClient.getInstance().getWorld().loadEntity(entityPowerUp);
            }
            if (packet.getType() == S04PacketPlayOutEntitySpawn.PROJECTILE) {
                EntityProjectile entityProjectile = new EntityProjectile(packet.getEntityId(), packet.getPosX(), packet.getPosY(), packet.getRotation());
                StarfightClient.getInstance().getWorld().loadEntity(entityProjectile);
            }
        }
        if (msg instanceof S05PacketPlayOutEntityPosition) {
            int id = ((S05PacketPlayOutEntityPosition) msg).getId();
            double x = ((S05PacketPlayOutEntityPosition) msg).getPosX();
            double y = ((S05PacketPlayOutEntityPosition) msg).getPosY();
            double rot = ((S05PacketPlayOutEntityPosition) msg).getRotation();
            Entity entity = StarfightClient.getInstance().getWorld().getEntity(id);
            if (entity != null) {
                entity.setPosX(x);
                entity.setPosY(y);
                if (entity instanceof EntityMovable && entity != StarfightClient.getInstance().getPlayerSpaceship() && !(entity instanceof EntityProjectile)) {
                    ((EntityMovable) entity).setRotation(rot);
                }
            }
        }
        if (msg instanceof S06PacketPlayOutEntityDespawn) {
            int id = ((S06PacketPlayOutEntityDespawn) msg).getEntityId();
            Entity entity = StarfightClient.getInstance().getWorld().getEntity(id);
            if (entity != null) {
                StarfightClient.getInstance().getWorld().unloadEntity(entity);
            }
        }
        if (msg instanceof S07PacketPlayOutEntityHealth) {
            int id = ((S07PacketPlayOutEntityHealth) msg).getEntityId();
            Entity e = StarfightClient.getInstance().getWorld().getEntity(id);
            if (e instanceof EntitySpaceship) {
                ((EntitySpaceship) e).setHealth(((S07PacketPlayOutEntityHealth) msg).getHealth());
            }
        }
        if (msg instanceof S08PacketPlayOutScoreboardData) {
            StarfightClient.getInstance().getWorld().getScoreboard().updateScoreboard(((S08PacketPlayOutScoreboardData) msg).getScoreboardEntries());
        }
        if (msg instanceof S09PacketPlayOutEffectSpawn) {
            StarfightClient.getInstance().getWorld().loadEntity(new EntityEffect(((S09PacketPlayOutEffectSpawn) msg).getEntityId(), ((S09PacketPlayOutEffectSpawn) msg).getPosX(), ((S09PacketPlayOutEffectSpawn) msg).getPosY()));
        }
        if (msg instanceof S10PacketPlayOutPowerUpStatus) {
            if(((S10PacketPlayOutPowerUpStatus) msg).getType() > 0) {
                StarfightClient.getInstance().getPlayerSpaceship().setActivePowerUp(((S10PacketPlayOutPowerUpStatus) msg).getType());
                StarfightClient.getInstance().getPlayerSpaceship().setRemainingPowerUpTicks(((S10PacketPlayOutPowerUpStatus) msg).getDuration());
            }
        }
        super.received(connection, o);
    }

    /**
     * Ausgel??st, sobald die Verbindung beendet wird.
     *
     * @param connection die Verbindung, die geschlossen wurde.
     */
    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    public long getLatency() {
        return latency;
    }

}
