package me.i3ick.SheepBump;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.material.Dye;

import java.util.HashMap;

/**
 * Created by Karlo on 8/2/2016.
 */
public class SBcontroller {


    static SheepBumpMain plugin;

    public SBcontroller(SheepBumpMain passPlugin){
        SBcontroller.plugin = passPlugin;
    }


    public void saveHashMap(HashMap<Integer, Color> hashMap){
        for(Object key : hashMap.keySet()){
            plugin.getConfig().set("HashMap."+key, hashMap.get(key));
        }
        plugin.saveConfig();
    }

    public HashMap<Integer, Color> loadHashMap(){
        HashMap<Integer, Color> hm = new HashMap<Integer, Color>();
        for (String key : plugin.getConfig().getConfigurationSection("HashMap").getKeys(false)) {
            plugin.getLogger().info(plugin.getConfig().getConfigurationSection("HashMap").getKeys(false) + "");
            int key2 = 2;
            hm.put(key2, plugin.getConfig().getColor("HashMap." + key));
        }
        return hm;
    }


}
