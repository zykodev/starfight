package dev.zyko.starfight.server.world.entity;

import dev.zyko.starfight.protocol.impl.S09PacketPlayOutEffectSpawn;
import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.server.world.entity.stats.PowerUpType;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityProjectile extends EntityMovable {

    private final EntityPlayerSpaceship firedBy;
    private double ticksAlive = 0;

    public EntityProjectile(EntityPlayerSpaceship firedBy, int id, double posX, double posY, double rotation) {
        super(id, posX, posY, 2, 4, rotation);
        this.firedBy = firedBy;
    }

    @Override
    public void updateEntity() {
        if (ticksAlive == 96) {
            StarfightServer.getInstance().getWorld().despawnEntity(this);
            return;
        }
        ticksAlive++;
        double newPosX = this.posX - Math.sin(Math.toRadians(this.rotation)) * 14;
        double newPosY = this.posY + Math.cos(Math.toRadians(this.rotation)) * 14;
        double distance = Math.hypot(Math.abs(newPosX), Math.abs(newPosY));
        if (distance >= StarfightServer.getInstance().getWorld().getRadius()) {
            ticksAlive = 96;
            return;
        }
        this.posX = newPosX;
        this.posY = newPosY;
        final EntityProjectile comparableEntity = this;
        Collection<TileEntity> collidingTileEntities = StarfightServer.getInstance().getWorld().getTileEntityList().parallelStream().filter(new Predicate<TileEntity>() {
            @Override
            public boolean test(TileEntity tileEntity) {
                return comparableEntity.isCollidingWith(tileEntity);
            }
        }).collect(Collectors.toList());
        if (!collidingTileEntities.isEmpty()) {
            TileEntity e = collidingTileEntities.iterator().next();
            if (e instanceof EntityPowerUp) {
                switch ((int) ((EntityPowerUp) e).getType()) {
                    case (int) EntityPowerUp.TYPE_CDR:
                        if (this.firedBy.getPowerUpType() == PowerUpType.NONE || this.firedBy.getPowerUpType() == PowerUpType.CDR) {
                            this.firedBy.setPowerUpType(PowerUpType.CDR);
                        }
                        break;
                    case (int) EntityPowerUp.TYPE_SPEED:
                        if (this.firedBy.getPowerUpType() == PowerUpType.NONE || this.firedBy.getPowerUpType() == PowerUpType.SPEED) {
                            this.firedBy.setPowerUpType(PowerUpType.SPEED);
                        }
                        break;
                    case (int) EntityPowerUp.TYPE_HEALTH:
                        this.firedBy.setHealth(this.firedBy.getHealth() + 1);
                        break;
                    default:
                        break;
                }
                this.spawnParticles(this.posX, this.posY);
                StarfightServer.getInstance().getWorld().despawnEntity(this);
                StarfightServer.getInstance().getWorld().despawnEntity(e);
            }
            return;
        }
        Collection<Entity> collidingEntities = StarfightServer.getInstance().getWorld().getEntityList().parallelStream().filter(new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                return entity != comparableEntity && comparableEntity.isCollidingWith(entity) && entity != getFiredBy();
            }
        }).collect(Collectors.toList());
        if (!collidingEntities.isEmpty()) {
            Entity e = collidingEntities.iterator().next();
            if (e instanceof EntityPlayerSpaceship) {
                StarfightServer.getInstance().getWorld().despawnEntity(this);
                ((EntityPlayerSpaceship) e).setHealth(((EntityPlayerSpaceship) e).getHealth() - 1);
                if (((EntityPlayerSpaceship) e).getHealth() == 0) {
                    double[] newPos = StarfightServer.getInstance().getWorld().getRandomSpawnPosition();
                    this.spawnParticles(e.getPosX(), e.getPosY());
                    e.setPosX(newPos[0]);
                    e.setPosY(newPos[1]);
                    if (((EntityPlayerSpaceship) e).getHealth() < 3)
                        ((EntityPlayerSpaceship) e).setHealth(3);
                    ((EntityPlayerSpaceship) e).setDeaths(((EntityPlayerSpaceship) e).getDeaths() + 1);
                    this.firedBy.setScore(this.firedBy.getScore() + 1);
                    this.firedBy.setHealth(3);
                }
            }
        }
    }

    @Override
    public boolean isCollidingWith(Entity e) {
        if (this.firedBy == e || e instanceof EntityProjectile) return false;
        double diffX = Math.abs(e.posX - this.posX);
        double diffY = Math.abs(e.posY - this.posY);
        return Math.hypot(diffX, diffY) <= 32;
    }

    public boolean isCollidingWith(TileEntity e) {
        double diffX = Math.abs(e.posX - this.posX);
        double diffY = Math.abs(e.posY - this.posY);
        return Math.hypot(diffX, diffY) <= 32;
    }

    public EntityPlayerSpaceship getFiredBy() {
        return firedBy;
    }

    private void spawnParticles(double x, double y) {
        S09PacketPlayOutEffectSpawn packetPlayOutEffectSpawn = new S09PacketPlayOutEffectSpawn(StarfightServer.getInstance().getWorld().getNextEntityID(), x, y);
        StarfightServer.getInstance().getWorld().getPlayerSpaceshipList().forEach(p -> p.sendPacket(packetPlayOutEffectSpawn));
    }

}
