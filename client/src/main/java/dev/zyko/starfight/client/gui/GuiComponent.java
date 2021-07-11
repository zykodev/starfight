package dev.zyko.starfight.client.gui;

public abstract class GuiComponent {

    public float posX, posY, width, height;

    public GuiComponent(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    /**
     * Stellt die GUI-Komponente dar.
     *
     * @param mouseX       die aktuelle x-Koordinate des Mauszeigers.
     * @param mouseY       die aktuelle y-Koordinate des Mauszeigers.
     * @param partialTicks Anzahl der vergangenen Ticks seit dem letzten vollständigen Update.
     */
    public abstract void drawComponent(double mouseX, double mouseY, double partialTicks);

    /**
     * Aktualisiert den Status der Komponente.
     *
     * @param mouseX die aktuelle x-Koordinate des Mauszeigers.
     * @param mouseY die aktuelle y-Koordinate des Mauszeigers.
     */
    public abstract void runTick(double mouseX, double mouseY);

    /**
     * @see GuiScreen#mouseButtonPressed(int, double, double)
     */
    public abstract void mouseButtonPressed(int button, double x, double y);

    /**
     * @see GuiScreen#mouseButtonReleased(int, double, double)
     */
    public abstract void mouseButtonReleased(int button, double x, double y);

    /**
     * @see GuiScreen#charInput(char)
     */
    public abstract void charInput(char c);

    /**
     * @see GuiScreen#keyInput(int, int)
     */
    public abstract void keyInput(int keyCode, int action);

    /**
     * Überprüft, ob die Maus auf die Komponente zeigt.
     *
     * @param mouseX die aktuelle x-Koordinate des Mauszeigers.
     * @param mouseY die aktuelle y-Koordinate des Mauszeigers.
     * @return true, wenn die Maus auf die Komponente zeigt, ansonsten false.
     */
    public boolean isHovered(double mouseX, double mouseY) {
        return this.posX <= mouseX && this.posX + this.width >= mouseX && this.posY <= mouseY && this.posY + this.height >= mouseY;
    }

}
