package com.example.parkourchallenge.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utils {
    public static void changeBlockType(Player player, Material newMaterial) {
        Block block = player.getLocation().getBlock();
        block.setType(newMaterial);
    }
}

