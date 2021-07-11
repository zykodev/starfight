package dev.zyko.starfight.client.renderer.particle;

import dev.zyko.starfight.client.StarfightClient;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleRenderer {

    private final Random random = new Random();
    private final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();

    public void tickParticles() {
        double screenWidth = StarfightClient.getInstance().getDisplayManager().getWidth();
        double screenHeight = StarfightClient.getInstance().getDisplayManager().getHeight();
        for (Particle particle : this.particles) {
            if (particle.getPosX() < -1 || particle.getPosY() < -1) {
                particle.setRotation(particle.getRotation() - 90);
            }
            if (particle.getPosX() > screenWidth + 1 || particle.getPosY() > screenHeight + 1) {
                particle.setRotation(particle.getRotation() + 90);
            }
            particle.onTick();
        }
    }

    public void setup(double x, double y, double width, double height) {
        this.particles.clear();
        for (int i = 1; i <= (width * height) * 0.00005D; i++) {
            double posX = x + random.nextDouble() * width;
            double posY = y + random.nextDouble() * height;
            double rotation = 360 * random.nextDouble();
            this.particles.add(new Particle(-1, rotation, posX, posY));
        }
    }

    public void drawParticles(double partialTicks) {
        for (Particle particle : this.particles) {
            GL11.glPushMatrix();
            double posX = particle.getLastX() + (particle.getPosX() - particle.getLastX()) * partialTicks;
            double posY = particle.getLastY() + (particle.getPosY() - particle.getLastY()) * partialTicks;
            GL11.glTranslated(posX, posY, 0);
            GL11.glRotated(particle.getRotation(), 0, 0, 1);
            StarfightClient.getInstance().getGameRenderer().drawRectangle(-1, -1, 2, 2, -1);
            GL11.glPopMatrix();
        }

        /* Code für coolen Plexus-Effekt 8)
         * Leider kaum performant, daher nicht verwendet.
        for (Particle particle : this.particles) {
            for (Particle particle2 : this.particles) {
                if(particle == particle2) continue;
                double dist = Math.hypot(particle2.getPosX() - particle.getPosX(), particle2.getPosY() - particle.getPosY());
                if(dist < 100) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    double alpha = 1 - (dist / 100.0D);
                    System.out.println(alpha);
                    GL11.glColor4d(1, 1, 1, alpha);
                    GL11.glBegin(GL11.GL_LINES);
                    {
                        GL11.glVertex2d(particle.getPosX(), particle.getPosY());
                        GL11.glVertex2d(particle2.getPosX(), particle2.getPosY());
                    }
                    GL11.glEnd();
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4d(1, 1, 1, 1);
                    GL11.glPopMatrix();
                }
            }
        }*/
    }

}
