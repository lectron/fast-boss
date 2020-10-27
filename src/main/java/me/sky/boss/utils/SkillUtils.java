package me.sky.boss.utils;

import me.sky.boss.skills.utils.ChanceObject;

public class SkillUtils {
    public static <T> ChanceObject<T> readChanceObject(T key, String chance) {
        return new ChanceObject<T>(key, Double.parseDouble(chance.replace("%", "")));
    }
}
