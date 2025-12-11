package com.ljm12914.javy.graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.EnumMap;

public final class Assets {
    private static final EnumMap<TextureId, Image> textures = new EnumMap<>(TextureId.class);

    private Assets() {}

    public static void load() throws SlickException {
        textures.put(TextureId.BACKGROUND, new Image("background.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.BOMB, new Image("boom.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.EXPLOSION_1, new Image("b.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.EXPLOSION_2, new Image("b1.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.EXPLOSION_3, new Image("b2.png", false,  Image.FILTER_NEAREST));
        textures.put(TextureId.SUBMARINE_LEFT, new Image("h2.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.SUBMARINE_RIGHT, new Image("q1.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.SUBMARINE_LEFT_EXPLODED, new Image("q2.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.SUBMARINE_RIGHT_EXPLODED, new Image("r1.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.WARSHIP_LEFT, new Image("ship1.png", false, Image.FILTER_NEAREST));
        textures.put(TextureId.WARSHIP_RIGHT, new Image("ship0.png", false, Image.FILTER_NEAREST));
    }

    public static Image get(TextureId id) {
        Image image = textures.get(id);
        if (image == null) throw new IllegalArgumentException("Texture " + id + " not found");
        return image;
    }
}