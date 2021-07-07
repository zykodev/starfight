package dev.zyko.starfight.data;

public class ScoreboardEntry {

    private String playerName;
    private int playerRank;
    private double playerScore;

    public ScoreboardEntry(String playerName, double playerScore, int playerRank) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.playerRank = playerRank;
    }

    public ScoreboardEntry() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getPlayerScore() {
        return playerScore;
    }

    public int getPlayerRank() {
        return playerRank;
    }

}
