package de.chaosschwein.system.Command;

import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.PermissionManager;
import de.chaosschwein.system.Utils.InventoryCreator;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PermissionManager pm = new PermissionManager(player);
            if(command.getName().equalsIgnoreCase("invsee")) {
                if(!Systemmain.invseesystem ){
                    new Log(sender).send("§cInvseesystem ist deaktiviert!");
                    return true;
                }
                if (pm.hasPermission(Permission.INVSEE)) {
                    if (args.length == 1) {
                        new InventoryCreator(player).invsee(args[0]);
                    } else {
                        new Log(player).send("§cBitte benutze: /invsee <Spieler>");
                    }
                } else {
                    new Log(player).noPermission();
                }
                return true;
            }

            if(command.getName().equalsIgnoreCase("ec")){
                if(!Systemmain.ecsystem ){
                    new Log(sender).send("§cECsystem ist deaktiviert!");
                    return true;
                }
                if(pm.hasPermission(Permission.EC)){
                    Player target = player;
                    if(args.length == 1){
                        if(pm.hasPermission(Permission.ECOTHER)) {
                            try {
                                target = player.getServer().getPlayer(args[0]);
                            } catch (Exception e) {
                                new Log(player).send("§cDieser Spieler ist nicht online!");
                            }
                        }
                    }
                    if(target != null) {
                        player.openInventory(target.getEnderChest());
                    }
                }
                return true;
            }

        }

        return false;
    }
}
