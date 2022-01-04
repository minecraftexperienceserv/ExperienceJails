package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.modules.JailedPlayer;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.*;

/**
 * @author IllegalNatrium
 */
public class ListJailCommand extends JailSubImplementation {
    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.list";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            if(args.length > 1) {
                switch(args[1].toLowerCase()) {
                    case "jails": {
                        for (Jail jail : getJails()) {
                            String s = String.format("%s | %s | %s | %s | %s", jail.getName(), jail.getPositionX(), jail.getPositionY(), jail.getPositionZ(), jail.getWorld());
                            player.sendMessage(s);
                        }
                        break;
                    }
                    case "players": {
                        StringBuilder s = new StringBuilder();
                        for(JailedPlayer jail : getJailedPlayers()) {
                            s.append(String.format("%s | %s | %s | %s | %s | %s |",
                                            jail.getJailName(),
                                            jail.getPlayerName(),
                                            jail.getReason(),
                                            jail.getStartDate(),
                                            jail.getFinishDate(),
                                            jail.isJailed())
                                    ).append("\n");
                        }
                        player.sendMessage(ChatColor.AQUA + s.toString());
                        break;
                    }
                }
            }
        }
        if(sender instanceof ConsoleCommandSender) {
            if(args.length > 1) {
                switch(args[1].toLowerCase()) {
                    case "jails": {
                        for (Jail jail : getJails()) {
                            String s = String.format("%s | %s | %s | %s | %s", jail.getName(), jail.getPositionX(), jail.getPositionY(), jail.getPositionZ(), jail.getWorld());
                            PluginLogger.log("INFO",s);
                        }
                        break;
                    }
                    case "players": {
                        StringBuilder s = new StringBuilder();
                        for(JailedPlayer jail : getJailedPlayers()) {
                            s.append(String.format("%s | %s | %s | %s | %s | %s |",
                                    jail.getJailName(),
                                    jail.getPlayerName(),
                                    jail.getReason(),
                                    jail.getStartDate(),
                                    jail.getFinishDate(),
                                    jail.isJailed())
                            ).append("\n");
                        }
                        PluginLogger.log("INFO",s.toString());
                        break;
                    }
                }
            }
        }
    }
}
