package com.example.parkourchallenge.commands;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.entity.Player;

public class EditCommand {
    private final ParkourChallenge plugin;
    private final Player player;

    public EditCommand(ParkourChallenge plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void execute() {
        // Toggle block protection logic

        player.sendMessage("Block protection toggled.");
    }
}

