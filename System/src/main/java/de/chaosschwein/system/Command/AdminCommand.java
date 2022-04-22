package de.chaosschwein.system.Command;

import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Manager.PermissionManager;
import de.chaosschwein.system.Utils.InventoryCreator;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Log log = new Log(player);
            if (command.getName().equalsIgnoreCase("admin")) {
                if (new PermissionManager(player).hasPermission(Permission.ADMIN)) {
                    new InventoryCreator(player).adminpanel();
                } else {
                    log.noPermission();
                }
            } else if (command.getName().equalsIgnoreCase("gm") || command.getName().equalsIgnoreCase("gamemode")) {
                if (new PermissionManager(player).hasPermission(Permission.GAMEMODE)) {
                    if (args.length == 1) {
                        setGamemode(player, player,args[0]);
                    }else if(args.length == 2){
                        try {
                            Player target = player.getServer().getPlayer(args[1]);
                            if(target != null){
                                setGamemode(player, target, args[0]);
                            }else{
                                log.send("§cDieser Spieler ist nicht online.");
                            }
                        }catch (Exception e){
                            log.send("§cDieser Spieler ist nicht online.");
                        }
                    }

                } else {
                    log.noPermission();
                }
            }
        }
        return true;
    }

    private static void setGamemode(Player player,Player target, String msg){
        Log log = new Log(player);
        switch (msg.toLowerCase()) {
            case "survival":
            case "0":
                target.setGameMode(GameMode.SURVIVAL);
                log.send("§aDu hast den Spielmodus auf §6SURVIVAL §agesetzt.");
                break;
            case "creative":
            case "1":
                target.setGameMode(GameMode.CREATIVE);
                log.send("§aDu hast den Spielmodus auf §6CREATIVE §agesetzt.");
                break;
            case "adventure":
            case "2":
                target.setGameMode(GameMode.ADVENTURE);
                log.send("§aDu hast den Spielmodus auf §6ADVENTURE §agesetzt.");
                break;
            case "spectator":
            case "3":
                target.setGameMode(GameMode.SPECTATOR);
                log.send("§aDu hast den Spielmodus auf §6SPECTATOR §agesetzt.");
                break;
            default:
                log.send("§c/gm <0/1/2/3>");
                break;
        }
    }
}
