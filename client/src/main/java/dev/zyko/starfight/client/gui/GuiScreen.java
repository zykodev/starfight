package dev.zyko.starfight.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiScreen {

    protected List<GuiComponent> componentList = new ArrayList<GuiComponent>();

    public GuiScreen() {
        this.initializeComponents();
    }

    protected void initializeComponents() {
    }

    public void runTick(double mouseX, double mouseY) {
        this.componentList.forEach(c -> c.runTick(mouseX, mouseY));
    }

    public void drawScreen(double mouseX, double mouseY, double partialTicks) {
        this.componentList.forEach(c -> c.drawComponent(mouseX, mouseY, partialTicks));
    }

}
