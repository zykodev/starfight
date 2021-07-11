package dev.zyko.starfight.server.world;

import dev.zyko.starfight.data.ScoreboardEntry;
import dev.zyko.starfight.protocol.impl.S04PacketPlayOutEntitySpawn;
import dev.zyko.starfight.protocol.impl.S05PacketPlayOutEntityPosition;
import dev.zyko.starfight.protocol.impl.S06PacketPlayOutEntityDespawn;
import dev.zyko.starfight.protocol.impl.S08PacketPlayOutScoreboardData;
import dev.zyko.starfight.server.world.entity.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private final double radius;
    private final CopyOnWriteArrayList<Entity> entityList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TileEntity> tileEntityList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Entity> unloadedEntityList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TileEntity> unloadedTileEntityList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<EntityPlayerSpaceship> playerSpaceshipList = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private int idCounter = 0;

    public World(double radius) {
        this.radius = radius;
    }

    public int getNextEntityID() {
        return idCounter++;
    }

    public CopyOnWriteArrayList<Entity> getEntityList() {
        return entityList;
    }

    public double getRadius() {
        return radius;
    }

    public void tick() {
        for (TileEntity tileEntity : this.unloadedTileEntityList) {
            if (this.playerSpaceshipList.isEmpty()) break;
            boolean shouldLoad = false;
            for (EntityPlayerSpaceship entityPlayerSpaceship : this.playerSpaceshipList) {
                if (entityPlayerSpaceship.distanceTo(tileEntity) < 1750) {
                    shouldLoad = true;
                    break;
                }
            }
            if (shouldLoad) {
                this.loadEntity(tileEntity);
            }
        }
        for (TileEntity tileEntity : this.tileEntityList) {
            if (this.playerSpaceshipList.isEmpty()) {
                this.unloadEntity(tileEntity);
                continue;
            }
            boolean despawnable = true;
            for (EntityPlayerSpaceship entityPlayerSpaceship : this.playerSpaceshipList) {
                if (entityPlayerSpaceship.distanceTo(tileEntity) < 2000) {
                    despawnable = false;
                    break;
                }
            }
            if (despawnable) {
                this.unloadEntity(tileEntity);
            }
        }
        ArrayList<EntityPlayerSpaceship> sortableList = new ArrayList<>(this.playerSpaceshipList);
        sortableList.sort((o1, o2) -> Double.compare(o2.getScore() / (double) (o2.getDeaths() == 0 ? 1 : o2.getDeaths()), o1.getScore() / (double) (o1.getDeaths() == 0 ? 1 : o1.getDeaths())));
        for (EntityPlayerSpaceship entityPlayerSpaceship : this.playerSpaceshipList) {
            ArrayList<ScoreboardEntry> sortedTopList = new ArrayList<>();
            for (EntityPlayerSpaceship scoreboardEntity : sortableList) {
                if (sortableList.indexOf(scoreboardEntity) > 9 && !scoreboardEntity.getName().equalsIgnoreCase(entityPlayerSpaceship.getName()))
                    continue;
                sortedTopList.add(new ScoreboardEntry(scoreboardEntity.getName(), scoreboardEntity.getScore() / (double) (scoreboardEntity.getDeaths() == 0 ? 1 : scoreboardEntity.getDeaths()), sortableList.indexOf(scoreboardEntity) + 1));
            }
            entityPlayerSpaceship.sendPacket(new S08PacketPlayOutScoreboardData(sortedTopList));
        }
        for (Entity e : this.entityList) e.updateEntity();
        for (Entity e : this.entityList) {
            if (e instanceof EntityPlayerSpaceship) {
                for (Entity ent : this.entityList) {
                    if (ent instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) e).sendPacket(new S05PacketPlayOutEntityPosition(ent.getId(), ent.getPosX(), ent.getPosY(), ((EntityPlayerSpaceship) ent).getRotation()));
                    } else {
                        ((EntityPlayerSpaceship) e).sendPacket(new S05PacketPlayOutEntityPosition(ent.getId(), ent.getPosX(), ent.getPosY(), 0));
                    }
                }
            }
        }
    }

    public double[] getRandomSpawnPosition() {
        double i = 360 * this.random.nextDouble();
        double x = Math.sin(Math.toRadians(i)) * this.random.nextDouble() * (this.radius - (this.radius / 10.0D));
        double y = Math.cos(Math.toRadians(i)) * this.random.nextDouble() * (this.radius - (this.radius / 10.0D));
        return new double[]{x, y};
    }

    public void despawnEntity(Entity e) {
        this.entityList.remove(e);
        if (e instanceof EntityPlayerSpaceship)
            this.playerSpaceshipList.remove(e);
        for (Entity entity : this.entityList) {
            if (entity instanceof EntityPlayerSpaceship) {
                ((EntityPlayerSpaceship) entity).sendPacket(new S06PacketPlayOutEntityDespawn(e.getId()));
            }
        }
    }

    private void loadEntity(TileEntity e) {
        this.spawnEntity(e);
        this.unloadedTileEntityList.remove(e);
    }

    private void unloadEntity(TileEntity e) {
        this.despawnEntity(e);
        this.unloadedTileEntityList.add(e);
    }

    public void despawnEntity(TileEntity e) {
        this.tileEntityList.remove(e);
        for (EntityPlayerSpaceship entityPlayerSpaceship : this.playerSpaceshipList) {
            entityPlayerSpaceship.sendPacket(new S06PacketPlayOutEntityDespawn(e.getId()));
        }
    }

    public void spawnEntity(Entity entity) {
        this.entityList.add(entity);
        if (entity instanceof EntityPlayerSpaceship)
            this.playerSpaceshipList.add((EntityPlayerSpaceship) entity);
        for (Entity e : this.entityList) {
            if (e instanceof EntityPlayerSpaceship) {
                if (e != entity) {
                    if (entity instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) e).sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), S04PacketPlayOutEntitySpawn.SPACESHIP, entity.getPosX(), entity.getPosY(), ((EntityPlayerSpaceship) entity).getRotation(), ((EntityPlayerSpaceship) entity).getName()));
                    } else if (entity instanceof EntityProjectile) {
                        ((EntityPlayerSpaceship) e).sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), S04PacketPlayOutEntitySpawn.PROJECTILE, entity.getPosX(), entity.getPosY(), ((EntityProjectile) entity).getRotation(), "Projectile"));
                    } else {
                        ((EntityPlayerSpaceship) e).sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), 0, entity.getPosX(), entity.getPosY(), 0, "Entity"));
                    }
                }
            }
            if (entity instanceof EntityPlayerSpaceship) {
                this.tileEntityList.forEach(te -> {
                    if (te instanceof EntityPowerUp) {
                        ((EntityPlayerSpaceship) e).sendPacket(new S04PacketPlayOutEntitySpawn(te.getId(), S04PacketPlayOutEntitySpawn.POWER_UP, te.getPosX(), te.getPosY(), ((EntityPowerUp) te).getType(), "Power Up"));
                    } else {
                        ((EntityPlayerSpaceship) e).sendPacket(new S04PacketPlayOutEntitySpawn(te.getId(), 0, entity.getPosX(), te.getPosY(), 0, "Tile Entity"));
                    }
                });
                if (e != entity) {
                    if (e instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) entity).sendPacket(new S04PacketPlayOutEntitySpawn(e.getId(), S04PacketPlayOutEntitySpawn.SPACESHIP, e.getPosX(), e.getPosY(), ((EntityPlayerSpaceship) e).getRotation(), ((EntityPlayerSpaceship) e).getName()));
                    } else if (e instanceof EntityProjectile) {
                        ((EntityPlayerSpaceship) entity).sendPacket(new S04PacketPlayOutEntitySpawn(e.getId(), S04PacketPlayOutEntitySpawn.PROJECTILE, e.getPosX(), e.getPosY(), ((EntityProjectile) e).getRotation(), "Projectile"));
                    } else {
                        ((EntityPlayerSpaceship) entity).sendPacket(new S04PacketPlayOutEntitySpawn(e.getId(), 0, e.getPosX(), e.getPosY(), 0, "Entity"));
                    }
                }
            }
        }
    }

    public void spawnEntity(TileEntity entity) {
        this.tileEntityList.add(entity);
        this.playerSpaceshipList.forEach(ps -> {
            if (entity instanceof EntityPowerUp) {
                ps.sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), S04PacketPlayOutEntitySpawn.POWER_UP, entity.getPosX(), entity.getPosY(), ((EntityPowerUp) entity).getType(), "Power Up"));
            } else {
                ps.sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), 0, entity.getPosX(), entity.getPosY(), 0, "Tile Entity"));
            }
        });
    }

    public void spawnPowerUps() {
        for (double i = 0; i < 360; i += 6.0D) {
            double x = Math.sin(Math.toRadians(i)) * this.random.nextDouble() * this.radius;
            double y = Math.cos(Math.toRadians(i)) * this.random.nextDouble() * this.radius;
            EntityPowerUp powerUp = new EntityPowerUp(this.getNextEntityID(), x, y, 2.0D + 2.0D * this.random.nextInt(3));
            this.spawnEntity(powerUp);
        }
    }

    public CopyOnWriteArrayList<TileEntity> getTileEntityList() {
        return tileEntityList;
    }

    public CopyOnWriteArrayList<Entity> getUnloadedEntityList() {
        return unloadedEntityList;
    }

    public CopyOnWriteArrayList<TileEntity> getUnloadedTileEntityList() {
        return unloadedTileEntityList;
    }

    public CopyOnWriteArrayList<EntityPlayerSpaceship> getPlayerSpaceshipList() {
        return playerSpaceshipList;
    }
}
