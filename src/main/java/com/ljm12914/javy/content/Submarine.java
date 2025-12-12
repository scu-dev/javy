package com.ljm12914.javy.content;
import com.ljm12914.javy.game.Sprite;
import com.ljm12914.javy.graphics.Animation;
import com.ljm12914.javy.graphics.Assets;
import com.ljm12914.javy.graphics.TextureId;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public final class Submarine extends Sprite<Submarine.State> {
    public static final int width = 75, height = 30;
    public enum State { MOVING_LEFT, MOVING_RIGHT, EXPLODING_LEFT, EXPLODING_RIGHT }

    public final float speed;

    public Submarine(GameContainer gc, float x, float y, float speed, boolean headingRight) {
        super(gc, headingRight ? State.MOVING_RIGHT : State.MOVING_LEFT, x, y, width, height);
        this.speed = speed;
        this.animations.put(State.MOVING_LEFT, new Animation(new Image[]{Assets.get(TextureId.SUBMARINE_LEFT)}, 0));
        this.animations.put(State.MOVING_RIGHT, new Animation(new Image[]{Assets.get(TextureId.SUBMARINE_RIGHT)}, 0));
        this.animations.put(State.EXPLODING_LEFT, new Animation(new Image[]{Assets.get(TextureId.SUBMARINE_LEFT_EXPLODED)}, 0));
        this.animations.put(State.EXPLODING_RIGHT, new Animation(new Image[]{Assets.get(TextureId.SUBMARINE_RIGHT_EXPLODED)}, 0));
    }

    public void hitByBomb() {
        if (state == State.MOVING_LEFT) setState(State.EXPLODING_LEFT);
        else if (state == State.MOVING_RIGHT) setState(State.EXPLODING_RIGHT);
        destroyInTicks(60);
    }

    public void update(int deltaTicks) {
        int windowHeight = gc.getHeight(), windowWidth = gc.getWidth();
        if (hitbox.x < -hitbox.width / 2.0f || hitbox.y < -hitbox.height / 2.0f || hitbox.y > windowHeight + hitbox.height / 2.0f || hitbox.x > windowWidth + hitbox.width / 2.0f) destroyInTicks(0);
        switch (state) {
            case MOVING_LEFT -> hitbox.x -= deltaTicks * speed;
            case MOVING_RIGHT -> hitbox.x += deltaTicks * speed;
        }
        updateSprite(deltaTicks);
    }
    public void render() { renderSprite(); }
}