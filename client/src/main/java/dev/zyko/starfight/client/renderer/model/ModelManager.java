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
    }

    public Model getModel(String alias) {
        return this.modelMap.get(alias.toLowerCase(Locale.ROOT));
    }

}
