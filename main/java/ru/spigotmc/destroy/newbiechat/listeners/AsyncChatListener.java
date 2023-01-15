package ru.spigotmc.destroy.newbiechat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.spigotmc.destroy.newbiechat.NewbieChat;
import ru.spigotmc.destroy.newbiechat.utils.Data;
import ru.spigotmc.destroy.newbiechat.utils.Utils;

public class AsyncChatListener implements Listener {

    NewbieChat main;
    public AsyncChatListener(NewbieChat main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if(!Data.isBlocked(player)) {
            return;
        }
        if (player.hasPermission("newbiechat.bypass")) {
            return;
        }
        int time = Data.getTime(player);
        if(time > 0) {
            e.setCancelled(true);
            String cd = Utils.formattedTime(time);
            for(String s : main.getConfig().getStringList("chat-lock-actions")) {
                Utils.startWithCheck(player,s,cd);
            }
        }
    }

}
