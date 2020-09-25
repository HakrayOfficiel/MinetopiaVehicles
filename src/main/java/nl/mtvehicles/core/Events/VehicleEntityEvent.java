package nl.mtvehicles.core.Events;

import nl.mtvehicles.core.Infrastructure.Helpers.TextUtils;
import nl.mtvehicles.core.Infrastructure.Models.ConfigUtils;
import nl.mtvehicles.core.Infrastructure.Models.Vehicle;
import nl.mtvehicles.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VehicleEntityEvent implements Listener {
    public static HashMap<String, Double> speed = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(EntityDamageByEntityEvent event) {
        final Entity a = event.getEntity();
        final Player p = (Player) event.getDamager();
        if (a.getCustomName() == null) {
            return;
        }
        if (p.isSneaking()) {
            if (a.getCustomName().contains("MTVEHICLES_MAINSEAT_")) {
                kofferbak(p, a.getCustomName().replace("MTVEHICLES_MAINSEAT_",""));
                event.setCancelled(true);
            }
            if (a.getCustomName().contains("MTVEHICLES_MAIN_")) {
                kofferbak(p, a.getCustomName().replace("MTVEHICLES_MAIN_",""));
                event.setCancelled(true);
            }
            if (a.getCustomName().contains("MTVEHICLES_SKIN_")) {
                kofferbak(p, a.getCustomName().replace("MTVEHICLES_SKIN_",""));
                event.setCancelled(true);
            }
            return;
        }
    }

    public void kofferbak(Player p, String ken){
        if (Vehicle.getByPlate(ken) == null){
            p.sendMessage(TextUtils.colorize(Main.messagesConfig.getMessage("vehicleNotFound")));
            return;
        }
        if (Vehicle.getByPlate(ken).getOwner().equals(p.getUniqueId().toString()) || Vehicle.canRide(p, ken) == true || p.hasPermission("mtvehicles.kofferbak")) {
            if (Main.vehicleDataConfig.getConfig().getBoolean("vehicle."+ken+".kofferbak") == true){
                Inventory inv = Bukkit.createInventory(null, Main.vehicleDataConfig.getConfig().getInt("vehicle."+ken+".kofferbakRows")*9, "Kofferbak");
                p.openInventory(inv);
                System.out.println("test");
            } else {
                System.out.println("test2");
            }

        } else {
            p.sendMessage(TextUtils.colorize(Main.messagesConfig.getMessage("vehicleNoRiderKofferbak").replace("%p%", Bukkit.getOfflinePlayer(UUID.fromString(Vehicle.getByPlate(ken).getOwner().toString())).getName())));

        }

    }
}