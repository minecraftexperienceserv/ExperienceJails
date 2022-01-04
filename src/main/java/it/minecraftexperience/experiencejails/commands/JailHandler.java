package it.minecraftexperience.experiencejails.commands;

import it.minecraftexperience.experiencejails.commands.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * @author IllegalNatrium
 */
public class JailHandler implements CommandExecutor {

    public ArrayList<JailSubImplementation> implementations = new ArrayList<>();

    Configuration _config = null;
    PluginLogger _logger = null;
    Plugin _plugin = null;

    public JailHandler(Plugin pl) {
        this._config = pl.getConfig();
        this._plugin = pl;
        this._logger = new PluginLogger();

        this.registerCommand(new JailCreateCommand());
        this.registerCommand(new ListJailCommand());
        this.registerCommand(new ReloadCommand());
        this.registerCommand(new TeleportJailCommand());
        this.registerCommand(new JailDeleteCommand());
        this.registerCommand(new HelpCommand());
        this.registerCommand(new JailPlayerCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length > 0) {
            for (int i = 0; i < implementations.size(); i++) {
                if(strings[0].equalsIgnoreCase(implementations.get(i).getCommandName())) {
                    if(commandSender.hasPermission(implementations.get(i).requiresPermission())) {
                        implementations.get(i).throwCommand(commandSender, strings, _logger, _config,_plugin);
                    }
                    else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',_config.getString("defaultMessages.denied")));
                    }
                }
            }
        }
        else {
            commandSender.sendMessage(" ");
        }
        return true;
    }

    private boolean registerCommand(JailSubImplementation impl) {
        if(impl.getCommandName().contains(" "))
            Bukkit.getServer().getLogger().log(Level.SEVERE, String.format("%s contains spaces", impl.getCommandName()));
        if(this.implementations.stream().map(JailSubImplementation::getCommandName).anyMatch(c -> impl.getCommandName().equalsIgnoreCase(c))) return false;
        this.implementations.add(impl);
        return true;
    }
}
