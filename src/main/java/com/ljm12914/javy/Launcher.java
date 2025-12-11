package com.ljm12914.javy;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import java.awt.*;

public final class Launcher {
    private Launcher() {}

    public static void main(String[] args) {
        try {
            Javy game = new Javy();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            game.size = Math.min(screenSize.width, screenSize.height);
            AppGameContainer app = new AppGameContainer(game);
            app.setDisplayMode(game.size, game.size, false);
            app.setIcon("b.png");
            app.setShowFPS(true);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}