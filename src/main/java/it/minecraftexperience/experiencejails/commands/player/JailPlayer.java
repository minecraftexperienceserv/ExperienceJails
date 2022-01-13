package it.minecraftexperience.experiencejails.commands.player;

import it.minecraftexperience.experiencejails.commands.managers.CoreInterface;
import it.minecraftexperience.experiencejails.storage.automaton.JailInjectorAutomaton;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

public class JailPlayer extends CoreInterface {

    @Override
    public String getName() {
        return "jail";
    }

    @Override
    public String getPermission() {
        return "experiencejails.jail";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public void execute(Player author, String[] args, Plugin plugin, Configuration config, Connection connection) {
        if(args.length > 0) {
            String playerName =  args[1];
            String jailName = (args[2] == null) ? null : args[2];
            if(JailObjectVariables.getObjects().stream().anyMatch(f->!f.getJail().equals(jailName))) return;
            String length = args[3];
            String reason = String.join(" ", Arrays.asList(args).subList(4,args.length));
            JailObjectInterface jail = JailObjectVariables.findByName(jailName);

            OfflinePlayer plr = Bukkit.getServer().getOfflinePlayerIfCached(playerName);

            PlayerObjectInterface player = new PlayerObjectInterface();
            player.setJail(jail);
            player.setStart(Date.from(Instant.now()));
            player.setFinish(getDate(length));
            player.setReason(reason);
            player.setStaffObjectUuid(author.getUniqueId());
            player.setTargetUuid(plr.getUniqueId());

            if(!plr.isOnline()) {
                new JailInjectorAutomaton(player,connection);
                return;
            }
            else {
                new JailInjectorAutomaton(player,connection);
                plr.getPlayer().teleport(jail != null ? jail.getLocation() : null);
                return;
            }
        }
        else {
            noArgs(author,config);
        }
    }

    Date getDate(String s) {
        char[] chars = s.toCharArray();
        for(char c : chars) {
            if(c == 's') {
                return Date.from(Instant.now().plus(Long.parseLong(s.replaceAll("s","")), ChronoUnit.SECONDS));
            }
            else if(c == 'm') {
                return Date.from(Instant.now().plus(Long.parseLong(s.replaceAll("m","")), ChronoUnit.MINUTES));
            }
            else if(c == 'h') {
                return Date.from(Instant.now().plus(Long.parseLong(s.replaceAll("h","")), ChronoUnit.HOURS));
            }
            else if(c == 'd') {
                return Date.from(Instant.now().plus(Long.parseLong(s.replaceAll("d","")), ChronoUnit.DAYS));
            }
            else if(c == 'w') {
                return Date.from(Instant.now().plus(Long.parseLong(s.replaceAll("w","")), ChronoUnit.WEEKS));
            }
        }
        return null;
    }

}
