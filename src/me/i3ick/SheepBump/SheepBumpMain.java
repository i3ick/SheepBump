package me.i3ick.SheepBump;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Karlo on 8/2/2016.
 */
public class SheepBumpMain extends JavaPlugin {



    @Override
    public void onDisable() {

        File f = new File(this.getDataFolder() + File.separator + "config.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(f);
        getLogger().info("SheepBump disabled!");
        try {
            arenaConfig.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @Override
    public void onEnable() {

        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = getConfig();

        if (!configFile.exists()) {
            getLogger().info("Config file created!");
            getLogger().info("Config hiii!");
        }
              saveDefaultConfig();
                    config.addDefault("Preservewool", true);
                    config.options().copyDefaults(true);
                    saveConfig();


        SBcontroller gameController = new SBcontroller(this);

        // register events
        this.getServer().getPluginManager().registerEvents(new SheepBumpEvents(this, gameController), this);
        this.getLogger().info("strt");
        gameController.loadHashMap();

        PluginDescriptionFile pdf = this.getDescription();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage("\n\n#####~~~~~~" + ChatColor.AQUA +" SHEEPBUMP " + ChatColor.GRAY +"~~~~~##### \n" +
                        "   SheepBump version " + pdf.getVersion() + " is now running!\n" +
                        "\n\n" + "#####~~~~~~ by" + ChatColor.WHITE +" i3ick " + ChatColor.GRAY +"~~~~~~#####"

        );


    }
}
