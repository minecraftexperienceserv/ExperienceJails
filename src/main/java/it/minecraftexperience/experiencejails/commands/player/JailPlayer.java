package it.minecraftexperience.experiencejails.commands.player;

import it.minecraftexperience.experiencejails.commands.managers.CoreInterface;
import it.minecraftexperience.experiencejails.storage.automaton.JailInjector;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;
import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import it.minecraftexperience.experiencejails.storage.variables.PlayerObjectVariables;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

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
            UUID target = Bukkit.getServer().getPlayer(args[1]).getUniqueId();
            String jail = args[2].toLowerCase();
            String length = args[3];
            String reason = String.join(" ", Arrays.asList(args).subList(4,args.length));
            UUID staffer = author.getUniqueId();
            
            if(JailObjectVariables.getObjects().stream().noneMatch(f->f.getJail().equals(jail))) {
                author.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noJail")));
                return;
            }

            // Jail Object
            JailObjectInterface j = null;

            for (JailObjectInterface ja : JailObjectVariables.getObjects()) {
                if(ja.getJail().equals(jail)) {
                    j = ja;
                }
            }

            PlayerObjectInterface p = new PlayerObjectInterface();

            p.setJail(j);
            p.setStart(Date.from(Instant.now()));
            p.setFinish(getDate(length));
            p.setTargetUuid(target);
            p.setStaffObjectUuid(staffer);
            p.setReason(reason);

            if(Bukkit.getPlayer(target).isOnline()) {
                // Teleport Database
                Bukkit.getPlayer(target).teleport(j.getLocation());

                // Insert Into Database
                new JailInjector(p,connection);
                PlayerObjectVariables.add(p);
                return;
            }
            else {
                // Insert into database
                PlayerObjectVariables.add(p);
                new JailInjector(p,connection);
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
