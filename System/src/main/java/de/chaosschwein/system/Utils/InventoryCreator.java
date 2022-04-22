package de.chaosschwein.system.Utils;

import de.chaosschwein.system.Enum.Permission;
import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Manager.HouseManager;
import de.chaosschwein.system.Manager.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryCreator {

    private final Player player;
    public static HashMap<Player,Player> invsee = new HashMap<>();
    private final PermissionManager pm;

    public InventoryCreator(Player player) {
        this.player = player;
        this.pm = new PermissionManager(player);
    }

    public void adminpanel(){
        if(!pm.hasPermission(Permission.ADMIN)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 27, "§6Adminpanel");

        fillInv(inv);
        inv.setItem(10,null);
        inv.setItem(11,null);
        inv.setItem(13,null);
        inv.setItem(15,null);
        inv.setItem(16,null);
        if(pm.hasPermission(Permission.ADMINTIME)) {
            inv.addItem( new ItemBuilder(Material.CLOCK).setName("§6Zeit").setLore("§eSetze die Uhrzeit!").build());
        }
        if(pm.hasPermission(Permission.ADMINWEATHER)) {
            inv.addItem( new ItemBuilder(Material.FIREWORK_STAR).setName("§3Wetter").setLore("§bSetze das Wetter!").build());
        }
        if(pm.hasPermission(Permission.ADMINPLAYER)) {
            inv.addItem( new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(this.player).setName("§7Spieler").setLore("§8Sehe alle Spieler an!").build());
        }
        inv.addItem( new ItemBuilder(Material.NETHER_STAR).setName("§4Admin").setLore("§cServer Verwalten!").build());
        if(pm.hasPermission(Permission.ADMINGAMEMODE)) {
            inv.addItem( new ItemBuilder(Material.ENDER_EYE).setName("§5Gamemode").setLore("§dSetze dein Gamemode!").build());
        }
        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());

        fillInv(inv);
        player.openInventory(inv);
    }

    public void admin(){
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 36, "§6Adminpanel | §4Admin");
        inv.setItem(10,new ItemBuilder(Material.RED_BED).setName("§dHome System").setLore(Systemmain.homesystem ? "§aAktiviert" : "§cDeaktiviert").build());
        inv.setItem(11,new ItemBuilder(Material.ENDER_CHEST).setName("§5EC System").setLore(Systemmain.ecsystem ? "§aAktiviert" : "§cDeaktiviert").build());
        inv.setItem(13,new ItemBuilder(Material.CHEST).setName("§eChest System").setLore(Systemmain.chestsystem ? "§aAktiviert" : "§cDeaktiviert").build());
        inv.setItem(15,new ItemBuilder(Material.ENDER_EYE).setName("§bInvsee System").setLore(Systemmain.invseesystem ? "§aAktiviert" : "§cDeaktiviert").build());
        inv.setItem(16,new ItemBuilder(Material.OAK_SIGN).setName("§aSign Edit System").setLore(Systemmain.signsystem ? "§aAktiviert" : "§cDeaktiviert").build());

        inv.setItem(21,new ItemBuilder(Material.NETHER_STAR).setName("§3System Reloaden").build());
        inv.setItem(23,new ItemBuilder(Material.DIAMOND_SWORD).setName("§4Server Reloaden").build());

        inv.setItem(27,new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        fillInv(inv);
        player.openInventory(inv);
    }

    public void timepanel(){
        if(!pm.hasPermission(Permission.ADMINTIME)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 27, "§6Adminpanel | §eZeit");

        inv.setItem(10, new ItemBuilder(Material.CLOCK).setName("§6Morgens").build());
        inv.setItem(12, new ItemBuilder(Material.CLOCK).setName("§6Mittags").build());
        inv.setItem(14, new ItemBuilder(Material.CLOCK).setName("§6Nacht").build());
        inv.setItem(16, new ItemBuilder(Material.CLOCK).setName("§6Mitternacht").build());

        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        fillInv(inv);
        player.openInventory(inv);
    }

    public void weatherpanel(){
        if(!pm.hasPermission(Permission.ADMINWEATHER)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 27, "§6Adminpanel | §3Wetter");

        inv.setItem(11, new ItemBuilder(Material.CLOCK).setName("§3Klar").build());
        inv.setItem(13, new ItemBuilder(Material.CLOCK).setName("§3Regen").build());
        inv.setItem(15, new ItemBuilder(Material.CLOCK).setName("§3Blitze").build());

        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        fillInv(inv);
        player.openInventory(inv);
    }

    public void gamemodepanel(){
        if(!pm.hasPermission(Permission.ADMINGAMEMODE)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 27, "§6Adminpanel | §5Gamemode");

        inv.setItem(10, new ItemBuilder(Material.CLOCK).setName("§5Gamemode Survival").build());
        inv.setItem(12, new ItemBuilder(Material.CLOCK).setName("§5Gamemode Creative").build());
        inv.setItem(14, new ItemBuilder(Material.CLOCK).setName("§5Gamemode Spectator").build());
        inv.setItem(16, new ItemBuilder(Material.CLOCK).setName("§5Gamemode Adventure").build());

        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        fillInv(inv);
        player.openInventory(inv);
    }

    public void playerspanel(int page){
        if(!pm.hasPermission(Permission.ADMINPLAYER)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        int max = 36;
        List<Object> players = Arrays.asList(Bukkit.getOnlinePlayers().toArray());
        int playersamount = Bukkit.getOnlinePlayers().size();
        float p = (float) playersamount / max;
        int rows = Math.round(p) + 1;
        Inventory inv = Bukkit.createInventory(null, 54, "§6Adminpanel | §4Spieler (" + page + " / " + rows + ")");

        for(int i = 0; i < 9; i++){inv.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("§e").build());}

        inv.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(this.player).setName("§4Spieler").build());

        for (int i=45; i<54; i++){inv.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("§e").build());}
        inv.setItem(45, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        if(page > 1) {inv.setItem(47, new ItemBuilder(Material.ARROW).setName("§cLetzte Seite").build());}
        inv.setItem(49, new ItemBuilder(Material.CLOCK).setName("§bSeite "+page).build());
        if(page < rows) {inv.setItem(51, new ItemBuilder(Material.ARROW).setName("§aNächste Seite").build());}

        int start = (page - 1) * max;
        int end = start + max;
        for (int i = start; i < end; i++) {
            if (i >= playersamount) {
                break;
            }
            Player p1 = (Player) players.get(i);
            inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(p1).setName("§c" + p1.getName()).setLore(new ArrayList<String>(){{add("§cSpieler einstellungen");}}).build());
        }

        fillInv(inv);
        player.openInventory(inv);
    }

    public void playercontrol(String name){
        if(!pm.hasPermission(Permission.ADMINPLAYER)){ new Log(player).noPermission(); return; }
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 27, "§6Adminpanel | §c" + name + " Control");

        inv.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(name).setName("§c"+name).build());

        inv.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD).setName("§4Spieler Töten").build());
        inv.setItem(11, new ItemBuilder(Material.ENDER_PEARL).setName("§bZum Spieler Telepotieren").build());
        inv.setItem(12, new ItemBuilder(Material.WITHER_SKELETON_SKULL).setName("§6Spieler Bannen").build());
        inv.setItem(13, new ItemBuilder(Material.SKELETON_SKULL).setName("§eSpieler Kicken").build());
        inv.setItem(14, new ItemBuilder(Material.CHEST).setName("§dInvsee").build());
        inv.setItem(15, new ItemBuilder(Material.ENDER_CHEST).setName("§5Ender Chest").build());
        inv.setItem(16, new ItemBuilder(Material.BLUE_ICE).setName("§1Freeze").build());

        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
        fillInv(inv);
        player.openInventory(inv);
    }

    public void invsee(String name){
        try{
            if(pm.hasPermission(Permission.INVSEE))
            {
                player.closeInventory();
                Player target = Bukkit.getPlayer(name);
                if(target == null){ new Log(player).playerNotFound(); return; }
                Inventory invtarget = target.getInventory();
                Inventory inv = Bukkit.createInventory(null, 54, "§6Invsee | §c" + target.getName());

                for(int i = 0; i < 18; i++){inv.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("§e").build());}


                if(pm.hasPermission(Permission.ADMIN)){
                    inv.setItem(0, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());
                    inv.setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§cInventar Clear").build());
                }
                inv.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target).setName("§c"+target.getName()).setLore("§cPress here to Refresh").build());
                ArrayList<ItemStack> equipment = new ArrayList<>();
                if(target.getEquipment() != null){
                    if(target.getEquipment().getHelmet() != null){
                        inv.setItem(10, target.getEquipment().getHelmet());
                        equipment.add(target.getEquipment().getHelmet());
                    }
                    if(target.getEquipment().getChestplate() != null){
                        inv.setItem(11, target.getEquipment().getChestplate());
                        equipment.add(target.getEquipment().getChestplate());
                    }
                    if(target.getEquipment().getLeggings() != null){
                        inv.setItem(12, target.getEquipment().getLeggings());
                        equipment.add(target.getEquipment().getLeggings());
                    }
                    if(target.getEquipment().getBoots() != null){
                        inv.setItem(13, target.getEquipment().getBoots());
                        equipment.add(target.getEquipment().getBoots());
                    }
                    if(target.getEquipment().getItemInMainHand().getType() != Material.AIR){
                        inv.setItem(15, target.getEquipment().getItemInMainHand());
                        equipment.add(target.getEquipment().getItemInMainHand());
                    }
                    if(target.getEquipment().getItemInOffHand().getType() != Material.AIR){
                        inv.setItem(16, target.getEquipment().getItemInOffHand());
                        equipment.add(target.getEquipment().getItemInOffHand());
                    }
                }


                for (int i = 0; i < invtarget.getSize(); i++) {
                    ItemStack item = invtarget.getItem(i);
                    if (item == null || item.getType() == Material.AIR) {continue;}
                    if(equipment.contains(item)){continue;}
                    inv.addItem(item);
                }
                player.openInventory(inv);
                invsee.put(player,target);
            }
        }catch (Exception e){
            e.printStackTrace();
            new Log(player).playerNotFound();
        }
    }

    public void house(int page){
        player.closeInventory();

        ArrayList<String> homes = new HouseManager(player).getHouses();
        if(homes.size()<1){return;}
        int row = Math.round((float) (homes.size() / 7))+1;
        Inventory inv = Bukkit.createInventory(null,27,"§5Home ("+page+" / "+row+")");
        fillInv(inv);
        for (int i = 10; i<17; i++) {
            inv.setItem(i,null);
        }
        int start = (page - 1) * 7;
        int end = start + 7;
        for (int i = start; i < end; i++) {
            if (i >= homes.size()) {break;}
            inv.addItem(new ItemBuilder(Material.DARK_OAK_DOOR).setName("§c" + homes.get(i)).setLore("§dTeleportiere dich zu deinem Home").build());
        }
        if(page > 1) {inv.setItem(20, new ItemBuilder(Material.ARROW).setName("§cLetzte Seite").build());}
        inv.setItem(22, new ItemBuilder(Material.CLOCK).setName("§bSeite "+page).build());
        if(page < row) {inv.setItem(24, new ItemBuilder(Material.ARROW).setName("§aNächste Seite").build());}
        fillInv(inv);
        player.openInventory(inv);
    }

    public void house(String name){
        player.closeInventory();
        ArrayList<String> homes = new HouseManager(player).getHouses();
        if(homes.contains(name)){house(1);}
        Inventory inv = Bukkit.createInventory(null,27,"§5Home §d| §5"+name);
        fillInv(inv);

        inv.setItem(11, new ItemBuilder(Material.ENDER_PEARL).setName("§dTelepotieren").setLore("§d"+name).build());
        inv.setItem(13, new ItemBuilder(Material.RED_BED).setName("§aUmsetzen").setLore("§d"+name).build());
        inv.setItem(15, new ItemBuilder(Material.STONECUTTER).setName("§cLöschen").setLore("§d"+name).build());
        inv.setItem(18, new ItemBuilder(Material.BARRIER).setName("§4Zurück").build());

        player.openInventory(inv);
    }

    private void fillInv(Inventory inv){
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || Objects.requireNonNull(inv.getItem(i)).getType() == Material.AIR){
                inv.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("§e").build());
            }
        }
    }

}
