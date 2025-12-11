package com.ljm12914.javy.graphics;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Util {
    public static void drawCenteredMultilineText(Graphics g, String text, float x, float y) {
        Font font = g.getFont();
        String[] lines = text.split("\n");
        int lineHeight = font.getLineHeight();
        float startY = y - lineHeight * lines.length / 2.0f;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineWidth = font.getWidth(line);
            float startX = x - lineWidth / 2.0f;
            float thisY = startY + i * lineHeight;
            g.drawString(line, startX, thisY);
        }
    }
}
