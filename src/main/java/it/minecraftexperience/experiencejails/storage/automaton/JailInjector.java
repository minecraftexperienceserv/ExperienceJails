package it.minecraftexperience.experiencejails.storage.automaton;

import it.minecraftexperience.experiencejails.storage.SQLQueries;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JailInjector {

    @SneakyThrows
    public JailInjector(PlayerObjectInterface i, Connection connection) {
        try (PreparedStatement s = connection.prepareStatement(new SQLQueries().getINSERT_JAILED())) {
            s.setString(1,i.getJail().getJail());
            s.setString(2,i.getTargetUuid().toString());
            s.setString(3,i.getReason());
            s.setString(4,i.getStaffObjectUuid().toString());
            s.setLong(5,i.getStart().getTime());
            s.setLong(6,i.getFinish().getTime());
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
