package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.renderer.model.Model;
import dev.zyko.starfight.client.util.MathHelper;
import dev.zyko.starfight.protocol.impl.C04PacketPlayOutPlayerData;

public class EntityPlayerSpaceship extends EntitySpaceship {

    private double prevRotation;
    private boolean prevShooting, prevUsingPowerup, shooting, usingPowerup;

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, rotation, name);
    }

    @Override
    public void updateEntity() {
        this.prevShooting = this.shooting;
        this.prevUsingPowerup = this.usingPowerup;
        this.shooting = StarfightClient.getInstance().getInputManager().isMouseButtonDown(InputManager.MOUSE_LEFT);
        this.usingPowerup = StarfightClient.getInstance().getInputManager().isMouseButtonDown(InputManager.MOUSE_RIGHT);
        if(this.rotation != this.prevRotation || this.prevShooting != this.shooting || this.prevUsingPowerup != this.usingPowerup) {
            StarfightClient.getInstance().getNetworkManager().sendPacket(new C04PacketPlayOutPlayerData(this.rotation, this.shooting, this.usingPowerup));
        }
        super.updateEntity();
        this.prevRotation = rotation;
    }

    @Override
    public void drawEntity(double partialTicks, double x, double y, double width, double height, double rotation) {
        double[] mousePosition = StarfightClient.getInstance().getInputManager().getMousePosition();
        this.rotation = MathHelper.calculateHorizontalAngle(StarfightClient.getInstance().getDisplayManager().getWidth() / 2, StarfightClient.getInstance().getDisplayManager().getHeight() / 2, mousePosition[0], mousePosition[1]) + 90;
        super.drawEntity(partialTicks, x, y, width, height, this.rotation);
    }

}
