package ru.spigotmc.destroy.newbiechat.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.spigotmc.destroy.newbiechat.NewbieChat;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Data {

    private static File file;
    private static FileConfiguration config;

    public static void loadConfig(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "data.yml");
        if(!file.exists()) {
            plugin.saveResource("data.yml", true);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void setBlock(Player p, int time) {
        config.set(p.getUniqueId()+".time", time);
        config.set(p.getUniqueId()+".blocked", true);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBlocked(Player p) {
        return config.getBoolean(p.getUniqueId()+".blocked");
    }

    public static boolean firstJoin(Player p) {
        return players().contains(p.getUniqueId().toString());
    }

    public static int getTime(Player p) {
        return config.getInt(p.getUniqueId()+".time");
    }

    private static Set<String> players() {
        return config.getKeys(false);
    }

    public static void startChecks(NewbieChat main) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(players().contains(p.getUniqueId().toString())) {
                    int time = config.getInt(p.getUniqueId()+".time");
                    if(time > 0) {
                        config.set(p.getUniqueId()+".time", time-1);
                    } else {
                        config.set(p.getUniqueId()+".blocked", false);
                    }
                }
            }
        },0,20);
    }


}
