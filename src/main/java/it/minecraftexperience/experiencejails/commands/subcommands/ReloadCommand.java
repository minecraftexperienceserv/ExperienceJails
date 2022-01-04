package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static it.minecraftexperience.experiencejails.utils.PluginLogger.log;

/**
 * @author IllegalNatrium
 */
public class ReloadCommand extends JailSubImplementation {
    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.reload";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            plugin.reloadConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.configReloaded")));
        }
        if(sender instanceof ConsoleCommandSender) {
            plugin.reloadConfig();
            log("SUCCESS","Config reloaded");
        }
    }
}
