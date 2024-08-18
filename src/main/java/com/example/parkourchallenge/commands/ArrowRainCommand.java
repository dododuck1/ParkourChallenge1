package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class ArrowRainCommand {
    private final ParkourChallenge plugin;
    private final Player player;

    public ArrowRainCommand(ParkourChallenge plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void execute() {
        World world = player.getWorld();
        Location location = player.getLocation();

        for (int i = 0; i < 10; i++) {
            Location arrowLocation = location.clone().add(Math.random() * 5, 10, Math.random() * 5);
            world.spawn(arrowLocation, Arrow.class);
        }

        player.sendMessage("Arrow rain summoned!");
    }
}

