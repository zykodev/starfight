package dev.zyko.starfight.client.renderer.texture;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

public class TextureManager {

    private HashMap<String, Texture> textureMap = new HashMap<>();

    public TextureManager() {
    }

    public void loadTextures() throws Exception {
        this.loadTexture("entity/spaceship", "spaceship.png");
    }

    private void loadTexture(String alias, String resourceName) throws Exception {
        InputStream is = ClassLoader.getSystemResourceAsStream("assets/textures/" + resourceName);
        if(is != null) {
            Texture texture = new Texture(is);
            this.textureMap.put(alias.toLowerCase(Locale.ROOT), texture);
        } else throw new Exception("Failed to load texture \"" + alias + "\". Resource missing.");
    }

    public Texture getTexture(String alias) {
        return this.textureMap.get(alias.toLowerCase(Locale.ROOT));
    }

}