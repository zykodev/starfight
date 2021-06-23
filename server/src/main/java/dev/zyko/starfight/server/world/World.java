package dev.zyko.starfight.server.world;

import dev.zyko.starfight.server.world.entity.Entity;

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

    public double[] getRandomSpawnPosition() {
        return new double[] {0, 0};
    }

    public void spawnEntity(Entity entity) {
        this.entityList.add(entity);
    }

}
