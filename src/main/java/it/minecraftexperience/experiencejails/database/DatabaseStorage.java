package it.minecraftexperience.experiencejails.database;

import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.modules.JailedPlayer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

import static it.minecraftexperience.experiencejails.database.DatabaseFactory.getConnection;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.pushJail;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.pushPlayer;

/**
 * @author IllegalNatrium
 * Class for database operations
 */
public class DatabaseStorage {

    public static void initializeJails() throws SQLException {
        Statement statement = getConnection().createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM JAILS");
        while(set.next()) {
            pushJail(new Jail(set.getString("jailname"),
                    set.getDouble("x"),
                    set.getDouble("y"),
                    set.getDouble("z"),
                    set.getString("world")));
        }
    }

    public static void initializeJailedPlayers() throws SQLException {
        Statement statement = getConnection().createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM JAILED_PLAYERS");
        while(set.next()) {
            pushPlayer(new JailedPlayer(
                    set.getString("playerName"),
                    set.getString("jailName"),
                    set.getBoolean("isJailed"),
                    set.getString("reason"),
                    Date.from(Instant.parse(set.getString("dateStart"))),
                    Date.from(Instant.parse(set.getString("dateFinish")))
            ));
        }
    }

}
