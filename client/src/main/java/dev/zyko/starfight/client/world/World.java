package dev.zyko.starfight.client.world;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.entity.Entity;
import dev.zyko.starfight.client.entity.EntityMovable;
import dev.zyko.starfight.client.entity.EntityPlayerSpaceship;
import dev.zyko.starfight.client.world.data.Scoreboard;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private CopyOnWriteArrayList<Entity> loadedEntityList = new CopyOnWriteArrayList<>();
    private Scoreboard scoreboard;
    private double radius;

    public World(double radius) {
        this.radius = radius;
        this.scoreboard = new Scoreboard();
    }

    public void loadEntity(Entity entity) {
        if (!this.loadedEntityList.contains(entity))
            this.loadedEntityList.add(entity);
    }

    public void renderWorld(double partialTicks) {
        StarfightClient.getInstance().getGameRenderer().getParticleRenderer().drawParticles(partialTicks);
        EntityPlayerSpaceship playerEntity = StarfightClient.getInstance().getPlayerSpaceship();
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        double circleCenterX = (playerEntity.getPosX() - 0) + screenWidth / 2;
        double circleCenterY = (playerEntity.getPosY() - 0) + screenHeight / 2;

        StarfightClient.getInstance().getGameRenderer().drawCircle(circleCenterX, circleCenterY, this.radius, -1, 2.5F);

        this.loadedEntityList.forEach(entity -> {
            double interpolatedPlayerX = playerEntity.getPosX() + (playerEntity.getLastPosX() - playerEntity.getPosX()) * partialTicks;
            double interpolatedPlayerY = playerEntity.getPosY() + (playerEntity.getLastPosY() - playerEntity.getPosY()) * partialTicks;
            double interpolatedEntityX = entity.getPosX();
            double interpolatedEntityY = entity.getPosY();
            if (entity instanceof EntityMovable) {
                interpolatedEntityX = entity.getPosX() + (((EntityMovable) entity).getLastPosX() - entity.getPosX()) * partialTicks;
                interpolatedEntityY = entity.getPosY() + (((EntityMovable) entity).getLastPosY() - entity.getPosY()) * partialTicks;
            }
            if (entity == playerEntity) {
                interpolatedEntityX = playerEntity.getPosX();
                interpolatedEntityY = playerEntity.getPosY();
                interpolatedPlayerX = playerEntity.getPosX();
                interpolatedPlayerY = playerEntity.getPosY();
            }
            double renderPosX = (interpolatedPlayerX - interpolatedEntityX) + screenWidth / 2;
            double renderPosY = (interpolatedPlayerY - interpolatedEntityY) + screenHeight / 2;

            double rotation = 0;
            if (entity instanceof EntityMovable) {
                rotation = ((EntityMovable) entity).getRotation();
            }
            GL11.glPushMatrix();
            entity.drawEntity(partialTicks, renderPosX, renderPosY, entity.getWidth(), entity.getHeight(), rotation);
            GL11.glPopMatrix();
        });
    }

    public void tickWorld() {
        this.loadedEntityList.forEach(Entity::updateEntity);
    }

    public Entity getEntity(int id) {
        for (Entity e : this.loadedEntityList) {
            if (e.getId() == id)
                return e;
        }
        return null;
    }

    public void unloadEntity(Entity entity) {
        this.loadedEntityList.remove(entity);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
