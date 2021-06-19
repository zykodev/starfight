package dev.zyko.starfight.client.renderer.model.impl;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.renderer.model.Model;
import dev.zyko.starfight.client.renderer.texture.Texture;
import dev.zyko.starfight.client.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModelSpaceship extends Model {

    private double rotation;
    private int color;

    public ModelSpaceship(int color) {
        super(StarfightClient.getInstance().getTextureManager().getTexture("entity/spaceship"));
    }

    @Override
    public void applyModelTransformation() {
        super.applyModelTransformation();
        double centerX = StarfightClient.getInstance().getDisplayManager().getWidth() / 2.0D;
        double centerY = StarfightClient.getInstance().getDisplayManager().getHeight() / 2.0D;
        double[] mouseCoords = StarfightClient.getInstance().getInputManager().getMousePosition();
        GL11.glTranslated(centerX, centerY, 0);
        GL11.glRotated(MathHelper.calculateHorizontalAngle(centerX, centerY, mouseCoords[0], mouseCoords[1]) + 90, 0, 0, 1);
    }

    @Override
    public void drawModel(double x, double y, double width, double height, double partialTicks) {
        super.drawModel(x, y, width, height, partialTicks);
    }

}
