package it.minecraftexperience.experiencejails.commands.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;


public abstract class CoreInterface {

    public abstract String getName();
    public abstract String getPermission();
    public abstract String getHelp();
    public abstract void execute(Player author, String[] args, Plugin plugin, Configuration config, Connection connection);

    public void noArgs(Player author, Configuration configuration) {
        author.sendMessage(ChatColor.translateAlternateColorCodes('&',configuration.getString("defaultMessages.noArgs")));
    }

}
