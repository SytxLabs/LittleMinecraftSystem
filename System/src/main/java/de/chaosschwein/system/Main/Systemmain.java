package de.chaosschwein.system.Main;

import de.chaosschwein.system.Command.*;
import de.chaosschwein.system.Listener.PlayerListener;
import de.chaosschwein.system.Manager.FileManager;
import de.chaosschwein.system.Manager.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Systemmain extends JavaPlugin {

    public static String prefix = "§8[§6System§8] §7";

    private static Systemmain instance;
    public static FileManager config;

    public static boolean homesystem = true;
    public static boolean ecsystem = true;
    public static boolean invseesystem = true;
    public static boolean signsystem = true;
    public static boolean chestsystem = true;

    public static Systemmain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        start();
    }

    @Override
    public void onDisable() {

    }

    public static void start(){
        config = new FileManager("config");
        config.writeDefault(new HashMap<String,Object>(){{
            put("prefix", "&8[&6System§8] &7");
        }});

        prefix = config.read("prefix").toString();
        new PermissionManager();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);
        instance.getCommand("admin").setExecutor(new AdminCommand());
        instance.getCommand("invsee").setExecutor(new InvseeCommand());
        instance.getCommand("system").setExecutor(new SystemCommand());
        instance.getCommand("ec").setExecutor(new InvseeCommand());
        instance.getCommand("home").setExecutor(new HomeCommand());
        instance.getCommand("chestcheck").setExecutor(new ChestCommand());
        instance.getCommand("gm").setExecutor(new AdminCommand());
        instance.getCommand("signedit").setExecutor(new SignCommand());
        homesystem = true;
        ecsystem = true;
        invseesystem = true;
        signsystem = true;
        chestsystem = true;
        Bukkit.getConsoleSender().sendMessage("§aSystem §7Plugin wurde geladen!");
    }

    public static void stop(){
        instance.getCommand("admin").setExecutor(null);
        instance.getCommand("invsee").setExecutor(null);
        instance.getCommand("ec").setExecutor(null);
        instance.getCommand("home").setExecutor(null);
        instance.getCommand("chestcheck").setExecutor(null);
        instance.getCommand("gm").setExecutor(null);
        instance.getCommand("signedit").setExecutor(null);
        homesystem = true;
        ecsystem = true;
        invseesystem = true;
        signsystem = true;
        chestsystem = true;
        Bukkit.getPluginManager().disablePlugin(instance);
        Bukkit.getConsoleSender().sendMessage("§aSystem §7Plugin wurde gestoppt!");
    }

    public static void reload(){
        Bukkit.getConsoleSender().sendMessage("§aSystem §7Plugin wurde neugeladen!");
        prefix = config.read("prefix").toString();
        new PermissionManager();
        Bukkit.getPluginManager().disablePlugin(instance);
        Bukkit.getPluginManager().enablePlugin(instance);
    }
}
