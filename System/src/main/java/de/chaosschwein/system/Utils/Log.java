package de.chaosschwein.system.Utils;

import de.chaosschwein.system.Main.Systemmain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Log {

    private CommandSender player = null;
    public Log(Player player) {
        this.player = player;
    }

    public Log(CommandSender player) {
        this.player = player;
    }

    public Log(){}

    public void send(String message) {
        if(player != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Systemmain.prefix+message));
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',Systemmain.prefix+message));
        }
    }

    public void noPermission() {
        send("&cYou don't have the permission to do this!");
    }

    public void playerNotFound() {
        send("&cPlayer not found!");
    }

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',Systemmain.prefix+message));
    }
}
