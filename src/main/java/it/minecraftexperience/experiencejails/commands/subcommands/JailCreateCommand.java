package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.database.DatabaseFactory;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getJails;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.pushJail;

/**
 * @author IllegalNatrium
 */
public class JailCreateCommand extends JailSubImplementation {
    @Override
    public String getCommandName() {
        return "create";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.create";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            if(args.length > 1) {
                if(getJails().stream().map(Jail::getName).anyMatch(f->f.equalsIgnoreCase(args[1]))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.alreadyCreated")));
                    return;
                }
                String name = args[1].toLowerCase();

                if(name == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
                    return;
                }

                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                String world = player.getLocation().getWorld().getName();
                pushJail(new Jail(name,x,y,z,world));

                try (PreparedStatement statement = DatabaseFactory.getConnection().prepareStatement("INSERT INTO JAILS VALUES (?,?,?,?,?)")) {

                    statement.setString(1,name);
                    statement.setDouble(2,x);
                    statement.setDouble(3,y);
                    statement.setDouble(4,z);
                    statement.setString(5,world);

                    statement.execute();

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.jailCreated").replace("{0}",name)));

                } catch (SQLException e) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.exceptionError")));
                    PluginLogger.log("ERROR",e.getLocalizedMessage());
                }
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
            }
        }
        if(sender instanceof ConsoleCommandSender) {
            PluginLogger.log("ERROR","Only Player!");
        }
    }
}
