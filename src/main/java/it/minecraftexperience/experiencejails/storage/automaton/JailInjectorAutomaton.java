package it.minecraftexperience.experiencejails.storage.automaton;

import it.minecraftexperience.experiencejails.storage.SQLQueries;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JailInjectorAutomaton {

    @SneakyThrows
    public JailInjectorAutomaton(PlayerObjectInterface i, Connection connection) {
        try (PreparedStatement s = connection.prepareStatement(new SQLQueries().getINSERT_JAILED())) {
            s.setString(1,i.getJail().getJail());
            s.setString(2,i.getTargetUuid().toString());
            s.setString(3,i.getReason());
            s.setString(4,i.getStaffObjectUuid().toString());
            s.setString(5,i.getStart().toString());
            s.setString(6,i.getFinish().toString());
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
