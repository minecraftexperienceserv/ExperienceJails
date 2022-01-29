package it.minecraftexperience.experiencejails.storage;

import it.minecraftexperience.experiencejails.storage.automaton.JailsTableInitializer;
import it.minecraftexperience.experiencejails.storage.automaton.PlayersTableInitializer;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLInitializer {

    private Connection connection;
    private final Plugin plugin;

    public SQLInitializer(Plugin l) {
        this.plugin = l;
    }

    public void run() {
        try {
            String HOST = "jdbc:mysql://" + get("ADDRESS") + ":" + get("PORT");
            String USERNAME = get("USERNAME");
            String PASSWORD = get("PASSWORD");
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);
            new SQLAutomaton(plugin,connection);
            new JailsTableInitializer(plugin,connection);
            new PlayersTableInitializer(plugin,connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public Connection getConnection() {
        if(connection == null) {
            this.run();
            return connection;
        }
        return connection;
    }

    private String get(String key) {
        return plugin.getConfig().getString(key);
    }

}
