package dev.zyko.starfight.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiScreen {

    /**
     * Eine Liste, welche alle GUI-Komponenten (Buttons, Textzeilen, etc.) dieses Menüs enthält.
     */
    protected List<GuiComponent> componentList = new ArrayList<GuiComponent>();

    public GuiScreen() {
        this.initializeComponents();
    }

    /**
     * Wird durch tatsächliche Implementierung eines Menüs (bspw. Hauptmenü) überschrieben, um die Buttons und Textzeilen zu initialisieren.
     */
    protected void initializeComponents() {
    }

    /**
     * Aktualisiert den Zustand der GUI-Komponenten.
     *
     * @param mouseX aktuelle x-Koordinate der Maus.
     * @param mouseY aktuelle y-Koordiante der Maus.
     */
    public void runTick(double mouseX, double mouseY) {
        this.componentList.forEach(c -> c.runTick(mouseX, mouseY));
    }

    /**
     * Stellt das Menü grundlegend dar. Wird durch "vollwertige" GuiScreens überschrieben.
     *
     * @param mouseX       aktuelle x-Koordinate der Maus.
     * @param mouseY       aktuelle y-Koordiante der Maus.
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        this.componentList.forEach(c -> c.drawComponent(mouseX, mouseY, partialTicks));
    }

    /**
     * Leitet Maus-Eingabe an die GUI-Komponenten weiter.
     *
     * @param button ID der gedrückten Maustaste.
     * @param x      aktuelle x-Koordinate der Maus.
     * @param y      aktuelle y-Koordiante der Maus.
     */
    public void mouseButtonPressed(int button, double x, double y) {
        this.componentList.forEach(c -> c.mouseButtonPressed(button, x, y));
    }

    /**
     * Leitet Maus-Eingabe an die GUI-Komponenten weiter.
     *
     * @param button ID der losgelassenen Maustaste.
     * @param x      aktuelle x-Koordinate der Maus.
     * @param y      aktuelle y-Koordiante der Maus.
     */
    public void mouseButtonReleased(int button, double x, double y) {
        this.componentList.forEach(c -> c.mouseButtonReleased(button, x, y));
    }

    /**
     * Leitet Tastatur-Eingabe an die GUI-Komponenten in Form von chars weiter.
     *
     * @param c das Symbol der gedrückten Taste.
     */
    public void charInput(char c) {
        this.componentList.forEach(co -> co.charInput(c));
    }

    /**
     * Leitet Tastatur-Eingabe an die GUI-Komponenten in Form von chars weiter.
     *
     * @param keyCode die ID der gedrückten Taste.
     * @param action  die ID der Aktion, welche ausgeführt wurde. (Drücken/Loslassen)
     */
    public void keyInput(int keyCode, int action) {
        this.componentList.forEach(co -> co.keyInput(keyCode, action));
    }

    public GuiScreen clone() {
        return null;
    }

}
