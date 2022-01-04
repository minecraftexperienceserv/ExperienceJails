package it.minecraftexperience.experiencejails.commands.tabCompleters;

import it.minecraftexperience.experiencejails.commands.JailHandler;
import it.minecraftexperience.experiencejails.commands.JailSubImplementation;
import it.minecraftexperience.experiencejails.modules.Jail;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.minecraftexperience.experiencejails.utils.GlobalVariables.getJails;
import static java.util.List.of;

/**
 * @author IllegalNatrium
 */
public class CoreTabCompleter implements TabCompleter {

    Plugin _plugin = null;

    public CoreTabCompleter(Plugin pl) {
        this._plugin = pl;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> c = new ArrayList<>();
            for(JailSubImplementation implementation : new JailHandler(_plugin).implementations) {
                c.add(implementation.getCommandName());
            }
            return c;
        }
        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("delete")) {
                List<String> mds = new ArrayList<>();
                for(Jail m : getJails()) {
                    mds.add(m.getName());
                }
                return mds;
            } else if (args[0].equalsIgnoreCase("player")) {
                List<String> mds = new ArrayList<>();
                for(Player m : Bukkit.getServer().getOnlinePlayers()) {
                    mds.add(m.getName());
                }
                return mds;
            }
            else if (args[0].equalsIgnoreCase("teleport")) {
                List<String> mds = new ArrayList<>();
                for(Jail m : getJails()) {
                    mds.add(m.getName());
                }
                return mds;
            }
            else if(args[0].equalsIgnoreCase("list")) {
                return new ArrayList<>(of("jails","players"));
            }
        }
        if(args.length == 3) {
            if(args[0].equalsIgnoreCase("player")) {
                List<String> jails = new ArrayList<>();
                for(Jail jail : getJails()) {
                    jails.add(jail.getName());
                }
                return jails;
            }
            else if(args[0].equalsIgnoreCase("list")) {
                return new ArrayList<>(of("players","jails"));
            }
        }
        if(args.length == 4) {
            if(args[0].equalsIgnoreCase("player")) {
                return new ArrayList<>(of("3h","3m","3d"));
            }
        }
        if(args.length == 5) {
            if(args[0].equalsIgnoreCase("player")) {
                return new ArrayList<>(of("<reason>"));
            }
        }
        return new ArrayList<>();
    }
}
