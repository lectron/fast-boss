package me.sky.boss.skills.types;

import me.sky.boss.skills.CustomBossSkill;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossSkillDelay;
import org.mineacademy.boss.api.SpawnedBoss;

public class CustomPigBoss extends CustomBossSkill {
    @Override
    public String getName() {
        return "Custom Pig Boss";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.PIG_SPAWN_EGG);
    }

    @Override
    public BossSkillDelay getDefaultDelay() {
        return new BossSkillDelay(Integer.MAX_VALUE);
    }

    @Override
    public String[] getDefaultMessage() {
        return new String[0];
    }

    @Override
    public boolean execute(SpawnedBoss spawnedBoss) {
        return false;
    }

    @Override
    public void executeSkill(Boss boss, LivingEntity livingEntity) {

    }
}
