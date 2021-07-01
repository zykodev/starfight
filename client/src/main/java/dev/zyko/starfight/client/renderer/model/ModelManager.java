package dev.zyko.starfight.client.renderer.model;

import dev.zyko.starfight.client.StarfightClient;

import java.util.HashMap;
import java.util.Locale;

public class ModelManager {

    private HashMap<String, Model> modelMap = new HashMap<>();

    private void loadModel(String alias, Model model) {
        this.modelMap.put(alias.toLowerCase(Locale.ROOT), model);
    }

    public void loadModels() {
        this.loadModel("spaceship", new Model(StarfightClient.getInstance().getTextureManager().getTexture("entity/spaceship")));
        this.loadModel("powerup_cdr", new Model(StarfightClient.getInstance().getTextureManager().getTexture("entity/powerup_cdr")));
        this.loadModel("powerup_health", new Model(StarfightClient.getInstance().getTextureManager().getTexture("entity/powerup_health")));
        this.loadModel("powerup_speed", new Model(StarfightClient.getInstance().getTextureManager().getTexture("entity/powerup_speed")));
        this.loadModel("projectile", new Model(StarfightClient.getInstance().getTextureManager().getTexture("entity/projectile")));
    }

    public Model getModel(String alias) {
        return this.modelMap.get(alias.toLowerCase(Locale.ROOT));
    }

}
