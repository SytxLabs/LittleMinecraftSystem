package de.chaosschwein.system.Command;

import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.HouseManager;
import de.chaosschwein.system.Utils.InventoryCreator;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Systemmain.homesystem ){
            new Log(sender).send("§cHomesystem ist deaktiviert!");
            return true;
        }
        if(sender instanceof Player) {
            Player player = (Player) sender;
            HouseManager hm = new HouseManager(player);
            Log log = new Log(player);
            if(args.length == 0) {
                if(hm.getHouseCount() == 1) {
                    player.teleport(hm.getHouseLocation());
                    log.send("Du wurdest zu deinem Haus teleportiert.");
                }else if(hm.getHouseCount() == 0){
                    hm.createHouse();
                }else{
                    new InventoryCreator(player).house(1);
                }
            }
            else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("set")) {
                    hm.createHouse();
                    log.send("Du hast dein Haus erstellt.");
                }else{
                    if(hm.getHouses().contains(args[0])){
                        Location loc = hm.getHouseLocation(args[0]);
                        if(loc != null) {
                            player.teleport(loc);
                        }
                    }else{
                        log.send("Du hast kein Haus mit diesem Namen.");
                    }
                }
            }else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("set")) {
                    if(hm.createHouse(args[1])) {
                        log.send("Du hast dein Haus erstellt.");
                    }else{
                        hm.updateHouse(args[1]);
                        log.send("Du hast die Position geändert");
                    }
                }
                if(args[0].equalsIgnoreCase("remove")) {
                    hm.deleteHouse(args[1]);
                    log.send("Du hast dein Haus gelöscht.");
                }
            }
        }
        return true;
    }
}
