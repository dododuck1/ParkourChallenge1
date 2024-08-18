package com.example.parkourchallenge;

import org.bukkit.plugin.java.JavaPlugin;

public class ParkourChallenge extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register commands
        getCommand("parkour").setExecutor(new ParkourCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

