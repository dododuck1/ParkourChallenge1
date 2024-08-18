package com.example.parkourchallenge;

import com.example.parkourchallenge.ParkourChallenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommandExecutor implements CommandExecutor {
    private final ParkourChallenge plugin;

    public ParkourCommandExecutor(ParkourChallenge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "create":
                        new CreateCommand(plugin, player).execute();
                        break;
                    case "delete":
                        new DeleteCommand(plugin, player).execute();
                        break;
                    case "tnt":
                        new TntCommand(plugin, player, args).execute();
                        break;
                    case "change_block":
                        new ChangeBlockCommand(plugin, player, args).execute();
                        break;
                    case "edit":
                        new EditCommand(plugin, player).execute();
                        break;
                    case "speed":
                        new SpeedCommand(plugin, player, args).execute();
                        break;
                    case "arrow_rain":
                        new ArrowRainCommand(plugin, player).execute();
                        break;
                    case "wolf":
                    case "chicken":
                        new RideCommand(plugin, player, args).execute();
                        break;
                    default:
                        player.sendMessage("Invalid command.");
                        break;
                }
            } else {
                player.sendMessage("Usage: /parkour <command>");
            }
        }
        return true;
    }
}

