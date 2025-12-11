package com.ljm12914.javy.game;
import org.newdawn.slick.Input;

public class KeyPress {
    public static boolean isCtrlDown(Input input) {
        return input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL);
    }

    public static boolean isLeftDown(Input input) {
        return input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT);
    }

    public static boolean isRightDown(Input input) {
        return input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT);
    }
}
