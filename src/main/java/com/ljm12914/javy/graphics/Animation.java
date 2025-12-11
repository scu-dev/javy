package com.ljm12914.javy.graphics;
import org.newdawn.slick.Image;

public final class Animation {
    private final Image[] frames;
    private final float ticksPerFrame;
    private float elapsedTicks = 0.0f;

    public Animation(Image[] frames, int ticksPerFrame) {
        if (frames == null) throw new IllegalArgumentException("No frames provided");
        if (ticksPerFrame < 0) throw new IllegalArgumentException("Ticks per frame cannot be negative");
        if (ticksPerFrame == 0 && frames.length != 1) throw new IllegalArgumentException("Must provide exactly 1 frame for static animation");
        this.frames = frames;
        this.ticksPerFrame = ticksPerFrame;
    }

    public void update(int deltaTime) { elapsedTicks += deltaTime; }

    public Image current() {
        if (ticksPerFrame == 0) return frames[0];
        int frameIndex = (int) (elapsedTicks / ticksPerFrame) % frames.length;
        return frames[frameIndex];
    }

    public void reset() { elapsedTicks = 0; }
}