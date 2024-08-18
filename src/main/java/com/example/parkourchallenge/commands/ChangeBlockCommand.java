package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChangeBlockCommand {
    private final ParkourChallenge plugin;
    private final Player player;
    private final Material newMaterial;

    public ChangeBlockCommand(ParkourChallenge plugin, Player player, String[] args) {
        this.plugin = plugin;
        this.player = player;

        switch (args[1]) {
            case "1":
                newMaterial = Material.STONE;
                break;
            case "2":
                newMaterial = Material.DIRT;
                break;
            case "3":
                newMaterial = Material.COBBLESTONE;
                break;
            default:
                newMaterial = Material.STONE;
                break;
        }
    }

    public void execute() {
        // Logic to change the blocks in the course to the new material
        // You may want to implement some logic to locate the existing course and change its blocks

        player.sendMessage("Blocks changed to " + newMaterial.toString().toLowerCase() + ".");
    }
}

