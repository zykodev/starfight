package dev.zyko.starfight.server.world.entity;

import com.esotericsoftware.kryonet.Connection;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.S07PacketPlayOutEntityHealth;
import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.server.netcode.PlayerConnection;
import dev.zyko.starfight.server.netcode.ServerNetworkHandler;

public class EntityPlayerSpaceship extends EntitySpaceship {

    private PlayerConnection connection;
    private boolean shooting, usingPowerup;
    private int ticksSinceLastShot = 0, score = 0, deaths = 0;

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name, PlayerConnection playerConnection) {
        super(id, posX, posY, rotation, name);
        this.name = name;
        this.health = 3;
        this.connection = playerConnection;
        this.connection.setPlayerSpaceship(this);
        this.shooting = this.usingPowerup = false;
        this.ticksSinceLastShot = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        for(EntityPlayerSpaceship p : StarfightServer.getInstance().getWorld().getPlayerSpaceshipList()) {
            p.sendPacket(new S07PacketPlayOutEntityHealth(this.id, this.health));
        }
    }

    @Override
    public void updateEntity() {
        if(this.shooting) {
            if(this.ticksSinceLastShot >= 24) {
                this.ticksSinceLastShot = 0;
                this.shoot();
            } else {
                this.ticksSinceLastShot++;
            }
        } else {
            this.ticksSinceLastShot++;
        }
        super.updateEntity();
    }

    private void shoot() {
        double generalOffsetX = -Math.sin(Math.toRadians(this.rotation)) * 9;
        double generalOffsetY = Math.cos(Math.toRadians(this.rotation)) * 9;

        double leftOffsetPosX = -Math.sin(Math.toRadians(this.rotation - 90)) * 21 + generalOffsetX;
        double leftOffsetPosY = Math.cos(Math.toRadians(this.rotation - 90)) * 21 + generalOffsetY;

        double rightOffsetPosX = -Math.sin(Math.toRadians(this.rotation + 90)) * 21 + generalOffsetX;
        double rightOffsetPosY = Math.cos(Math.toRadians(this.rotation + 90)) * 21 + generalOffsetY;

        EntityProjectile leftProjectile = new EntityProjectile(this, StarfightServer.getInstance().getWorld().getNextEntityID(), this.posX + leftOffsetPosX, this.posY + leftOffsetPosY, this.rotation);
        EntityProjectile rightProjectile = new EntityProjectile(this, StarfightServer.getInstance().getWorld().getNextEntityID(), this.posX + rightOffsetPosX, this.posY + rightOffsetPosY, this.rotation);

        StarfightServer.getInstance().getWorld().spawnEntity(leftProjectile);
        StarfightServer.getInstance().getWorld().spawnEntity(rightProjectile);
    }

    public String getName() {
        return name;
    }

    public PlayerConnection getConnection() {
        return connection;
    }

    public void sendPacket(Packet packet) {
        if(this.connection.isConnected()) {
            this.connection.sendTCP(packet);
        }
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public void setUsingPowerup(boolean usingPowerup) {
        this.usingPowerup = usingPowerup;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

}
