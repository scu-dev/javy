package com.ljm12914.javy.game;

public class Hitbox {
    public float x, y, width, height;

    public Hitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Hitbox other) { return ((x < other.x && x + width > other.x) || (x < other.x + other.width && x + width > other.x + other.width)) && ((y < other.y && y + height > other.y) || (y < other.y + other.height && y + height > other.y + other.height)); }
}