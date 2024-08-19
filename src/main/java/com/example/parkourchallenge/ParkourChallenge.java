package com.example.parkourchallenge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkourChallenge extends JavaPlugin implements Listener, CommandExecutor {

    private Map<String, List<Location>> parkourCourses = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("parkour").setExecutor(this);
        getLogger().info("ParkourChallenge plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ParkourChallenge plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                return createParkour(player, args);
            case "delete":
                return deleteParkour(player, args);
            case "tp":
                return teleportToStart(player, args);
            case "tnt":
                return summonTNT(player, args);
            case "change_block":
                return changeBlockStyle(player, args);
            case "edit":
                return toggleEditMode(player);
            case "speed":
                return changePlayerSpeed(player, args);
            case "arrow_rain":
                return startArrowRain(player);
            case "wolf":
            case "chicken":
            case "allay":
                return rideEntity(player, args);
            default:
                player.sendMessage("Unknown command. Type /parkour for help.");
                return true;
        }
    }

    private boolean createParkour(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Usage: /parkour create <name>");
            return true;
        }
        String courseName = args[1];
        List<Location> course = new ArrayList<>();
        course.add(player.getLocation());
        parkourCourses.put(courseName, course);
        player.sendMessage("Parkour course '" + courseName + "' created!");
        return true;
    }

    private boolean deleteParkour(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Usage: /parkour delete <name>");
            return true;
        }
        String courseName = args[1];
        if (parkourCourses.remove(courseName) != null) {
            player.sendMessage("Parkour course '" + courseName + "' deleted!");
        } else {
            player.sendMessage("Parkour course '" + courseName + "' not found.");
        }
        return true;
    }

    private boolean teleportToStart(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Usage: /parkour tp <name>");
            return true;
        }
        String courseName = args[1];
        List<Location> course = parkourCourses.get(courseName);
        if (course != null && !course.isEmpty()) {
            player.teleport(course.get(0));
            player.sendMessage("Teleported to the start of '" + courseName + "'!");
        } else {
            player.sendMessage("Parkour course '" + courseName + "' not found.");
        }
        return true;
    }

    private boolean summonTNT(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Usage: /parkour tnt <strength>");
            return true;
        }
        int strength = Integer.parseInt(args[1]);
        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
        tnt.setYield(strength);
        player.sendMessage("TNT summoned with strength " + strength);
        return true;
    }

    private boolean changeBlockStyle(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Usage: /parkour change_block <style>");
            return true;
        }
        int style = Integer.parseInt(args[1]);
        Material newMaterial;
        switch (style) {
            case 1:
                newMaterial = Material.STONE;
                break;
            case 2:
                newMaterial = Material.WOOD;
                break;
            case 3:
                newMaterial = Material.GLASS;
                break;
            default:
                newMaterial = Material.STONE;
        }
        player.getLocation().getBlock().setType(newMaterial);
        player.sendMessage("Block style changed to " + newMaterial.name());
        return true;
    }

    private boolean toggleEditMode(Player player) {
        // Implement edit mode logic here
        player.sendMessage("Edit mode toggled");
        return true;
    }

    private boolean changePlayerSpeed(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage("Usage: /parkour speed <speedUp/slowDown> <duration>");
            return true;
        }
        boolean speedUp = args[1].equalsIgnoreCase("speedUp");
        int duration = Integer.parseInt(args[2]);
        float speed = speedUp ? 0.4f : 0.1f;
        player.setWalkSpeed(speed);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setWalkSpeed(0.2f);
            }
        }.runTaskLater(this, duration * 20L);
        player.sendMessage("Speed " + (speedUp ? "increased" : "decreased") + " for " + duration + " seconds");
        return true;
    }

    private boolean startArrowRain(Player player) {
        Location center = player.getLocation();
        int radius = 10;
        int duration = 10;
        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                if (ticks >= duration * 20) {
                    this.cancel();
                    return;
                }
                Location arrowSpawn = center.clone().add(Math.random() * radius * 2 - radius, 20, Math.random() * radius * 2 - radius);
                arrowSpawn.getWorld().spawnArrow(arrowSpawn, new Vector(0, -1, 0), 0.6f, 12);
                ticks++;
            }
        }.runTaskTimer(this, 0L, 1L);
        player.sendMessage("Arrow rain started!");
        return true;
    }

    private boolean rideEntity(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage("Usage: /parkour <entity> <Up/Down> <blocks>");
            return true;
        }
        EntityType entityType = EntityType.valueOf(args[0].toUpperCase());
        boolean up = args[1].equalsIgnoreCase("Up");
        int blocks = Integer.parseInt(args[2]);
        
        Entity mount = player.getWorld().spawnEntity(player.getLocation(), entityType);
        mount.addPassenger(player);
        
        new BukkitRunnable() {
            int blocksMoved = 0;
            @Override
            public void run() {
                if (blocksMoved >= blocks || !mount.isValid() || mount.getPassengers().isEmpty()) {
                    mount.remove();
                    this.cancel();
                    return;
                }
                Location newLoc = mount.getLocation().add(0, up ? 1 : -1, 0);
                mount.teleport(newLoc);
                blocksMoved++;
            }
        }.runTaskTimer(this, 0L, 5L);
        player.sendMessage("You're now riding a " + entityType.name());
        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Implement parkour logic here
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            // Implement fall handling logic here
        }
    }
}
