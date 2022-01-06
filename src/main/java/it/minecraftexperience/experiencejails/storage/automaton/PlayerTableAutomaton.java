package it.minecraftexperience.experiencejails.storage.automaton;

import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.PlayerObjectVariables;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

public class PlayerTableAutomaton extends Thread {

    public PlayerTableAutomaton(Plugin pl, Connection connection) {
        super(() -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet query = statement.executeQuery("SELECT * FROM JAILED;");
                while(query.next()) {
                    PlayerObjectInterface i = new PlayerObjectInterface();
                    JailObjectInterface iJ = new JailObjectInterface();
                    iJ.setJail(query.getString("jail"));
                    i.setJail(iJ);
                    i.setReason(query.getString("reason"));
                    i.setTargetUuid(UUID.nameUUIDFromBytes(query.getString("target").getBytes()));
                    i.setStaffObjectUuid(UUID.nameUUIDFromBytes(query.getString("staffer").getBytes()));
                    i.setStart(new Date(query.getLong("start")));
                    i.setFinish(new Date(query.getLong("finish")));
                    PlayerObjectVariables.add(i);
                }
            } catch (SQLException e) {
                pl.getLogger().severe(e.getLocalizedMessage());
            }
        });
    }

}
