package dev.zyko.starfight.protocol.impl;

import dev.zyko.starfight.data.ScoreboardEntry;
import dev.zyko.starfight.protocol.Packet;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class S08PacketPlayOutScoreboardData implements Packet {

    private ArrayList<ScoreboardEntry> scoreboardEntries;

    public S08PacketPlayOutScoreboardData() {
    }

    public S08PacketPlayOutScoreboardData(ArrayList<ScoreboardEntry> scoreboardEntries) {
        this.scoreboardEntries = scoreboardEntries;
    }

    public ArrayList<ScoreboardEntry> getScoreboardEntries() {
        return scoreboardEntries;
    }

}
