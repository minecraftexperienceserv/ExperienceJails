package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getJails;

/**
 * @author IllegalNatrium
 */
public class TeleportJailCommand extends JailSubImplementation {
    @Override
    public String getCommandName() {
        return "teleport";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.teleport";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player plr) {
            if(args.length > 1) {
                for (Jail jail : getJails()) {
                    if(args[1].equalsIgnoreCase(jail.getName())) {
                        double x = jail.getPositionX();
                        double y = jail.getPositionY();
                        double z = jail.getPositionZ();
                        Location location = Bukkit.getServer().getWorld(jail.getWorld()).getSpawnLocation();
                        location.set(x,y,z);
                        plr.teleport(location);
                        return;
                    }
                }
            }
        }
        if(sender instanceof ConsoleCommandSender) {
            PluginLogger.log("ERROR","Only Player!");
            return;
        }
    }
}
