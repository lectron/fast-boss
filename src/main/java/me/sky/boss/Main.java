package me.sky.boss;

import me.sky.boss.skills.SkillListener;
import me.sky.boss.skills.SkillManager;
import me.sky.boss.utils.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements IBossPlugin {
    private SkillManager skillManager;
    private PluginConfig messages;

    @Override
    public void onEnable() {
        messages = new PluginConfig("messages.yml", this);
        skillManager = new SkillManager(this);
        new SkillListener(this);
    }

    public PluginConfig getMessages() {
        return messages;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
}
