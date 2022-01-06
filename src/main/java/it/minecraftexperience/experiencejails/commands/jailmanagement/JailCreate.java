package it.minecraftexperience.experiencejails.commands.jailmanagement;

import it.minecraftexperience.experiencejails.commands.managers.CoreInterface;
import it.minecraftexperience.experiencejails.storage.SQLQueries;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JailCreate extends CoreInterface {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "experiencejails.create";
    }

    @Override
    public String getHelp() {
        return "Creates a jail!";
    }

    @SneakyThrows
    @Override
    public void execute(Player author, String[] args, Plugin plugin, Configuration configuration, Connection connection) {
        if(args.length > 0) {
            SQLQueries queries = new SQLQueries();
            JailObjectInterface i = new JailObjectInterface();
            i.setJail(args[1].toLowerCase());
            i.setLocation(author.getLocation());
            i.setWorld(author.getWorld());

            PreparedStatement statement = connection.prepareStatement(queries.getINSERT_JAIL());

            statement.setString(1,i.getJail());
            statement.setString(2,String.valueOf(i.getLocation().getBlockX()));
            statement.setString(3,String.valueOf(i.getLocation().getBlockY()));
            statement.setString(4,String.valueOf(i.getLocation().getBlockZ()));
            statement.setString(5,String.valueOf(i.getLocation().getYaw()));
            statement.setString(6,String.valueOf(i.getLocation().getPitch()));
            statement.setString(7,i.getWorld().getName());

            statement.execute();
            JailObjectVariables.add(i);
        }
        else {
            noArgs(author,configuration);
        }
    }
}
