package it.minecraftexperience.experiencejails.commands;

import it.minecraftexperience.experiencejails.commands.managers.CoreInterface;
import it.minecraftexperience.experiencejails.storage.variables.JailObjectVariables;
import it.minecraftexperience.experiencejails.storage.variables.PlayerObjectVariables;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ListCommand extends CoreInterface {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "experiencejails.list";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public void execute(Player author, String[] args, Plugin plugin, Configuration config, Connection connection) {
        StringBuilder jails = new StringBuilder();
        StringBuilder jailed = new StringBuilder();

        JailObjectVariables.forEach(f -> {
            String jail = f.getJail();
            double x = f.getLocation().getX();
            double y = f.getLocation().getY();
            double z = f.getLocation().getZ();
            double yaw = f.getLocation().getYaw();
            double pitch = f.getLocation().getPitch();
            World world = f.getWorld();
            String format = String.format("%s | %s | %s | %s | %s | %s | %s ", jail, x, y, z, yaw, pitch, world.getName());
            jails.append(format);
        });

        PlayerObjectVariables.forEach(f -> {
            String jail = f.getJail().getJail();
            String reason = f.getReason();
            UUID uuid = f.getTargetUuid();
            UUID staffObjectUuid = f.getStaffObjectUuid();
            Date start = f.getStart();
            Date finish = f.getFinish();
            SimpleDateFormat pattern = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            String format = String.format(
                    "%s | %s | %s | %s | %s | %s",
                    jail,
                    reason,
                    uuid.toString(),
                    staffObjectUuid.toString(),
                    pattern.format(start),
                    pattern.format(finish)
            );
            jailed.append(format);
        });
        author.sendMessage(jails.toString());
        author.sendMessage(jailed.toString());
    }
}
