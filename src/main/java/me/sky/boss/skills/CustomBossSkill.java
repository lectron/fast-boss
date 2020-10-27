package me.sky.boss.skills;

import org.bukkit.entity.LivingEntity;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossSkill;
import org.mineacademy.boss.api.SpawnedBoss;

public abstract class CustomBossSkill extends BossSkill {
    public abstract void executeSkill(Boss boss, LivingEntity livingEntity);
}
