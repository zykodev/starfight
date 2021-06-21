package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.model.Model;

public class EntitySpaceship extends EntityMovable {

    protected int health;
    protected String name;

    public EntitySpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, 64, 64, StarfightClient.getInstance().getModelManager().getModel("spaceship"), rotation);
        this.name = name;
        this.health = 3;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        super.drawEntity(partialTicks, x, y, width, height, rotation);
        double nameWidth = StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").getStringWidth(this.name);
        StarfightClient.getInstance().getGameRenderer().drawRectangle(x - nameWidth / 2 - 2, y + height / 2 + 2, nameWidth + 4, 19, 0x80333333);
        StarfightClient.getInstance().getFontManager().getFontRenderer("ui/basictext").drawCenteredStringWithShadow(this.name, (float) x, (float) (y + height / 2 + 2), -1);
    }

    public String getName() {
        return name;
    }

}
