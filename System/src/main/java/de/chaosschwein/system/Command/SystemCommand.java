package de.chaosschwein.system.Command;

import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Manager.PermissionManager;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SystemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (new PermissionManager(sender).hasPermission(Permission.ADMIN)) {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        new Log(sender).send("System is reloading...");
                        Systemmain.reload();
                        break;
                    case "stop":
                        new Log(sender).send("Stopping System...");
                        Systemmain.stop();
                        break;
                    case "start":
                        new Log(sender).send("&cSystem is running!");
                        Bukkit.getPluginManager().enablePlugin(Systemmain.getInstance());
                        Systemmain.start();
                        break;
                    case "help":
                        Log log = new Log(sender);
                        log.send("&7&m-----------------------------------------------------");
                        log.send("&7/system | System Commands");
                        log.send("&7/admin | Admin GUI");
                        log.send("&7/invsee <Player> | Opens the Inventory of a Player");
                        log.send("&7/ec | Opens the Enderchest");
                        log.send("&7&m-----------------------------------------------------");
                        break;
                    default:
                        new Log(sender).send("/system reload | Reloads the System");
                        new Log(sender).send("/system stop | Stops the System");
                        new Log(sender).send("/system start | Starts the System");
                        new Log(sender).send("/system help | Shows this Help");
                        break;
                }
            } else {
                new Log(sender).send("/system reload | Reloads the System");
                new Log(sender).send("/system stop | Stops the System");
                new Log(sender).send("/system start | Starts the System");
                new Log(sender).send("/system help | Shows this Help");
            }
        } else {
            new Log(sender).noPermission();
        }
        return true;
    }
}
