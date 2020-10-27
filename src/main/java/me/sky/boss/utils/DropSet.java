package me.sky.boss.utils;

import me.sky.boss.skills.utils.ChanceObject;
import org.bukkit.Material;

public class DropSet {
    private ChanceObject<Material> mainHand, offHand;
    private ChanceObject<Material>[] armorContents;

    public DropSet(ChanceObject<Material> mainHand, ChanceObject<Material> offHand, ChanceObject<Material>[] armorContents) {
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.armorContents = armorContents;
    }

    public ChanceObject<Material> getMainHand() {
        return mainHand;
    }

    public ChanceObject<Material> getOffHand() {
        return offHand;
    }

    public ChanceObject<Material>[] getArmorContents() {
        return armorContents;
    }
}
