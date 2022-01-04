package it.minecraftexperience.experiencejails.listeners;

import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.modules.JailedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.sql.Date;
import java.time.Instant;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.*;

public class PlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        // Iterates all Jailed Players
        for(JailedPlayer j : getJailedPlayers()) {
            // Iterates all Jails
            for(Jail ja : getJails()) {
                // Checks if Jailed Player is in list
                if(j.getPlayerName().equalsIgnoreCase(event.getPlayer().getName())) {
                    // Checks if JailPlayer's Jail is equals to a Jail in Jail List
                    if(j.getJailName().equalsIgnoreCase(ja.getName())) {
                        // Checks if Date is out
                        if(Date.from(Instant.now()).after(j.getFinishDate())) {
                            popPlayer(event.getPlayer().getName());
                            return;
                        }
                        // Teleports the player to the jail
                        Jail jail = new Jail(ja.getName(),ja.getPositionX(),ja.getPositionY(),ja.getPositionZ(),ja.getWorld());
                        Location location = Bukkit.getServer().getWorld(jail.getWorld()).getSpawnLocation();
                        location.setX(jail.getPositionX());
                        location.setY(jail.getPositionY());
                        location.setZ(jail.getPositionZ());
                        event.getPlayer().teleport(location);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        for(JailedPlayer j : getJailedPlayers()) {
            // Iterates all Jails
            for(Jail ja : getJails()) {
                // Checks if Jailed Player is in list
                if(j.getPlayerName().equalsIgnoreCase(event.getPlayer().getName())) {
                    // Checks if JailPlayer's Jail is equals to a Jail in Jail List
                    if(j.getJailName().equalsIgnoreCase(ja.getName())) {
                        // Checks if Date is out
                        if(Date.from(Instant.now()).after(j.getFinishDate())) {
                            popPlayer(event.getPlayer().getName());
                            return;
                        }
                        // Teleports the player to the jail
                        Jail jail = new Jail(ja.getName(),ja.getPositionX(),ja.getPositionY(),ja.getPositionZ(),ja.getWorld());
                        Location location = Bukkit.getServer().getWorld(jail.getWorld()).getSpawnLocation();
                        location.setX(jail.getPositionX());
                        location.setY(jail.getPositionY());
                        location.setZ(jail.getPositionZ());
                        event.getPlayer().teleport(location);
                    }
                }
            }
        }
    }
}