package ru.spigotmc.destroy.newbiechat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.spigotmc.destroy.newbiechat.commands.ReloadCommand;
import ru.spigotmc.destroy.newbiechat.listeners.AsyncChatListener;
import ru.spigotmc.destroy.newbiechat.listeners.CommandListener;
import ru.spigotmc.destroy.newbiechat.listeners.JoinListener;
import ru.spigotmc.destroy.newbiechat.utils.Data;

public final class NewbieChat extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new AsyncChatListener(this);
        new ReloadCommand(this);
        new CommandListener(this);
        new JoinListener(this);
        msg("&e============================");
        msg("&aВерсия &f" + getDescription().getVersion());
        msg("&aРазработчик:&b https://vk.com/emptycsgo");
        msg("&e============================");
        Data.loadConfig(this);
        Data.startChecks(this);
    }

    @Override
    public void onDisable() {

    }

    private void msg(String msg) {
        String p = ChatColor.translateAlternateColorCodes('&', "&e"+getName()+" | ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', p+msg));
    }}
