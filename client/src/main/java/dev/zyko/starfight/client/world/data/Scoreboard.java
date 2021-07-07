package dev.zyko.starfight.client.world.data;

import dev.zyko.starfight.data.ScoreboardEntry;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scoreboard {

    private CopyOnWriteArrayList<ScoreboardEntry> entryList = new CopyOnWriteArrayList<>();

    public void updateScoreboard(List<ScoreboardEntry> newEntries) {
        this.entryList.clear();
        this.entryList.addAll(newEntries);
    }

    public CopyOnWriteArrayList<ScoreboardEntry> getEntryList() {
        return entryList;
    }

}
