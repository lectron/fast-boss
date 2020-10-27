package me.sky.boss.skills;

import org.bukkit.entity.EntityType;

public enum SkillTypes {
    CUSTOM_SKELETON(EntityType.SKELETON),
    CUSTOM_ZOMBIE(EntityType.ZOMBIE),
    CUSTOM_CREEPER(EntityType.CREEPER),
    CUSTOM_ENDERMAN(EntityType.ENDERMAN),
    CUSTOM_PIG(EntityType.PIG),
    CUSTOM_PIGMEN(EntityType.ZOMBIFIED_PIGLIN),
    CUSTOM_WITHER_SKELETON(EntityType.WITHER_SKELETON),
    CUSTOM_SPIDER_JOCKEY(EntityType.SPIDER),
    ;

    private final EntityType entityType;

    SkillTypes(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static SkillTypes getSkillType(EntityType entityType) {
        for (SkillTypes skillTypes : values()) {
            if (skillTypes.getEntityType() == entityType) {
                return skillTypes;
            }
        }
        return null;
    }
}
