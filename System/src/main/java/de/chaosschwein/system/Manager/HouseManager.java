package de.chaosschwein.system.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class HouseManager {

    private final FileManager houseFile;
    private final Player player;
    public HouseManager(Player p){
        this.player = p;
        this.houseFile = new FileManager("data","house");
    }

    public void createHouse(){
        int count = getHouseCount()+1;
        while (existHouse(count+"")){
            count++;
        }
        houseFile.write("house."+player.getUniqueId()+"."+count+".location", player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        houseFile.write("house."+player.getUniqueId()+"."+count+".rotation", player.getLocation().getYaw()+","+player.getLocation().getPitch());
        houseFile.write("house."+player.getUniqueId()+"."+count+".world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        houseFile.write("house."+player.getUniqueId()+".count", count);
        addHousename(count+"");
    }

    public boolean createHouse(String name){
        name = name.toLowerCase();
        if(existHouse(name)){return false;}
        int count = getHouseCount()+1;
        houseFile.write("house."+player.getUniqueId()+"."+name+".location", player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        houseFile.write("house."+player.getUniqueId()+"."+name+".rotation", player.getLocation().getYaw()+","+player.getLocation().getPitch());
        houseFile.write("house."+player.getUniqueId()+"."+name+".world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        houseFile.write("house."+player.getUniqueId()+".count", count);
        addHousename(name);
        return true;
    }

    public boolean updateHouse(String name){
        name = name.toLowerCase();
        if(!existHouse(name)){return false;}
        houseFile.write("house."+player.getUniqueId()+"."+name+".location", player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        houseFile.write("house."+player.getUniqueId()+"."+name+".rotation", player.getLocation().getYaw()+","+player.getLocation().getPitch());
        houseFile.write("house."+player.getUniqueId()+"."+name+".world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        return true;
    }

    public boolean deleteHouse(String name){
        name = name.toLowerCase();
        if(!existHouse(name)){return false;}
        houseFile.write("house."+player.getUniqueId()+"."+name+".location", null);
        houseFile.write("house."+player.getUniqueId()+"."+name+".world", null);
        houseFile.write("house."+player.getUniqueId()+"."+name+".rotation",null);
        houseFile.write("house."+player.getUniqueId()+"."+name,null);
        houseFile.write("house."+player.getUniqueId()+".count", getHouseCount()-1);
        removeHousename(name);
        return true;
    }

    public Location getHouseLocation(){
        if(getHouses().size()>0 && getHouseCount()>0) {
            String name = getHouses().get(0);
            String[] loc = houseFile.read("house." + player.getUniqueId().toString() + "." + name + ".location").toString().split(",");
            String[] rot = houseFile.read("house." + player.getUniqueId().toString() + "." + name + ".rotation").toString().split(",");
            return new Location(getHouseWorld(name), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Float.parseFloat(rot[0]), Float.parseFloat(rot[1]));
        }
        return null;
    }

    public Location getHouseLocation(String name){
        name = name.toLowerCase();
        if(!existHouse(name)) return null;
        String[] loc = houseFile.read("house."+player.getUniqueId().toString()+"."+name+".location").toString().split(",");
        String[] rot = houseFile.read("house."+player.getUniqueId().toString()+"."+name+".rotation").toString().split(",");
        return new Location(getHouseWorld(name), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Float.parseFloat(rot[0]), Float.parseFloat(rot[1]));
    }

    public World getHouseWorld(String name){
        name = name.toLowerCase();
        if(!existHouse(name)) return Bukkit.getWorld("world");
        return Bukkit.getWorld((String)houseFile.read("house."+player.getUniqueId()+"."+name+".world"));
    }

    public ArrayList<String> getHouses(){
        ArrayList<String> houses = new ArrayList<>();
        if(houseFile.read("house."+player.getUniqueId()+".names") != null) {
            String[] names = houseFile.read("house."+player.getUniqueId()+".names").toString().split(",");
            houses.addAll(Arrays.asList(names));
        }
        return houses;
    }

    public String getHousesString(){
        return houseFile.read("house." + player.getUniqueId() + ".names") != null ? houseFile.read("house." + player.getUniqueId() + ".names").toString() : "";
    }

    private void addHousename(String name){
        name = name.toLowerCase();
        ArrayList<String> houses = getHouses();
        houses.add(name);
        String s = "";
        for(String house : houses){
            s += house+",";
        }
        houseFile.write("house." + player.getUniqueId() + ".names", s);
    }

    private void removeHousename(String name){
        name = name.toLowerCase();
        ArrayList<String> houses = getHouses();
        houses.remove(name);
        if(houses.size()<1){
            houseFile.write("house." + player.getUniqueId() + ".names", null);
            return;
        }
        String s = "";
        for(String house : houses){
            s += house+",";
        }
        houseFile.write("house." + player.getUniqueId() + ".names", s);
    }

    public boolean existHouse(String name){
        name = name.toLowerCase();
        return getHouses().contains(name);
    }



    public int getHouseCount(){
        return Integer.parseInt(houseFile.read("house."+player.getUniqueId()+".count") == null ? "0" : (String)houseFile.read("house."+player.getUniqueId()+".count"));
    }
}
