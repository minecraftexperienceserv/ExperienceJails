package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getPluginHelp;

/**
 * @author IllegalNatrium
 */
public class HelpCommand extends JailSubImplementation {

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String requiresPermission() {
        return null;
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            player.sendMessage(ChatColor.BOLD + getPluginHelp());
        }
        if(sender instanceof ConsoleCommandSender) {
            PluginLogger.log("INFO",getPluginHelp());
        }
    }
}
