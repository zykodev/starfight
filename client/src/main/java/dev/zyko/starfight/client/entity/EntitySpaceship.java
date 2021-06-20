package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.model.Model;

public class EntitySpaceship extends EntityMovable {

    protected String name;

    public EntitySpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, 64, 64, StarfightClient.getInstance().getModelManager().getModel("spaceship"), rotation);
        this.name = name;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        super.drawEntity(partialTicks, x, y, width, height, rotation);
    }

    public String getName() {
        return name;
    }

}
