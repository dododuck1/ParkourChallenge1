package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;

public class RideCommand {
    private final ParkourChallenge plugin;
    private final Player player;
    private final String entityType;
    private final int height;

    public RideCommand(ParkourChallenge plugin, Player player, String[] args) {
        this.plugin = plugin;
        this.player = player;
        this.entityType = args[0].toLowerCase();
        this.height = Integer.parseInt(args[2]);
    }

    public void execute() {
        Entity rideEntity;

        switch (entityType) {
            case "wolf":
                rideEntity = player.getWorld().spawn(player.getLocation(), Wolf.class);
                break;
            case "chicken":
                rideEntity = player.getWorld().spawn(player.getLocation(), Chicken.class);
                break;
            case "allay":
                rideEntity = player.getWorld().spawn(player.getLocation(), Allay.class);
                break;
            default:
                player.sendMessage("Invalid entity type.");
                return;
        }

        rideEntity.addPassenger(player);
        rideEntity.setVelocity(new Vector(0, height / 10.0, 0));

        String action = entityType.equalsIgnoreCase("wolf") ? "up" : "down";
        player.sendMessage("You are now riding " + entityType + " " + action + "!");
    }
}

