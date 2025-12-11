package com.ljm12914.javy.game;

public class Scoreboard {
    private int score = 0;

    public Scoreboard() {}

    public void add(int score) { this.score += score; }
    public int get() { return score; }
}
