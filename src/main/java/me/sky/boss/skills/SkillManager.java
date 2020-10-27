package me.sky.boss.skills;

import me.sky.boss.IBossPlugin;
import me.sky.boss.skills.types.*;
import org.bukkit.Bukkit;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossAPI;
import org.mineacademy.boss.api.BossSkillRegistry;

import java.util.HashMap;
import java.util.Map;

public class SkillManager {
    private final IBossPlugin plugin;
    private final Map<SkillTypes, CustomBossSkill> skillSet = new HashMap<>();

    public SkillManager(IBossPlugin plugin) {
        this.plugin = plugin;
        skillSet.put(SkillTypes.CUSTOM_SKELETON, new AdvancedSkeleton(plugin));
        skillSet.put(SkillTypes.CUSTOM_ZOMBIE, new CustomZombieBoss());
        skillSet.put(SkillTypes.CUSTOM_CREEPER, new CustomCreeperBoss());
        skillSet.put(SkillTypes.CUSTOM_ENDERMAN, new CustomEndermanBoss());
        skillSet.put(SkillTypes.CUSTOM_PIG, new CustomPigBoss());
        skillSet.put(SkillTypes.CUSTOM_PIGMEN, new CustomPigmenBoss());
        skillSet.put(SkillTypes.CUSTOM_SPIDER_JOCKEY, new CustomSpiderJockeyBoss());
        registerSkills();

        skillSet.keySet().forEach(skillType -> {
            Boss boss = BossAPI.getBoss(skillType.name().toLowerCase());
            if (boss == null) {
                boss = BossAPI.createBoss(skillType.getEntityType(), skillType.name().toLowerCase());
                boss.getSkills().addAndUpdate(skillSet.get(skillType));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss reload");
            }
        });
    }

    private void registerSkills() {
        skillSet.values().forEach(BossSkillRegistry::register);
    }

    public Map<SkillTypes, CustomBossSkill> getSkillSet() {
        return skillSet;
    }
}
