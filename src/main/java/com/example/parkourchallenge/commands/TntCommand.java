package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class TntCommand {
    private final ParkourChallenge plugin;
    private final Player player;
    private final int power;

    public TntCommand(ParkourChallenge plugin, Player player, String[] args) {
        this.plugin = plugin;
        this.player = player;
        this.power = Integer.parseInt(args[1]);
    }

    public void execute() {
        World world = player.getWorld();
        Location location = player.getLocation();

        TNTPrimed tnt = world.spawn(location, TNTPrimed.class);
        tnt.setYield(power);

        player.sendMessage("TNT summoned with power " + power + ".");
    }
}

