package dev.zyko.starfight.server.world;

import dev.zyko.starfight.protocol.impl.S04PacketPlayOutEntitySpawn;
import dev.zyko.starfight.protocol.impl.S05PacketPlayOutEntityPosition;
import dev.zyko.starfight.server.world.entity.Entity;
import dev.zyko.starfight.server.world.entity.EntityPlayerSpaceship;

import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private final double radius;
    private final CopyOnWriteArrayList<Entity> entityList = new CopyOnWriteArrayList<>();
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
        for(Entity e : this.entityList) e.updateEntity();
        for(Entity e : this.entityList) {
            if(e instanceof EntityPlayerSpaceship) {
                for(Entity ent : this.entityList) {
                    if(ent instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) e).getNetHandler().sendPacket(new S05PacketPlayOutEntityPosition(ent.getId(), ent.getPosX(), ent.getPosY(), ((EntityPlayerSpaceship) ent).getRotation()));
                    } else {
                        ((EntityPlayerSpaceship) e).getNetHandler().sendPacket(new S05PacketPlayOutEntityPosition(ent.getId(), ent.getPosX(), ent.getPosY(), 0));
                    }
                }
            }
        }
    }

    public double[] getRandomSpawnPosition() {
        return new double[] {0, 0};
    }

    public void despawnEntity(Entity e) {
        this.entityList.remove(e);
    }

    public void spawnEntity(Entity entity) {
        this.entityList.add(entity);
        for(Entity e : this.entityList) {
            if(e instanceof EntityPlayerSpaceship) {
                if(e != entity) {
                    if(entity instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) e).getNetHandler().sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), 1, entity.getPosX(), entity.getPosY(), ((EntityPlayerSpaceship) entity).getRotation(), ((EntityPlayerSpaceship) entity).getName()));
                    } else {
                        ((EntityPlayerSpaceship) e).getNetHandler().sendPacket(new S04PacketPlayOutEntitySpawn(entity.getId(), 2, entity.getPosX(), entity.getPosY(), 0, "Entity"));
                    }
                }
            }
            if(entity instanceof EntityPlayerSpaceship) {
                if(e != entity) {
                    if(e instanceof EntityPlayerSpaceship) {
                        ((EntityPlayerSpaceship) entity).getNetHandler().sendPacket(new S04PacketPlayOutEntitySpawn(e.getId(), 1, e.getPosX(), e.getPosY(), ((EntityPlayerSpaceship) e).getRotation(), ((EntityPlayerSpaceship) e).getName()));
                    } else {
                        ((EntityPlayerSpaceship) entity).getNetHandler().sendPacket(new S04PacketPlayOutEntitySpawn(e.getId(), 2, e.getPosX(), e.getPosY(), 0, "Entity"));
                    }
                }
            }
        }
    }

}
