package me.sky.boss.skills.utils;

public class ChanceObject<T> {
    private final T object;
    private final double chance;

    public ChanceObject(T object, double chance) {
        this.object = object;
        this.chance = chance;
    }

    public T getObject() {
        return object;
    }

    public double getChance() {
        return chance;
    }
}
