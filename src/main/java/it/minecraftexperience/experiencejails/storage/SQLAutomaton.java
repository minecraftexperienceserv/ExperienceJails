package it.minecraftexperience.experiencejails.storage;

import it.minecraftexperience.experiencejails.storage.SQLQueries;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAutomaton extends Thread {

    private Plugin plugin;
    private final Connection connection;

    public SQLAutomaton(Plugin l, Connection conn) {
        this.plugin = l;
        this.connection = conn;
    }

    @Override
    public void run() {
        try {
            SQLQueries data = new SQLQueries();
            Configuration config = plugin.getConfig();
            // Database Creation
            connection.createStatement()
                    .execute(data.getCREATE_DATABASE().replace("?",config.getString("DATABASE")));
            connection.createStatement()
                    .execute("USE ?".replace("?",config.getString("DATABASE")));
            connection.createStatement()
                    .execute(data.getCREATE_JAILS_TABLE());
            connection.createStatement()
                    .execute(data.getCREATE_JAILED_TABLE());
        } catch (SQLException e) {
            plugin.getLogger().severe(e.getLocalizedMessage());
        }
        super.run();
    }
}
