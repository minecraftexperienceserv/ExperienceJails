package it.minecraftexperience.experiencejails.commands.jailmanagement;

import it.minecraftexperience.experiencejails.commands.managers.CoreInterface;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public class JailTeleport extends CoreInterface {
    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public String getPermission() {
        return "experiencejails.teleport";
    }

    @Override
    public String getHelp() {
        return "Teleports you to the jail location!";
    }

    @Override
    public void execute(Player author, String[] args, Plugin plugin, Configuration config, Connection connection) {
        if(args.length > 0) {
            JailObjectInterface j = JailObjectVariables.findByName(args[1].toLowerCase());
            author.teleport(j == null ? null : j.getLocation());
        }
        else {
            noArgs(author,config);
        }
    }
}
