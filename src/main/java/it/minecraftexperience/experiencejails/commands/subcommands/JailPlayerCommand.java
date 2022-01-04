package it.minecraftexperience.experiencejails.commands.subcommands;

import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.modules.JailedPlayer;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static it.minecraftexperience.experiencejails.database.DatabaseFactory.getConnection;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getJails;
import static it.minecraftexperience.experiencejails.utils.GlobalVariables.pushPlayer;

/**
 * @author IllegalNatrium
 */
public class JailPlayerCommand extends JailSubImplementation {

    @Override
    public String getCommandName() {
        return "player";
    }

    @Override
    public String requiresPermission() {
        return "experiencejails.jail";
    }

    @Override
    public void throwCommand(CommandSender sender, String[] args, PluginLogger logger, Configuration config, Plugin plugin) {
        if(sender instanceof Player player) {
            // Argument control
            if(args.length > 1) {
                Player plr = Bukkit.getServer().getOfflinePlayerIfCached(args[1]).getPlayer();
                String jailName = args[2];
                String dateFinish = args[3];
                List<String> listArgs = Arrays.asList(args).subList(0,args.length);
                String reason = String.join(" ", listArgs.subList(4,listArgs.size()));
                if(jailName == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noJail")));
                    return;
                }
                if(plr != null) {
                    if(plr.isOnline()) {
                        if(reason == null) {
                            executeJail(player,plr,jailName,"No reason specified",dateFinish);
                            return;
                        }
                        executeJail(player,plr,jailName,reason,dateFinish);
                        return;
                    } else {
                        if(reason == null) {
                            executeJail(player,plr,jailName,"No reason specified",dateFinish);
                            return;
                        }
                        executeJail(player,plr,jailName,reason,dateFinish);
                        return;
                    }
                }
                else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
                    return;
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("defaultMessages.noArgs")));
                return;
            }
        }
    }

    boolean isOnline(Player plr) {
        return plr.isOnline();
    }

    void executeJail(Player player, Player plr, String jailName, String reason, String dateFinish) {
        // Execute if player is online
        if(isOnline(plr)) {
            for(Jail j : getJails()) {
                if(j.getName().equalsIgnoreCase(jailName)) {
                    Jail jail = new Jail(j.getName(),j.getPositionX(),j.getPositionY(),j.getPositionZ(),j.getWorld());

                    player.sendMessage(String.format("%s %s %s %s %s",
                            jail.getName(),
                            jail.getPositionX(),
                            jail.getPositionY(),
                            jail.getPositionZ(),
                            jail.getWorld()
                    ));

                    Location location = Bukkit.getServer().getWorld(jail.getWorld()).getSpawnLocation();
                    location.setX(jail.getPositionX());
                    location.setY(jail.getPositionY());
                    location.setZ(jail.getPositionZ());
                    plr.teleport(location);

                    JailedPlayer jp = new JailedPlayer(
                            plr.getName(),
                            j.getName(),
                            true,
                            reason,
                            Date.from(Instant.now()),
                            setDateFinish(Date.from(Instant.now()),dateFinish)
                    );

                    pushPlayer(jp);
                    insert(plr,jailName,dateFinish,reason);
                    return;
                }
            }
            return;
        }
        else {
            for(Jail j : getJails()) {
                if(j.getName().equalsIgnoreCase(jailName)) {
                    JailedPlayer jp = new JailedPlayer(
                            plr.getName(),
                            j.getName(),
                            true,
                            "",
                            Date.from(Instant.now()),
                            setDateFinish(Date.from(Instant.now()),dateFinish)
                    );
                    pushPlayer(jp);
                    insert(plr,jailName,dateFinish,reason);
                    return;
                }
            }
        }
    }

    // Database insertion
    void insert(Player target, String jailName, String finishDate, String reason) {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO JAILED_PLAYERS VALUES (?,?,?,?,?,?);")) {
            if(reason == null) {
                String plrName = target.getName();
                Date start = Date.from(Instant.now());
                String finish = setDateFinish(start,finishDate).toString();

                statement.setString(1,plrName);
                statement.setString(2,jailName);
                statement.setBoolean(3,true);
                statement.setString(4,"No reason Specified");
                statement.setString(5,start.toString());
                statement.setString(6,finish);
                statement.execute();
                statement.close();
                return;
            }
            else {
                String plrName = target.getName();
                Date start = Date.from(Instant.now());
                String finish = setDateFinish(start,finishDate).toString();

                statement.setString(1,plrName);
                statement.setString(2,jailName);
                statement.setBoolean(3,true);
                statement.setString(4,reason);
                statement.setString(5,start.toString());
                statement.setString(6,finish);
                statement.execute();
                statement.close();
                return;
            }
        } catch (SQLException e) {
            PluginLogger.log("INFO",e.getLocalizedMessage());
        }
    }

    Date setDateFinish(Date start, String s) {
        char[] chars = s.toCharArray();
        Instant instant = start.toInstant();
        for(char c : chars) {
            if(c == 'd') {
                instant = instant.plus(Long.parseLong(s.replaceAll("d","")),ChronoUnit.DAYS);
            }
            else if(c == 'h') {
                instant = instant.plus(Long.parseLong(s.replaceAll("h","")),ChronoUnit.HOURS);
            }
            else if(c == 'm') {
                instant = instant.plus(Long.parseLong(s.replaceAll("m","")),ChronoUnit.MINUTES);
            }
        }
        if(Date.from(instant).getTime() < Date.from(Instant.now()).getTime()) {
            return Date.from(instant.plus(3,ChronoUnit.DAYS));
        }
        return Date.from(instant);
    }


}
