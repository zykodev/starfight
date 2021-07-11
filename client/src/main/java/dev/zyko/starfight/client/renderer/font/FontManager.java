package dev.zyko.starfight.client.renderer.font;

import java.util.HashMap;
import java.util.Locale;

public class FontManager {

    /**
     * Eine Map, welche die einzelnen Font-Renderer mit ihren Namen enthält.
     */
    private HashMap<String, FontRenderer> fontRendererMap = new HashMap<>();

    /**
     * Lädt Schriftarten aus den Ressourcen, erstellt die Font-Renderer und legt sie in der obigen Map ab.
     *
     * @throws Exception, falls es ein Problem beim beschriebenen Vorgang gab.
     */
    public void loadFonts() throws Exception {
        this.loadFont("ui/basictext", "raleway.ttf", 18);
        this.loadFont("ui/logo", "prequeldemo.otf", 48);
    }

    /**
     * Lädt eine Schriftart aus einer Ressource und erstellt den Font-Renderer.
     *
     * @param alias        der Name, unter welchem die Font später verwendet werden soll.
     * @param resourceName die Ressource, welche die Schriftartdaten enthält.
     * @param size         die Schriftgröße.
     */
    private void loadFont(String alias, String resourceName, int size) {
        FontRenderer fontRenderer = new FontRenderer(alias, ClassLoader.getSystemResourceAsStream("assets/fonts/" + resourceName), size);
        this.fontRendererMap.put(alias.toLowerCase(Locale.ROOT), fontRenderer);
    }

    /**
     * Gibt einen Font-Renderer anhand seines Namens aus der obigen Map zurück.
     *
     * @param alias der Name des zu suchenden Font-Renderers.
     * @return den Font-Renderer, falls er existiert, ansonsten null.
     */
    public FontRenderer getFontRenderer(String alias) {
        return this.fontRendererMap.get(alias.toLowerCase(Locale.ROOT));
    }

}
