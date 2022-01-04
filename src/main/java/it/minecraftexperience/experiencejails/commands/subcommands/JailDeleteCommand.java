package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
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

import static it.minecraftexperience.experiencejails.database.DatabaseFactory.getConnection;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getJails;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.popJail;

/**
 * @author IllegalNatrium
 */
public class JailDeleteCommand extends JailSubImplementation {
    @Override
    public String getCommandName() {
        return "delete";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.delete";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            if(args.length > 1) {
                if(getJails().stream().map(Jail::getName).noneMatch(f->f.equalsIgnoreCase(args[1]))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noJail")));
                    return;
                }

                // Removes jail from the list
                popJail(args[1]);

                // Removes jail from database
                try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM JAILS WHERE jailname = ?")) {
                    statement.setString(1,args[1]);
                    statement.execute();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.jailDeleted").replace("{0}",args[1])));
                } catch (SQLException e) {
                    PluginLogger.log("ERROR",e.getLocalizedMessage());
                }
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
            }
        }
        if(sender instanceof ConsoleCommandSender cls) {
            if(args.length > 0) {
                if(getJails().stream().map(Jail::getName).noneMatch(f->f.equalsIgnoreCase(args[1]))) {
                    cls.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noJail")));
                    return;
                }

                // Removes jail from the list
                popJail(args[1]);

                // Removes jail from database
                try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM JAILS WHERE jailname = ?")) {
                    statement.setString(1,args[1]);
                    statement.execute();
                    cls.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.jailDeleted").replace("{0}",args[1])));
                } catch (SQLException e) {
                    PluginLogger.log("ERROR",e.getLocalizedMessage());
                }
            }
            else {
                cls.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
            }
        }
    }


}
