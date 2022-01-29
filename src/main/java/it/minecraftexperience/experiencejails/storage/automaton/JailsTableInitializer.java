package it.minecraftexperience.experiencejails.storage.automaton;

import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JailsTableInitializer {

    public JailsTableInitializer(Plugin pl, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet query = statement.executeQuery("SELECT * FROM JAILS;");
            while(query.next()) {
                JailObjectInterface i = new JailObjectInterface();
                i.setJail(query.getString("jail"));
                World world = pl.getServer().getWorld(query.getString("world"));
                if(world == null) {
                    pl.getLogger().severe(String.format("%s does not exist",query.getString("world")));
                    return;
                }
                double x = query.getDouble("x");
                double y = query.getDouble("y");
                double z = query.getDouble("z");
                float yaw = (float) query.getDouble("yaw");
                float pitch = (float) query.getDouble("pitch");
                Location location = new Location(world,x,y,z,yaw,pitch);
                i.setLocation(location);
                i.setWorld(world);
                JailObjectVariables.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
