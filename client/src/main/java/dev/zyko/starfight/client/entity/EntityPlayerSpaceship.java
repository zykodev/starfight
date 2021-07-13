package dev.zyko.starfight.client.entity;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.input.InputManager;
import dev.zyko.starfight.client.util.MathHelper;
import dev.zyko.starfight.protocol.impl.C04PacketPlayOutPlayerData;

public class EntityPlayerSpaceship extends EntitySpaceship {

    private double activePowerUp = -1.0D;
    private int remainingPowerUpTicks;
    private double prevRotation;
    private boolean prevShooting, prevUsingPowerup, shooting, usingPowerUp;

    public EntityPlayerSpaceship(int id, double posX, double posY, double rotation, String name) {
        super(id, posX, posY, rotation, name);
    }

    @Override
    public void updateEntity() {
        if(this.activePowerUp > 0) {
            this.remainingPowerUpTicks--;
            if(this.remainingPowerUpTicks <= 0) {
                this.activePowerUp = -1.0D;
            }
        }
        this.prevShooting = this.shooting;
        this.prevUsingPowerup = this.usingPowerUp;
        this.shooting = StarfightClient.getInstance().getInputManager().isMouseButtonDown(InputManager.MOUSE_LEFT);
        this.usingPowerUp = StarfightClient.getInstance().getInputManager().isMouseButtonDown(InputManager.MOUSE_RIGHT);
        boolean useAction = StarfightClient.getInstance().getGameRenderer().getCurrentScreen() == null;
        if (this.rotation != this.prevRotation || this.prevShooting != this.shooting || this.prevUsingPowerup != this.usingPowerUp) {
            StarfightClient.getInstance().getNetworkManager().sendPacket(new C04PacketPlayOutPlayerData(this.rotation, this.shooting && useAction, this.usingPowerUp && useAction));
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

    public void setRemainingPowerUpTicks(int remainingPowerUpTicks) {
        this.remainingPowerUpTicks = remainingPowerUpTicks;
    }

    public double getActivePowerUp() {
        return activePowerUp;
    }

    public void setActivePowerUp(double activePowerUp) {
        this.activePowerUp = activePowerUp;
    }

    public int getRemainingPowerUpTicks() {
        return remainingPowerUpTicks;
    }

}
