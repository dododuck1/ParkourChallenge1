package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class DeleteCommand {
    private final ParkourChallenge plugin;
    private final Player player;

    public DeleteCommand(ParkourChallenge plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void execute() {
        World world = player.getWorld();
        Location startLocation = player.getLocation();

        int height = 50;
        for (int i = 0; i < height; i++) {
            Location blockLocation = startLocation.clone().add(0, i, 0);
            world.getBlockAt(blockLocation).setType(Material.AIR);
        }

        player.sendMessage("Parkour structure deleted.");
    }
}

