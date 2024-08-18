package com.example.parkourchallenge.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CreateCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public CreateCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("คำสั่งนี้สามารถใช้ได้เฉพาะผู้เล่นเท่านั้น!");
            return false;
        }

        Player player = (Player) sender;
        Location startLocation = player.getLocation();
        
        // ตั้งค่าบล็อกที่จุดเริ่มต้น
        startLocation.getBlock().setType(Material.IRON_BLOCK);
        
        // สร้างบล็อกที่เป็นเส้นทาง parkour
        createParkourCourse(player, startLocation);
        
        player.sendMessage("สร้าง parkour course เรียบร้อยแล้ว!");
        return true;
    }

    private void createParkourCourse(Player player, Location startLocation) {
        int height = 50; // ความสูงของ parkour
        int stepGap = 1; // ระยะห่างระหว่างบล็อก

        Location endLocation = null;

        for (int i = 0; i < height; i++) {
            Location blockLocation = startLocation.clone().add(0, i * stepGap, 0);
            blockLocation.getBlock().setType(Material.COBBLESTONE);
            
            if (i % 2 == 0) {
                // สร้างบล็อกด้านข้าง
                Location sideBlockLocation = blockLocation.clone().add(1, 0, 0);
                sideBlockLocation.getBlock().setType(Material.COBBLESTONE);
            }
            
            endLocation = blockLocation;  // เก็บตำแหน่งบล็อกสุดท้าย (จุดสิ้นสุด)
        }

        // ตรวจจับเมื่อผู้เล่นถึงจุดสิ้นสุด
        Location finalEndLocation = endLocation;
        Bukkit.getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getLocation().distance(finalEndLocation) < 1) {
                    player.sendMessage("คุณได้ถึงจุดสิ้นสุดแล้ว! จะส่งกลับไปยังจุดเริ่มต้นใน 15 วินาที");

                    // รอ 15 วินาทีก่อนที่จะส่งผู้เล่นกลับไปยังจุดเริ่มต้น
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(startLocation);
                            player.sendMessage("คุณถูกส่งกลับไปยังจุดเริ่มต้นแล้ว!");
                        }
                    }.runTaskLater(plugin, 15 * 20L); // 15 วินาที = 15 * 20 ticks
                    
                    // หยุดตรวจจับเพิ่มเติม
                    cancel();
                }
            }
        }, 0L, 10L); // ตรวจสอบทุกๆ 10 ticks (0.5 วินาที)
    }
}

