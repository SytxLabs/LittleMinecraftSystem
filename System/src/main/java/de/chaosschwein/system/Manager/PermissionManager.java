package de.chaosschwein.system.Manager;

import de.chaosschwein.system.Enum.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PermissionManager {

    private CommandSender sender = null;
    private static FileManager permissionfile;

    private static void init(){
        permissionfile = new FileManager("permissions");
        HashMap<String, Object> data = new HashMap<>();
        for (Permission perm : Permission.values()) {
            data.put(perm.getName(), perm.getPermission());
        }
        permissionfile.writeDefault(data);

        for (Permission perm : Permission.values()) {
            perm.setPermission((String) permissionfile.read(perm.getName()));
        }
    }

    public PermissionManager(){
        init();
    }

    public PermissionManager(CommandSender sender) {
        this.sender = sender;
    }

    public PermissionManager(Player player) {
        this.sender = player;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public void setSender(Player player) {
        this.sender = player;
    }


    public boolean hasPermission(String permission) {
        if(sender==null) return false;
        return sender.hasPermission(permission);
    }

    public boolean hasPermission(Permission perm){
        if(sender==null) return false;
        return sender.hasPermission(perm.getPermission());
    }
}
