package com.ljm12914.javy.content;
import com.ljm12914.javy.Javy;
import com.ljm12914.javy.graphics.Animation;
import com.ljm12914.javy.graphics.Assets;
import com.ljm12914.javy.game.Sprite;
import com.ljm12914.javy.graphics.TextureId;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public final class Warship extends Sprite<Warship.State> {
    public static final int width = 100, height = 40;
    public static final float moveSpeed = 0.6f;
    public enum State { MOVING_LEFT, MOVING_RIGHT }

    public Warship(GameContainer gc) {
        super(gc, State.MOVING_RIGHT, width / 2.0f, gc.getHeight() * Javy.seaLevelRatio, width, height);
        this.animations.put(State.MOVING_LEFT, new Animation(new Image[]{Assets.get(TextureId.WARSHIP_LEFT)}, 0));
        this.animations.put(State.MOVING_RIGHT, new Animation(new Image[]{Assets.get(TextureId.WARSHIP_RIGHT)}, 0));
    }

    public void update(int deltaTicks) { updateSprite(deltaTicks); }
    public void render() { renderSprite(); }

    public void goLeft(int deltaTicks, float modifier) {
        boolean moved = false;
        float actualSpeed = moveSpeed * modifier;
        for (int i = 0; i < deltaTicks; i++) if(hitbox.x - actualSpeed >= width / 2.0f) {
            if (!moved) moved = true;
            hitbox.x -= actualSpeed;
        }
        if (moved) setState(State.MOVING_LEFT);
    }

    public void goRight(int deltaTicks, float modifier) {
        boolean moved = false;
        float actualSpeed = moveSpeed * modifier;
        for (int i = 0; i < deltaTicks; i++) if(hitbox.x + actualSpeed <= gc.getWidth() - width / 2.0f) {
            if (!moved) moved = true;
            hitbox.x += actualSpeed;
        }
        if (moved) setState(State.MOVING_RIGHT);
    }

    public float getX() { return hitbox.x; }
}