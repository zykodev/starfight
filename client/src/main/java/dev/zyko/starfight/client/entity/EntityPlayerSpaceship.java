package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.model.Model;
import dev.zyko.starfight.client.util.MathHelper;

public class EntityPlayerSpaceship extends EntitySpaceship {

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, rotation, name);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        double[] mousePosition = StarfightClient.getInstance().getInputManager().getMousePosition();
        this.rotation = MathHelper.calculateHorizontalAngle(StarfightClient.getInstance().getDisplayManager().getWidth() / 2, StarfightClient.getInstance().getDisplayManager().getHeight() / 2, mousePosition[0], mousePosition[1]) + 90;
        super.drawEntity(partialTicks, x, y, width, height, this.rotation);
    }

}
