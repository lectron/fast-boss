package me.sky.boss.utils.config;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PluginConfig extends Config {

    private final Plugin plugin;

    public PluginConfig(String fileName, Plugin plugin) {
        super("plugins/" + plugin.getName(), fileName, plugin);
        this.plugin = plugin;
        create();
        if (exists()) {
            verifyConfig();
        } else {
            setDefault(fileName);
            getConfig().options().copyDefaults(true);
        }
        saveConfig();
        reloadConfig();
    }

    private void verifyConfig() {
        Config c = new Config("plugins/" + plugin.getName(), "temp.yml", plugin);
        c.create();
        c.setDefault(toFile().getName());
        c.getConfig().options().copyDefaults(true);
        for (String key : c.getConfig().getKeys(false)) {
            if (!getConfig().contains(key)) {
                getConfig().set(key, c.getConfig().get(key));
            }
        }
    }

    public String getMessage(String s) {
        if (!getConfig().contains(s)) {
            getConfig().set(s, "&4Example Message");
            saveConfig();
            reloadConfig();
            return "§cMessage does not exist!";
        }
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(s));
    }

    public List<String> getMessageList(String s) {
        if (!getConfig().contains(s)) {
            getConfig().set(s, Collections.singletonList("&4Example Message"));
            saveConfig();
            reloadConfig();
            return Arrays.asList("§cMessage does not exist!");
        }
        List<String> lore = getConfig().getStringList(s);
        lore.replaceAll(s1 -> ChatColor.translateAlternateColorCodes('&', s1));
        return lore;
    }
}
