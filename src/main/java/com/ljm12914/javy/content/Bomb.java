package com.ljm12914.javy.content;
import com.ljm12914.javy.Javy;
import com.ljm12914.javy.graphics.Animation;
import com.ljm12914.javy.graphics.Assets;
import com.ljm12914.javy.game.Sprite;
import com.ljm12914.javy.graphics.TextureId;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public final class Bomb extends Sprite<Bomb.State> {
    public static final int width = 18, height = 14;
    public static final float downwardSpeed = 0.5f;
    public enum State { IDLE, EXPLODING }

    private final float verticalSpeed, horizontalSpeed;

    public Bomb(GameContainer gc, float x, float horizontalSpeed, float verticalSpeed) {
        super(gc, State.IDLE, x, gc.getHeight() * Javy.seaLevelRatio, width, height);
        this.verticalSpeed = verticalSpeed;
        this.horizontalSpeed = horizontalSpeed;
        this.animations.put(State.IDLE, new Animation(new Image[]{Assets.get(TextureId.BOMB)}, 0));
        this.animations.put(State.EXPLODING, new Animation(new Image[]{
            Assets.get(TextureId.EXPLOSION_1),
            Assets.get(TextureId.EXPLOSION_2),
            Assets.get(TextureId.EXPLOSION_3),
        }, 20));
    }

    public void explode() {
        setState(State.EXPLODING);
        destroyInTicks(60);
    }

    public void update(int deltaTicks) {
        int windowHeight = gc.getHeight(), windowWidth = gc.getWidth();
        if (hitbox.x < 0 || hitbox.y < 0 || hitbox.y + hitbox.height / 2.0f > windowHeight || hitbox.x + hitbox.width / 2.0f > windowWidth) destroyInTicks(0);
        if (persisting) {
            hitbox.y += deltaTicks * verticalSpeed;
            hitbox.x += deltaTicks * horizontalSpeed;
        }
        updateSprite(deltaTicks);
    }

    public void render() { renderSprite(); }
}