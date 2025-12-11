package com.ljm12914.javy.game;
import com.ljm12914.javy.graphics.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import java.util.EnumMap;

public abstract class Sprite<S extends Enum<S>> {
    protected S state;
    protected Hitbox hitbox;
    protected EnumMap<S, Animation> animations;
    protected boolean persisting;
    protected int removeCountdown = -1;
    protected final GameContainer gc;

    protected Sprite(GameContainer gc, S initialState, float initialX, float initialY, float initialWidth, float initialHeight) {
        this.gc = gc;
        this.state = initialState;
        this.hitbox = new Hitbox(initialX, initialY, initialWidth, initialHeight);
        this.animations = new EnumMap<>(initialState.getDeclaringClass());
        this.persisting = true;
    }

    public boolean isPersisting() { return this.persisting; }
    public int getRemoveCountdown() { return this.removeCountdown; }
    public void countDown() { if(this.removeCountdown > 0) this.removeCountdown--; }
    public Hitbox getHitbox() { return hitbox; }
    protected void destroyInTicks(int countdown) {
        this.persisting = false;
        this.removeCountdown = countdown;
    }

    public void setState(S state) {
        if (this.state == state) return;
        this.state = state;
        Animation current = animations.get(state);
        if (current != null) current.reset();
    }

    public void updateSprite(int deltaTicks) {
        Animation current = animations.get(state);
        if (current == null) return;
        current.update(deltaTicks);
    }
    public abstract void update(int deltaTicks);

    public void renderSprite() {
        Animation current = animations.get(state);
        if (current == null) return;
        Image currentImage = current.current();
        currentImage.draw(hitbox.x - (hitbox.width / 2), hitbox.y - (hitbox.height / 2), hitbox.width, hitbox.height);
    }
    public abstract void render();
}