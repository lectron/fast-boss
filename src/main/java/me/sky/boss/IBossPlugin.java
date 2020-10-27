package me.sky.boss;

import me.sky.boss.skills.SkillManager;
import me.sky.boss.utils.config.PluginConfig;
import org.bukkit.plugin.Plugin;

public interface IBossPlugin extends Plugin {
    PluginConfig getMessages();
    SkillManager getSkillManager();
}
