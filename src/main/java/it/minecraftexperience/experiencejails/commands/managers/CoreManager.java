package it.minecraftexperience.experiencejails.commands.managers;

import it.minecraftexperience.experiencejails.commands.ListCommand;
import it.minecraftexperience.experiencejails.commands.jailmanagement.JailCreate;
import it.minecraftexperience.experiencejails.commands.jailmanagement.JailTeleport;
import it.minecraftexperience.experiencejails.commands.player.JailPlayer;
import it.minecraftexperience.experiencejails.storage.SQLInitializer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CoreManager implements CommandExecutor {

    private ArrayList<CoreInterface> in = new ArrayList<>();

    Plugin plugin;
    Configuration configuration;
    SQLInitializer initializer;

    public CoreManager(Plugin pl, SQLInitializer initializer) {
        this.plugin = pl;
        this.configuration = pl.getConfig();
        this.initializer = initializer;
        createCommand(new ListCommand());
        createCommand(new JailTeleport());
        createCommand(new JailCreate());
        createCommand(new JailPlayer());
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player plr) {
            for(CoreInterface i : in) {
                if(args[0].equalsIgnoreCase(i.getName())) {
                    if (plr.hasPermission(i.getPermission())) {
                        i.execute(plr, args, plugin, configuration, initializer.getConnection());
                    }
                    else {
                        plr.sendMessage(ChatColor.translateAlternateColorCodes('&',configuration.getString("defaultMessages.denied")));
                    }
                }
            }
        }
        return true;
    }

    private boolean createCommand(CoreInterface i) {
        if(in.stream().map(CoreInterface::getName).anyMatch(c -> i.getName().equalsIgnoreCase(c))) return false;
        in.add(i);
        return true;
    }

}
