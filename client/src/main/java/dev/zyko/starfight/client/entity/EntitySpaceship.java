package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.texture.Texture;
import org.lwjgl.opengl.GL11;

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
        Texture texture = StarfightClient.getInstance().getTextureManager().getTexture("ui/heart");
        double size = 16;
        double totalWidth = size * this.health;
        double leftOffset = totalWidth / 2.0D + size;
        for (int i = 1; i <= this.health; i++) {
            GL11.glPushMatrix();
            texture.bindTexture();
            StarfightClient.getInstance().getGameRenderer().drawTexturableRectangle(x - leftOffset + i * size, y + height / 2.0D + 24, size, size);
            texture.unbindTexture();
            GL11.glPopMatrix();
        }
    }

    public String getName() {
        return name;
    }

}
