package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;

public class EntityEffect extends Entity {

    private int ticksExisted = 0;
    private double renderedRotation = 0;

    public EntityEffect(int id, double posX, double posY) {
        super(id, posX, posY, 96, 96, StarfightClient.getInstance().getModelManager().getModel("explosion_1"));
    }

    @Override
    public void updateEntity() {
        this.ticksExisted++;
        double rotationTicks = 360.0D / 18.0D;
        this.renderedRotation += rotationTicks;
        if (ticksExisted == 2) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_2"));
        }
        if (ticksExisted == 4) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_3"));
        }
        if (ticksExisted == 8) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_4"));
        }
        if (ticksExisted == 12) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_3"));
        }
        if (ticksExisted == 14) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_2"));
        }
        if (ticksExisted == 16) {
            this.setModel(StarfightClient.getInstance().getModelManager().getModel("explosion_1"));
        }
        if (ticksExisted >= 18) {
            StarfightClient.getInstance().getWorld().unloadEntity(this);
        }
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        super.drawEntity(partialTicks, x, y, width, height, this.renderedRotation);
    }

}
