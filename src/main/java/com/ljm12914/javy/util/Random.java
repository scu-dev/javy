package com.ljm12914.javy.util;

public class Random {
    private final static java.util.Random rand = new java.util.Random();

    public static boolean decide(float probability) { return rand.nextFloat() < probability; }
    public static float floatInRange(float minimum, float maximum) { return rand.nextFloat() * (maximum - minimum) + minimum; }
}