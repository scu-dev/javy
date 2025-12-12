package com.ljm12914.javy;
import java.util.ArrayList;
import java.util.List;

import com.ljm12914.javy.content.Bomb;
import com.ljm12914.javy.content.Submarine;
import com.ljm12914.javy.content.Warship;
import com.ljm12914.javy.game.KeyPress;
import com.ljm12914.javy.game.Scoreboard;
import com.ljm12914.javy.graphics.Assets;
import com.ljm12914.javy.game.Sprite;
import com.ljm12914.javy.graphics.Font;
import com.ljm12914.javy.graphics.TextureId;
import com.ljm12914.javy.graphics.Util;
import com.ljm12914.javy.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.Color;

public final class Javy extends BasicGame {
    private static final String splash = "Javy By LJM12914\n\nA / <-: Drive Left\nD / ->: Drive Right\nCtrl: Speed Up\nSpace: Release Bomb\n\nPress Any Key to Continue";
    public static final float seaLevelRatio = 0.41f, ctrlModifier = 1.5f;

    public int size;
    private final List<Sprite<?>> sprites = new ArrayList<>();
    private GameContainer gc;
    private Warship warship;
    private Scoreboard scoreboard;
    private UnicodeFont scoreFont, splashFont;
    private boolean readSplash = false;

    public Javy() { super("Javy"); }

    @Override
    public void init(GameContainer gc) throws SlickException {
        Assets.load();
        this.gc = gc;
        warship = new Warship(gc);
        sprites.add(warship);
        scoreboard = new Scoreboard();
        scoreFont = Font.get("Arial", 32, java.awt.Color.BLACK);
        splashFont = Font.get("Arial", 64, java.awt.Color.BLACK);
    }

    @Override
    public void update(GameContainer gc, int deltaTicks) {
        if (!readSplash) return;
        for (Sprite<?> sprite : sprites) {
            if (!sprite.isPersisting()) sprite.countDown();
            sprite.update(deltaTicks);
        }
        sprites.removeIf(sprite -> !sprite.isPersisting() && sprite.getRemoveCountdown() == 0);
        Input input = gc.getInput();
        if (KeyPress.isLeftDown(input)) warship.goLeft(deltaTicks, KeyPress.isCtrlDown(input) ? ctrlModifier : 1.0f);
        if (KeyPress.isRightDown(input)) warship.goRight(deltaTicks, KeyPress.isCtrlDown(input) ? ctrlModifier : 1.0f);
        List<Submarine> submarines = new ArrayList<>();
        List<Bomb> bombs = new ArrayList<>();
        for (Sprite<?> sprite : sprites) if (sprite.isPersisting()) {
            if (sprite instanceof Submarine) submarines.add((Submarine) sprite);
            else if (sprite instanceof Bomb) bombs.add((Bomb) sprite);
        }
        for (Submarine submarine : submarines) for (Bomb bomb : bombs) if(submarine.getHitbox().intersects(bomb.getHitbox())) {
            bomb.explode();
            submarine.hitByBomb();
            scoreboard.add(1);
        }
        if (Random.decide(0.001f)) {
            boolean spawnFromRight = Random.decide(0.5f);
            float speed = Random.floatInRange(0.2f, 0.9f), yRatio = Random.floatInRange(seaLevelRatio, 1.0f), y = size * yRatio;
            if (y < size * seaLevelRatio - Submarine.height + size * 0.1f) y = size * seaLevelRatio - Submarine.height + size * 0.1f;
            if (y > size - Submarine.height / 2.0f) y = size - Submarine.height / 2.0f;
            Submarine submarine = new Submarine(gc, spawnFromRight ? size - Submarine.width / 2.0f : Submarine.width / 2.0f, y, speed, !spawnFromRight);
            sprites.add(submarine);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        if (!readSplash) {
            g.setColor(Color.white);
            g.fillRect(0, 0, size, size);
            g.setFont(splashFont);
            Util.drawCenteredMultilineText(g, splash, size / 2.0f, size / 2.0f);
            return;
        }
        Assets.get(TextureId.BACKGROUND).draw(0, 0, size, size);
        for (Sprite<?> sprite : sprites) sprite.render();
        String score = "Score: " + scoreboard.get();
        int textWidth = scoreFont.getWidth(score);
        g.setFont(scoreFont);
        g.drawString(score, size - textWidth - 20, 20);
    }

    @Override
    public void keyPressed(int key, char c) {
        if (!readSplash) readSplash = true;
        Input input = gc.getInput();
        if (key == Input.KEY_SPACE) {
            float horizontalSpeed = 0.0f;
            if (KeyPress.isLeftDown(input)) horizontalSpeed = -Warship.moveSpeed * (KeyPress.isCtrlDown(input) ? ctrlModifier : 1.0f);
            else if (KeyPress.isRightDown(input)) horizontalSpeed = Warship.moveSpeed * (KeyPress.isCtrlDown(input) ? ctrlModifier : 1.0f);
            Bomb bomb = new Bomb(gc, warship.getX(), horizontalSpeed, Bomb.downwardSpeed * (KeyPress.isCtrlDown(input) ? ctrlModifier : 1.0f));
            sprites.add(bomb);
        }
    }
}