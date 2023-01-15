package ru.spigotmc.destroy.newbiechat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.spigotmc.destroy.newbiechat.NewbieChat;
import ru.spigotmc.destroy.newbiechat.utils.Data;
import ru.spigotmc.destroy.newbiechat.utils.Utils;

public class JoinListener implements Listener {

    NewbieChat main;
    public JoinListener(NewbieChat main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if(!Data.isBlocked(player)) {
            Data.setBlock(player, main.getConfig().getInt("cooldown"));
            for(String s : main.getConfig().getStringList("first-join-actions")) {
                Utils.startWithCheck(player, s);
            }
        }
    }

}
