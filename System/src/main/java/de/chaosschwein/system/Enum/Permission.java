package de.chaosschwein.system.Enum;

import org.bukkit.command.CommandSender;

public enum Permission {
    INVSEE("system.invsee", "Invsee"),
    EC("system.ec", "EC"),
    ECOTHER("system.ec.other", "EC other"),
    ADMIN("system.admin", "Admin"),
    ADMINTIME("system.admin.time", "Admin time"),
    ADMINWEATHER("system.admin.weather", "Admin weather"),
    ADMINGAMEMODE("system.admin.gamemode", "Admin gamemode"),
    ADMINPLAYER("system.admin.player", "Admin player"),
    CHESTCHECK("system.chestcheck", "Chestcheck"),
    GAMEMODE("system.gamemode", "Gamemode"),
    SIGN("system.signedit", "Signedit");


    private String permission;
    private final String name;

    Permission(String permission, String name) {
        this.permission = permission;
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }
}
