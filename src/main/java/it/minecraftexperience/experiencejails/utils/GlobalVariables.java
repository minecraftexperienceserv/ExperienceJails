package it.minecraftexperience.experiencejails.utils;

import it.minecraftexperience.experiencejails.commands.JailHandler;
import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.modules.JailedPlayer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 Class for Global Variables
 @author IllegalNatrium
 */
public class GlobalVariables {

    static Plugin _plugin = null;

    public GlobalVariables(Plugin pl) {
        _plugin = pl;
    }

    /**
     * Variables for Jail Module
     */
    private static List<Jail> jails = new ArrayList<>();

    public static List<Jail> getJails() {
        return jails;
    }

    public static void pushJail(Jail jail) {
        jails.add(jail);
    }

    public static void popJail(String m) {
        jails.removeIf(j -> j.getName().equalsIgnoreCase(m));
    }

    // Variables for Jailed Player Module

    public static List<JailedPlayer> jailedPlayers = new ArrayList<>();

    public static List<JailedPlayer> getJailedPlayers() {
        return jailedPlayers;
    }

    public static void popPlayer(String playerName) {
        jailedPlayers.removeIf(j->j.getPlayerName().equalsIgnoreCase(playerName));
    }

    public static void pushPlayer(JailedPlayer jp) {
        jailedPlayers.add(jp);
    }

    // Help TextBox
    public static String getPluginHelp() {
        StringBuilder str = new StringBuilder();
        for (JailSubImplementation implementation : new JailHandler(_plugin).implementations) {
            if(implementation.requiresPermission() == null) {
                str.append(implementation.getCommandName())
                        .append(" | ");
                continue;
            }
            str.append(implementation.getCommandName())
                    .append(" | ")
                    .append(String.format("Requires %s",implementation.requiresPermission()));
        }
        return String.format("""

                ExperienceJails Help page
                
                Commands:
                %s

                """,str);
    }


}
