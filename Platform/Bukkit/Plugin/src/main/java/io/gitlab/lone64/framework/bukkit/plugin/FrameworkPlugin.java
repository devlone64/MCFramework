package io.gitlab.lone64.framework.bukkit.plugin;

import io.gitlab.lone64.framework.bukkit.plugin.listener.CommandListener;
import io.gitlab.lone64.framework.bukkit.plugin.listener.InventoryListener;
import io.gitlab.lone64.framework.bukkit.plugin.listener.PlayerListener;
import io.gitlab.lone64.framework.bukkit.plugin.listener.TextFieldListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FrameworkPlugin extends JavaPlugin {

    @Getter private static FrameworkPlugin instance;

    @Override
    public void onLoad() {
        getLogger().info("lone64's framework is loading...");
        instance = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("lone64's framework is enabling...");

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new TextFieldListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("lone64's framework is disabling...");
    }

}