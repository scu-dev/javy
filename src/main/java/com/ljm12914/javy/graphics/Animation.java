package com.ljm12914.javy.graphics;
import org.newdawn.slick.Image;

public final class Animation {
    private final Image[] frames;
    private final int ticksPerFrame;
    private int elapsedTicks = 0;

    public Animation(Image[] frames, int ticksPerFrame) {
        if (frames == null) throw new IllegalArgumentException("No frames provided");
        if (ticksPerFrame < 0) throw new IllegalArgumentException("Ticks per frame cannot be negative");
        if (ticksPerFrame == 0 && frames.length != 1) throw new IllegalArgumentException("Must provide exactly 1 frame for static animation");
        this.frames = frames;
        this.ticksPerFrame = ticksPerFrame;
    }

    public void update(int deltaTicks) { elapsedTicks += deltaTicks; }

    public Image current() {
        if (ticksPerFrame == 0) return frames[0];
        int frameIndex = (elapsedTicks / ticksPerFrame) % frames.length;
        return frames[frameIndex];
    }

    public void reset() { elapsedTicks = 0; }
}