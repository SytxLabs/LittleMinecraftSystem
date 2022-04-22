package de.chaosschwein.system.Command;

import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.PermissionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.chaosschwein.system.Utils.Log;
import java.util.ArrayList;

public class SignCommand implements CommandExecutor {
    //List of Minecraft signs

    private static final ArrayList<Material> signs = new ArrayList<Material>(){{
        add(Material.ACACIA_SIGN);
        add(Material.ACACIA_WALL_SIGN);
        add(Material.BIRCH_SIGN);
        add(Material.BIRCH_WALL_SIGN);
        add(Material.DARK_OAK_SIGN);
        add(Material.DARK_OAK_WALL_SIGN);
        add(Material.JUNGLE_SIGN);
        add(Material.JUNGLE_WALL_SIGN);
        add(Material.OAK_SIGN);
        add(Material.OAK_WALL_SIGN);
        add(Material.SPRUCE_SIGN);
        add(Material.SPRUCE_WALL_SIGN);
    }};


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Systemmain.signsystem ){
            new Log(sender).send("§cSignsystem ist deaktiviert!");
            return true;
        }
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Log log = new Log(player);
            if(new PermissionManager(player).hasPermission(Permission.SIGN)){
                if(args.length>1){
                   try{
                       int x = Integer.parseInt(args[0])-1;
                       Block block = player.getTargetBlock(null, 10);
                       if(signs.contains(block.getType())){
                           Sign sign = (Sign) block.getState();
                           String s = "";
                           for(int i = 1; i<args.length; i++){
                               s += args[i] + " ";
                           }
                           sign.setLine(x, ChatColor.translateAlternateColorCodes('&',s));
                           sign.update(true);
                           log.send("§aSign changed!");
                       }else{
                           log.send("§cYou can only change the text of a sign!");
                       }
                   }catch (NumberFormatException e){
                       log.send("§c/signedit <Zeile> <text>");
                   }
                }else{
                    log.send("§c/signedit <Zeile> <text>");
                }
            }else{
                log.noPermission();
            }
        }
        return true;
    }
}
