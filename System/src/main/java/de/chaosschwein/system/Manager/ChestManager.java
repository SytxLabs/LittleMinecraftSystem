package de.chaosschwein.system.Manager;

import de.chaosschwein.system.Main.Systemmain;
import de.chaosschwein.system.Utils.Log;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.text.Collator;
import java.util.*;

public class ChestManager {

    private Block chest;
    private Player player;
    private final FileManager file;

    public ChestManager(Block block) {
        this.chest = block;
        this.file = new FileManager("data", "chests");
    }

    public ChestManager(Player player) {
        this.player = player;
        this.file = new FileManager("data", "chests");
    }

    public ChestManager(Block block, Player player) {
        this.chest = block;
        this.player = player;
        this.file = new FileManager("data", "chests");
    }

    public void addChest() {
        if (!isExisting()) {
            if (Type.get(chest.getType()) != null) {
                file.write("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Owner", player.getUniqueId().toString());
                file.write("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Type", Objects.requireNonNull(Type.get(chest.getType())).getName());
                log(Interaction.CREATE);
            }
        }
    }

    public void removeChest() {
        if (isExisting()) {
            file.write("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Owner", null);
            file.write("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Type", null);
            file.write("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ(), null);
            file.write("Log.Interactions." + chest.getX() + "," + chest.getY() + "," + chest.getZ(), null);
            file.write("Log.Player." + chest.getX() + "," + chest.getY() + "," + chest.getZ(), null);
            file.write("Log.Time." + chest.getX() + "," + chest.getY() + "," + chest.getZ(), null);
        }
    }

    public boolean isExisting() {
        return file.read("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Owner") != null;
    }

    public UUID getOwner() {
        return UUID.fromString((String)file.read("Chest."+chest.getX() + "," + chest.getY() + "," + chest.getZ() + ".Owner"));
    }

    public void log(Interaction interaction) {
        log(interaction, player);
    }

    public void log(Interaction interaction, Player player) {
        if(!Systemmain.chestsystem ){return;}
        if (isExisting()) {
            long ms = System.currentTimeMillis();
            file.write("Log.Interactions." + chest.getX() + "," + chest.getY() + "," + chest.getZ() + "." + ms, interaction.getName());
            file.write("Log.Player." + chest.getX() + "," + chest.getY() + "," + chest.getZ() + "." + ms + "." + interaction.getName(), player.getUniqueId().toString());
            String times = file.read("Log.Time." + chest.getX() + "," + chest.getY() + "," + chest.getZ())==null ? ms+"" : file.read("Log.Time." + chest.getX() + "," + chest.getY() + "," + chest.getZ())+","+ms;
            file.write("Log.Time." + chest.getX() + "," + chest.getY() + "," + chest.getZ(),  times);
        }
    }

    public HashMap<Long, Interaction> getLogs(){
        HashMap<Long, Interaction> logs = new HashMap<>();
        if(isExisting()){
            for(String line : file.read("Log.Time." + chest.getX() + "," + chest.getY() + "," + chest.getZ()).toString().split(",")){
                String split = file.read("Log.Interactions." + chest.getX() + "," + chest.getY() + "," + chest.getZ() + "." + line).toString();
                logs.put(Long.parseLong(line), Interaction.getFromName(split));
            }
        }
        HashMap<Long, Interaction> sorted = new HashMap<>();
        logs.keySet().stream().sorted(Collections.reverseOrder()).forEach(key -> sorted.put(key, logs.get(key)));
        return sorted;
    }

    public int getLogsCount(){
        return getLogs().size();
    }

    public String getLogPlayer(long time,Interaction interaction){
        String s = "";
        if(isExisting()){
            s = file.read("Log.Player." + chest.getX() + "," + chest.getY() + "," + chest.getZ() + "." + time + "." + interaction.getName()).toString();
        }
        return s;
    }


    public enum Interaction {
        OPEN("open","geöffnet"),
        CREATE("create", "erstellt");

        private final String chat;

        private final String name;

        Interaction(String name,String chat) {
            this.name = name;
            this.chat = chat;
        }

        public String getName() {
            return name;
        }

        public String getChat() {
            return chat;
        }

        public static Interaction getFromName(String name) {
            for (Interaction interaction : Interaction.values()) {
                if (interaction.getName().equals(name)) {
                    return interaction;
                }
            }
            return null;
        }
    }

    public enum Type {
        CHEST("chest", Material.CHEST),
        TRAPPED_CHEST("trapped_chest", Material.TRAPPED_CHEST),
        SHULKER_BOX("shulker_box", Material.SHULKER_BOX),
        ENDER_CHEST("ender_chest", Material.ENDER_CHEST),
        BARREL("barrel", Material.BARREL),
        MINECART_CHEST("minecart_chest", Material.CHEST_MINECART);

        private final String name;
        private final Material material;

        Type(String name, Material material) {
            this.name = name;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public static Type get(String name) {
            for (Type type : Type.values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            return null;
        }

        public Material getMaterial() {
            return material;
        }

        static final ArrayList<Material> shulkerBoxes = new ArrayList<Material>() {
            {
                add(Material.SHULKER_BOX);
                add(Material.WHITE_SHULKER_BOX);
                add(Material.ORANGE_SHULKER_BOX);
                add(Material.MAGENTA_SHULKER_BOX);
                add(Material.LIGHT_BLUE_SHULKER_BOX);
                add(Material.YELLOW_SHULKER_BOX);
                add(Material.LIME_SHULKER_BOX);
                add(Material.PINK_SHULKER_BOX);
                add(Material.GRAY_SHULKER_BOX);
                add(Material.LIGHT_GRAY_SHULKER_BOX);
                add(Material.CYAN_SHULKER_BOX);
                add(Material.PURPLE_SHULKER_BOX);
                add(Material.BLUE_SHULKER_BOX);
                add(Material.BROWN_SHULKER_BOX);
                add(Material.GREEN_SHULKER_BOX);
                add(Material.RED_SHULKER_BOX);
                add(Material.BLACK_SHULKER_BOX);
            }
        };

        public static Type get(Material material) {
            material = shulkerBoxes.contains(material) ? Material.SHULKER_BOX : material;
            for (Type type : Type.values()) {
                if (type.getMaterial().equals(material)) {
                    return type;
                }
            }
            return null;
        }
    }
}
