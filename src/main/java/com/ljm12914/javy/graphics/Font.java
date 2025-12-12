package com.ljm12914.javy.graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.*;

public class Font {
    @SuppressWarnings("unchecked")
    public static UnicodeFont get(String name, int size, Color color) throws SlickException {
        java.awt.Font font = new java.awt.Font(name, java.awt.Font.BOLD, size);
        UnicodeFont unicodeFont = new UnicodeFont(font);
        unicodeFont.getEffects().add(new ColorEffect(color));
        unicodeFont.addGlyphs('0', '9');
        unicodeFont.addAsciiGlyphs();
        unicodeFont.loadGlyphs();
        return unicodeFont;
    }
}