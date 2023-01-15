package ru.spigotmc.destroy.newbiechat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.spigotmc.destroy.newbiechat.NewbieChat;
import ru.spigotmc.destroy.newbiechat.utils.Data;
import ru.spigotmc.destroy.newbiechat.utils.Utils;

public class CommandListener implements org.bukkit.event.Listener {

    NewbieChat main;
    public CommandListener(NewbieChat main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if(main.getConfig().getBoolean("block-commands.enable") && e.getMessage().contains(":")) {
            if (player.hasPermission("newbiechat.doubled")) {
                return;
            }
            for(String sz : main.getConfig().getStringList("block-commands.actions")) {
                Utils.startWithCheck(player, sz);
            }
        }
        if (player.hasPermission("newbiechat.bypass")) {
            return;
        }
        for(String s : main.getConfig().getStringList("lock-commands")) {
            if (e.getMessage().equalsIgnoreCase("/"+s+" ")) {
                if(!Data.isBlocked(player)) {
                    return;
                }
                e.setCancelled(true);
                int time = Data.getTime(player);
                if(time > 0) {
                    e.setCancelled(true);
                    String cd = Utils.formattedTime(time);
                    for(String sz : main.getConfig().getStringList("chat-lock-actions")) {
                        Utils.startWithCheck(player,sz,cd);
                    }
                }
            }
        }
    }
}
