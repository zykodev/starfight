package dev.zyko.starfight.client.renderer.font;

import java.util.HashMap;
import java.util.Locale;

public class FontManager {

    private HashMap<String, FontRenderer> fontRendererMap = new HashMap<>();

    public void loadFonts() throws Exception {
        this.loadFont("ui/basictext", "raleway.ttf", 18);
        this.loadFont("ui/logo", "prequeldemo.otf", 48);
    }

    private void loadFont(String alias, String resourceName, int size) throws Exception {
        FontRenderer fontRenderer = new FontRenderer(alias, ClassLoader.getSystemResourceAsStream("assets/fonts/" + resourceName), size);
        this.fontRendererMap.put(alias.toLowerCase(Locale.ROOT), fontRenderer);
    }

    public FontRenderer getFontRenderer(String alias) {
        return this.fontRendererMap.get(alias.toLowerCase(Locale.ROOT));
    }

}
