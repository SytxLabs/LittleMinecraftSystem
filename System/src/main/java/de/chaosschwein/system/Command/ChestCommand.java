package de.chaosschwein.system.Command;

import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.PermissionManager;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.*;

public class ChestCommand implements CommandExecutor {

    public static HashMap<Player,Integer> chestcheck = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Systemmain.chestsystem ){
            new Log(sender).send("§cChestsystem ist deaktiviert!");
            return true;
        }
        if(sender instanceof Player){
            Player p = (Player) sender;
            Log log = new Log(p);
            if(!new PermissionManager(p).hasPermission(Permission.CHESTCHECK)){
                log.noPermission();
                return true;
            }
            if(args.length == 0){
                if(chestcheck.containsKey(p)){
                    log.send("Du hast den Chest-Check deaktiviert!");
                    chestcheck.remove(p);
                }
                else{
                    log.send("§aDu hast den Chest-Check aktiviert!");
                    chestcheck.put(p,1);
                }
            }else if(args.length == 1){
                if(chestcheck.containsKey(p)){
                    log.send("Du hast den Chest-Check deaktiviert!");
                    chestcheck.remove(p);
                }else {
                    try {
                        int i = Integer.parseInt(args[0]);
                        if (i > 0) {
                            log.send("§aDu hast den Chest-Check aktiviert! Du bekommst die Letzen §e" + i + " §aLogs!");
                            chestcheck.put(p, i);
                        } else {
                            log.send("§cDu kannst nur Zahlen größer als 0 eingeben!");
                        }
                    } catch (Exception e) {
                        log.send("§cBitte gib eine Zahl ein!");
                    }
                }
            }
        }
        return true;
    }
}
