package me.sky.boss.skills.types;

import me.sky.boss.skills.CustomBossSkill;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossSkillDelay;
import org.mineacademy.boss.api.SpawnedBoss;

public class CustomPigmenBoss extends CustomBossSkill {
    @Override
    public String getName() {
        return "Custom Pigmen Boss";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG);
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
