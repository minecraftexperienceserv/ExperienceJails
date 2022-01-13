package it.minecraftexperience.experiencejails.storage;

import it.minecraftexperience.experiencejails.storage.SQLQueries;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAutomaton {

    public SQLAutomaton(Plugin l, Connection connection) {
        try {
            SQLQueries data = new SQLQueries();
            Configuration config = l.getConfig();
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
            l.getLogger().severe(e.getLocalizedMessage());
        }
    }
}
