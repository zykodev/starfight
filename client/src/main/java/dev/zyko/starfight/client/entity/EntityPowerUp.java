package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.model.Model;

public class EntityPowerUp extends Entity {

    public static final double TYPE_SPEED = 2.0D;
    public static final double TYPE_HEALTH = 4.0D;
    public static final double TYPE_CDR = 6.0D;

    private double type;

    public EntityPowerUp(int id, double posX, double posY, double type) {
        super(id, posX, posY, 64, 64, getModelForType(type));
        this.type = type;
    }

    private static Model getModelForType(double type) {
        if (type == TYPE_SPEED) {
            return StarfightClient.getInstance().getModelManager().getModel("powerup_speed");
        }
        if (type == TYPE_CDR) {
            return StarfightClient.getInstance().getModelManager().getModel("powerup_cdr");
        }
        if (type == TYPE_HEALTH) {
            return StarfightClient.getInstance().getModelManager().getModel("powerup_health");
        }
        return null;
    }

    public double getType() {
        return type;
    }

    @Override
    public void updateEntity() {

    }

}
