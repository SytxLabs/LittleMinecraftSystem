package de.chaosschwein.system.Listener;

import de.chaosschwein.system.Command.ChestCommand;
import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.ChestManager;
import de.chaosschwein.system.Manager.HouseManager;
import de.chaosschwein.system.Manager.PermissionManager;
import de.chaosschwein.system.Utils.InventoryCreator;
import de.chaosschwein.system.Utils.ItemBuilder;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerListener implements Listener {

    public static ArrayList<Player> freezedplayer = new ArrayList<>();

    @EventHandler
    public static void onLeave(PlayerQuitEvent event) {
        freezedplayer.remove(event.getPlayer());
    }

    @EventHandler
    public static void onInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();


        InventoryCreator invcreator = new InventoryCreator(player);
        Log log = new Log(player);
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        String nameinventory = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();
        String itemname = item.getItemMeta().getDisplayName();

        if(freezedplayer.contains(player)) {
            event.setCancelled(true);
            return;
        }

        if(InventoryCreator.invsee.containsKey(player)) {
            Player target = InventoryCreator.invsee.get(player);
            event.setCancelled(true);
            if(new PermissionManager(player).hasPermission(Permission.ADMIN)){
                if(itemname.equalsIgnoreCase("§4Zurück")){
                    invcreator.adminpanel();
                }
                if(itemname.equalsIgnoreCase("§cInventar Clear")){
                    player.closeInventory();
                    InventoryCreator.invsee.remove(player);
                    for (int i = 0; i < target.getInventory().getSize(); i++) {
                        target.getInventory().setItem(i, new ItemBuilder(Material.AIR).build());
                    }
                    log.send("§aInventar geleert");
                    invcreator.invsee(target.getName());
                }
                if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size()>0){
                    if(item.getItemMeta().getLore().get(0).equalsIgnoreCase("§cPress here to Refresh")){
                        player.closeInventory();
                        InventoryCreator.invsee.remove(player);
                        invcreator.invsee(target.getName());
                    }
                }
            }
            return;
        }
        switch (nameinventory) {
            case "§6Adminpanel": {
                event.setCancelled(true);
                if (itemname.equalsIgnoreCase("§4Zurück")) {
                    player.closeInventory();
                    break;
                }
                if (itemname.equalsIgnoreCase("§6Zeit")) {
                    invcreator.timepanel();
                }
                if (itemname.equalsIgnoreCase("§3Wetter")) {
                    invcreator.weatherpanel();
                }
                if(itemname.equalsIgnoreCase("§4Admin")){
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§7Spieler")) {
                    invcreator.playerspanel(1);
                }
                if (itemname.equalsIgnoreCase("§5Gamemode")) {
                    invcreator.gamemodepanel();
                }
                break;
            }

            case "§6Adminpanel | §eZeit": {
                event.setCancelled(true);
                if (itemname.equalsIgnoreCase("§4Zurück")) {
                    invcreator.adminpanel();
                    break;
                }
                if (itemname.equalsIgnoreCase("§6Morgens")) {
                    player.getWorld().setTime(1000);
                    log.send("§aDie Zeit wurde auf §6Morgens §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§6Mittags")) {
                    player.getWorld().setTime(6000);
                    log.send("§aDie Zeit wurde auf §6Mittags §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§6Nacht")) {
                    player.getWorld().setTime(13000);
                    log.send("§aDie Zeit wurde auf §6Nacht §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§6Mitternacht")) {
                    player.getWorld().setTime(18000);
                    log.send("§aDie Zeit wurde auf §6Mitternacht §agesetzt!");
                }
                break;
            }

            case "§6Adminpanel | §3Wetter": {
                event.setCancelled(true);
                if (itemname.equalsIgnoreCase("§4Zurück")) {
                    invcreator.adminpanel();
                    break;
                }
                if (itemname.equalsIgnoreCase("§3Klar")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
                    log.send("§aDas Wetter wurde auf §3Klar §agesetzt!");

                }
                if (itemname.equalsIgnoreCase("§3Regen")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather rain");
                    log.send("§aDas Wetter wurde auf §3Regen §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§3Blitze")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather thunder");
                    log.send("§aDas Wetter wurde auf §3Blitze §agesetzt!");
                }
                break;
            }

            case "§6Adminpanel | §5Gamemode": {
                event.setCancelled(true);

                if (itemname.equalsIgnoreCase("§4Zurück")) {
                    invcreator.adminpanel();
                    break;
                }
                if (itemname.equalsIgnoreCase("§5Gamemode Survival")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    log.send("§aDer Gamemode wurde auf §5Gamemode Survival §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§5Gamemode Creative")) {
                    player.setGameMode(GameMode.CREATIVE);
                    log.send("§aDer Gamemode wurde auf §5Gamemode Creative §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§5Gamemode Spectator")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    log.send("§aDer Gamemode wurde auf §5Gamemode Spectator §agesetzt!");
                }
                if (itemname.equalsIgnoreCase("§5Gamemode Adventure")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    log.send("§aDer Gamemode wurde auf §5Gamemode Adventure §agesetzt!");
                }
                break;
            }

            case "§6Adminpanel | §4Admin": {
                event.setCancelled(true);
                if (itemname.equalsIgnoreCase("§4Zurück")) {
                    invcreator.adminpanel();
                }
                if (itemname.equalsIgnoreCase("§dHome System")) {
                    Systemmain.homesystem = !Systemmain.homesystem;
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§5EC System")) {
                    Systemmain.ecsystem = !Systemmain.ecsystem;
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§eChest System")) {
                    Systemmain.chestsystem = !Systemmain.chestsystem;
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§bInvsee System")) {
                    Systemmain.invseesystem = !Systemmain.invseesystem;
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§aSign Edit System")) {
                    Systemmain.signsystem = !Systemmain.signsystem;
                    invcreator.admin();
                }
                if (itemname.equalsIgnoreCase("§3System Reloaden")) {
                    player.closeInventory();
                    log.send("§aDas System wird neu geladen...");
                    Systemmain.reload();
                }
                if (itemname.equalsIgnoreCase("§4Server Reloaden")) {
                    player.closeInventory();
                    log.send("§aDer Server wird neu geladen...");
                    player.getServer().dispatchCommand(player, "reload");
                }
                break;
            }

            default: {
                if (nameinventory.contains("§6Adminpanel | §4Spieler")) {
                    event.setCancelled(true);
                    if (itemname.equalsIgnoreCase("§4Zurück")) {
                        invcreator.adminpanel();
                        break;
                    }
                    if(item.getItemMeta().getLore() != null) {
                        if (item.getItemMeta().getLore().get(0).equalsIgnoreCase("§cSpieler einstellungen")) {
                            invcreator.playercontrol(ChatColor.stripColor(itemname));
                        }
                    }
                    if (itemname.equalsIgnoreCase("§cLetzte Seite")) {
                        ItemStack siteitem = player.getOpenInventory().getItem(49);
                        if (siteitem != null && siteitem.getItemMeta() != null) {
                            invcreator.playerspanel(Integer.parseInt(ChatColor.stripColor(siteitem.getItemMeta().getDisplayName()).split(" ")[1]) - 1);
                        }
                    }
                    if (itemname.equalsIgnoreCase("§aNächste Seite")) {
                        ItemStack siteitem = player.getOpenInventory().getItem(49);
                        if (siteitem != null && siteitem.getItemMeta() != null) {
                            invcreator.playerspanel(Integer.parseInt(ChatColor.stripColor(siteitem.getItemMeta().getDisplayName()).split(" ")[1]) + 1);
                        }
                    }
                }

                if (nameinventory.contains("§6Adminpanel | §c") && nameinventory.contains("Control")) {
                    event.setCancelled(true);
                    if (itemname.equalsIgnoreCase("§4Zurück")) {
                        invcreator.playerspanel(1);
                        break;
                    }
                    ItemStack playernameitem = player.getOpenInventory().getItem(4);
                    if (playernameitem == null || playernameitem.getItemMeta() == null) {
                        break;
                    }
                    String playername = ChatColor.stripColor(playernameitem.getItemMeta().getDisplayName());
                    if (itemname.equalsIgnoreCase("§4Spieler Töten")) {
                        Bukkit.dispatchCommand(player, "kill " + playername);
                        player.closeInventory();
                    }
                    if (itemname.equalsIgnoreCase("§bZum Spieler Telepotieren")) {
                        Bukkit.dispatchCommand(player, "tp " + playername);
                        player.closeInventory();
                    }
                    if (itemname.equalsIgnoreCase("§6Spieler Bannen")) {
                        Bukkit.dispatchCommand(player, "ban " + playername);
                        player.closeInventory();
                    }
                    if (itemname.equalsIgnoreCase("§eSpieler Kicken")) {
                        Bukkit.dispatchCommand(player, "kick " + playername);
                        player.closeInventory();
                    }

                    if (itemname.equalsIgnoreCase("§dInvsee")) {
                        player.closeInventory();
                        invcreator.invsee(playername);
                    }
                    if (itemname.equalsIgnoreCase("§5Ender Chest")) {
                        Player target = Bukkit.getPlayer(playername);
                        if (target != null) {
                            player.closeInventory();
                            player.openInventory(target.getEnderChest());
                        }else{
                            log.playerNotFound();
                        }
                    }
                    if (itemname.equalsIgnoreCase("§1Freeze")) {
                        Player freezeplayer = Bukkit.getPlayer(playername);
                        if (!freezedplayer.contains(freezeplayer)) {
                            freezedplayer.add(freezeplayer);
                            log.send("§aDu hast den Spieler §e" + playername + " §aFrozen!");
                        } else {
                            freezedplayer.remove(freezeplayer);
                            log.send("§aDu hast den Spieler §e" + playername + " §aEntfrozen!");
                        }
                        player.closeInventory();
                    }
                }
                break;
            }
        }

        if(nameinventory.contains("§5Home")){
            event.setCancelled(true);
            if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size()>0){
                if(item.getItemMeta().getLore().get(0).equalsIgnoreCase("§dTeleportiere dich zu deinem Home")){
                    invcreator.house(ChatColor.stripColor(itemname));
                }
            }
            if((itemname.equalsIgnoreCase("§cLetzte Seite") || itemname.equalsIgnoreCase("§aNächste Seite"))){
                ItemStack itemstack = player.getOpenInventory().getItem(22);
                if(itemstack!=null && itemstack.getItemMeta() != null) {
                    int site = Integer.parseInt(itemstack.getItemMeta().getDisplayName().split(" ")[1]);
                    if (itemname.equalsIgnoreCase("§cLetzte Seite")) {
                        site--;
                    }
                    if (itemname.equalsIgnoreCase("§aNächste Seite")) {
                        site++;
                    }
                    invcreator.house(site);
                }
            }
        }
        if(nameinventory.contains("§5Home §d| §5")){
            event.setCancelled(true);
            if(itemname.equalsIgnoreCase("§dTelepotieren")){
                if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size()>0) {
                    if (new HouseManager(player).getHouses().contains(ChatColor.stripColor(item.getItemMeta().getLore().get(0)))) {
                        player.teleport(new HouseManager(player).getHouseLocation(ChatColor.stripColor(item.getItemMeta().getLore().get(0))));
                        player.closeInventory();
                        log.send("Du hast dich zu deim Home Telepotiert!");
                    } else {
                        log.send("Du hast kein Haus mit diesem Namen.");
                    }
                }
            }
            if(itemname.equalsIgnoreCase("§cLöschen")){
                if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size()>0) {
                    if (new HouseManager(player).getHouses().contains(ChatColor.stripColor(item.getItemMeta().getLore().get(0)))) {
                        new HouseManager(player).deleteHouse(ChatColor.stripColor(item.getItemMeta().getLore().get(0)));
                        invcreator.house(1);
                        log.send("Du hast dein Hause gelöscht!");
                    } else {
                        log.send("Du hast kein Haus mit diesem Namen.");
                    }
                }
            }
            if(itemname.equalsIgnoreCase("§aUmsetzen")){
                if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size()>0) {
                    if (new HouseManager(player).getHouses().contains(ChatColor.stripColor(item.getItemMeta().getLore().get(0)))) {
                        new HouseManager(player).updateHouse(ChatColor.stripColor(item.getItemMeta().getLore().get(0)));
                        log.send("Du hast die Position geändert.");
                    } else {
                        log.send("Du hast kein Haus mit diesem Namen.");
                    }
                }
            }
            if(itemname.equalsIgnoreCase("§4Zurück")){
                invcreator.house(1);
            }
        }

    }

    @EventHandler
    public static void onClick(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()){
            if(new ChestManager(event.getClickedBlock()).isExisting()){
                new ChestManager(event.getClickedBlock()).log(ChestManager.Interaction.OPEN,event.getPlayer());
            }
        }
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()){
            if(new ChestManager(event.getClickedBlock()).isExisting()){
                if(ChestCommand.chestcheck.containsKey(event.getPlayer())){
                    ChestManager cm = new ChestManager(event.getClickedBlock());
                    int max = ChestCommand.chestcheck.get(event.getPlayer());
                    HashMap<Long, ChestManager.Interaction> logs = cm.getLogs();
                    if(max >= logs.size()){
                        max = logs.size();
                    }
                    int returned = 0;
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    for(long key : logs.keySet()){
                       if(returned >= max) {
                           break;
                       }
                       String date = format.format(new Date(key)).split(" ")[0]+" um "+format.format(new Date(key)).split(" ")[1];
                       boolean owner = cm.getOwner() == UUID.fromString(cm.getLogPlayer(key, logs.get(key)));
                       OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(cm.getLogPlayer(key, logs.get(key))));
                       new Log(event.getPlayer()).send(owner?"Der Besitzer ":""+op.getName() + " hat die Kiste am " + date + " "+logs.get(key).getChat()+".");
                       returned++;
                    }

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public static void onInvClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        InventoryCreator.invsee.remove(player);
    }

    @EventHandler
    public static void onInvOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if(freezedplayer.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(freezedplayer.contains(player)) {
            event.setCancelled(true);
        }
        if(new ChestManager(event.getBlock()).isExisting()){
            new ChestManager(event.getBlock()).removeChest();
        }
    }

    @EventHandler
    public static void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(freezedplayer.contains(player)) {
            event.setCancelled(true);
        }
        if(ChestManager.Type.get(event.getBlock().getType()) != null) {
            new ChestManager(event.getBlock(),player).addChest();
        }
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (freezedplayer.contains(player)) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if(to == null) { return; }
            if(to.getY() > from.getY() || to.getX() != from.getX() || to.getZ() != from.getZ()) {
                event.setCancelled(true);
            }
        }
    }
}
