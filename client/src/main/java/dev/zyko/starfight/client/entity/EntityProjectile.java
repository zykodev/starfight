package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;

import java.util.concurrent.CopyOnWriteArrayList;

public class EntityProjectile extends EntityMovable {

    public EntityProjectile(int id, double posX, double posY, double rotation) {
        super(id, posX, posY, 4, 8, StarfightClient.getInstance().getModelManager().getModel("projectile"), rotation);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        super.drawEntity(partialTicks, x, y, width, height, rotation);
    }

}
