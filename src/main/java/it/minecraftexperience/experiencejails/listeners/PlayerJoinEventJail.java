package it.minecraftexperience.experiencejails.listeners;

import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import it.minecraftexperience.experiencejails.storage.variables.PlayerObjectVariables;
import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventJail implements Listener {

    @SneakyThrows
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(PlayerObjectVariables.getObjects().stream().anyMatch(f->f.getTargetUuid().equals(event.getPlayer().getUniqueId()))) {
            PlayerObjectInterface poj = null;
            for (PlayerObjectInterface pojo : PlayerObjectVariables.getObjects()) {
                if(pojo.getTargetUuid().equals(event.getPlayer().getUniqueId())) {
                    poj = pojo;
                }
            }
            JailObjectInterface j = poj.getJail();

            event.getPlayer().teleport(j.getLocation());
        }
    }
}
