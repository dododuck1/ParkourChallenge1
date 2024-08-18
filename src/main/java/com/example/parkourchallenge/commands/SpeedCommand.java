package com.example.parkourchallenge.commands;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.example.parkourchallenge.ParkourChallenge;

public class SpeedCommand {
    private ParkourChallenge plugin;
    private Player player;
    private boolean speedUp;
    private int duration;

    public SpeedCommand(ParkourChallenge plugin, Player player, String[] args) {
        this.plugin = plugin;
        this.player = player;
        this.speedUp = args[1].equalsIgnoreCase("speedUp");
        this.duration = Integer.parseInt(args[2]);
    }

    public void execute() {
        PotionEffectType effectType = speedUp ? PotionEffectType.SPEED : PotionEffectType.SLOW;
        player.addPotionEffect(new PotionEffect(effectType, duration * 20, 1));
        String action = speedUp ? "sped up" : "slowed down";
        player.sendMessage("You have been " + action + " for " + duration + " seconds.");
    }
}
