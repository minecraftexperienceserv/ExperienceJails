package it.minecraftexperience.experiencejails.commands;

import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class for subs of command Jail
 * @author IllegalNatrium
 */
public abstract class JailSubImplementation {

    public abstract String getCommandName();
    public abstract String requiresPermission();
    public abstract void throwCommand(
            CommandSender sender,
            String[] args,
            PluginLogger logger,
            Configuration config,
            Plugin plugin
    );

}
