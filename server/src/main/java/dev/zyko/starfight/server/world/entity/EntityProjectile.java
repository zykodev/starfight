package dev.zyko.starfight.server.world.entity;

import dev.zyko.starfight.server.StarfightServer;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
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
        if(ticksAlive == 96) {
            StarfightServer.getInstance().getWorld().despawnEntity(this);
            return;
        }
        ticksAlive++;
        double newPosX = this.posX - Math.sin(Math.toRadians(this.rotation)) * 14;
        double newPosY = this.posY + Math.cos(Math.toRadians(this.rotation)) * 14;
        double distance = Math.hypot(Math.abs(newPosX), Math.abs(newPosY));
        if(distance >= StarfightServer.getInstance().getWorld().getRadius()) {
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
        if(!collidingTileEntities.isEmpty()) {
            TileEntity e = collidingTileEntities.iterator().next();
            if(e instanceof EntityPowerUp) {
                StarfightServer.getInstance().getWorld().despawnEntity(this);
                StarfightServer.getInstance().getWorld().despawnEntity(e);
                this.firedBy.setHealth(this.firedBy.getHealth() - 1);
            }
            return;
        }
        Collection<Entity> collidingEntities = StarfightServer.getInstance().getWorld().getEntityList().parallelStream().filter(new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                return entity != comparableEntity && comparableEntity.isCollidingWith(entity) && entity != getFiredBy();
            }
        }).collect(Collectors.toList());
        if(!collidingEntities.isEmpty()) {
            Entity e = collidingEntities.iterator().next();
            if(e instanceof EntityPlayerSpaceship) {
                StarfightServer.getInstance().getWorld().despawnEntity(this);
                ((EntityPlayerSpaceship) e).setHealth(((EntityPlayerSpaceship) e).getHealth() - 1);
            }
        }
    }

    @Override
    public boolean isCollidingWith(Entity e) {
        if(this.firedBy == e || e instanceof EntityProjectile) return false;
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

}
