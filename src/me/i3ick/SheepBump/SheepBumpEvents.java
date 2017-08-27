package me.i3ick.SheepBump;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import sun.plugin2.main.server.Plugin;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Karlo on 8/2/2016.
 */
public class SheepBumpEvents implements Listener {

    private SheepBumpMain plugin;
    private SBcontroller gameController;

    public SheepBumpEvents(SheepBumpMain PassPlug, SBcontroller PassPlug2){
        this.plugin = PassPlug;
        this.gameController = PassPlug2;
    }

    public HashMap<Integer, Color> sheepMap = new HashMap<Integer, Color>();

    @EventHandler
    public void pDamage(EntityDamageByEntityEvent e) {
        Entity victim_entity = e.getEntity();
        Entity damager_entity = e.getDamager();


        if (victim_entity instanceof Sheep) {

            if (damager_entity instanceof Player || damager_entity instanceof Wolf) {
                Sheep victim = (Sheep) victim_entity;
                if(damager_entity instanceof Wolf){
                    Wolf damager = (Wolf) damager_entity;
                }else {
                    Player damager = (Player) damager_entity;
                    if(!damager.hasPermission("sheepbump.bump")){
                        return;
                    }

                    if(damager.hasPermission("sheepbump.tnt")){
                        if(victim.isSheared()){
                            Location loc = victim.getLocation();
                            TNTPrimed tnt = (TNTPrimed) damager_entity.getWorld().spawn(loc, TNTPrimed.class);
                        }
                    }

                }

                if(plugin.getConfig().getBoolean("Preservewool")){
                    if(!(sheepMap.containsKey(victim.getEntityId()))){
                        victim.getColor();
                        sheepMap.put(victim.getEntityId(), victim.getColor().getColor());
                        gameController.saveHashMap(sheepMap);
                    }
                }

                Random rand = new Random();
                final DyeColor[] woolArray = {
                        DyeColor.BROWN, DyeColor.BLACK, DyeColor.BLUE, DyeColor.CYAN, DyeColor.GRAY,
                        DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIME, DyeColor.MAGENTA, DyeColor.ORANGE,
                        DyeColor.PURPLE, DyeColor.RED, DyeColor.SILVER, DyeColor.WHITE, DyeColor.YELLOW
                };
                final int woolNumber = woolArray.length;

                victim.setColor(woolArray[rand.nextInt(woolNumber)]);


            }
        }
    }

    @EventHandler
         public void eDeath(EntityDeathEvent e) {
        Entity victim_entity = e.getEntity();
        Entity damager_entity = e.getEntity().getKiller();
        int victimId = victim_entity.getEntityId();

        if(!(sheepMap.containsKey(victimId))) {
            return;
        }

        if (victim_entity instanceof Sheep) {
            if (damager_entity instanceof Player) {
                Sheep victim = (Sheep) victim_entity;

                Player damager = (Player) damager_entity;
                if (!damager.hasPermission("sheepbump.bump")) {
                    return;
                }

                if (damager.hasPermission("sheepbump.tnt")) {
                    if (victim.isSheared()) {
                        Location loc = victim.getLocation();
                        TNTPrimed tnt = (TNTPrimed) damager_entity.getWorld().spawn(loc, TNTPrimed.class);
                    }
                }

                if(plugin.getConfig().getBoolean("Preservewool")){
                    int size = e.getDrops().size();
                    e.getDrops().clear();
                    Color col = sheepMap.get(victimId);
                    DyeColor dyeColor = DyeColor.getByColor(col);
                    ItemStack wool = new ItemStack(Material.WOOL, size, dyeColor.getData());
                    e.getDrops().add(wool);
                }

            }
        }
    }

    @EventHandler
    public void eShear(PlayerShearEntityEvent e) {
        Entity victim_entity = e.getEntity();
        Entity damager_entity = e.getPlayer();
        int victimId = victim_entity.getEntityId();

        if(!(sheepMap.containsKey(victimId))) {
            return;
        }

        if (victim_entity instanceof Sheep) {
            if (damager_entity instanceof Player) {
                Sheep victim = (Sheep) victim_entity;

                Player damager = (Player) damager_entity;
                if (!damager.hasPermission("sheepbump.bump")) {
                    return;
                }


                if(plugin.getConfig().getBoolean("Preservewool")){
                    e.setCancelled(true);
                    Random rand = new Random();
                    int size = rand.nextInt(4) + 1;
                    Color col = sheepMap.get(victimId);
                    DyeColor dyeColor = DyeColor.getByColor(col);
                    ItemStack wool = new ItemStack(Material.WOOL, size, dyeColor.getData());
                    World world = (World) victim_entity.getLocation().getWorld();
                    ((Sheep) victim_entity).setSheared(true);
                    world.dropItem(victim_entity.getLocation(), wool);
                }

            }
        }
    }

}
