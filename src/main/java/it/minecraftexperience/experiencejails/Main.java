package it.minecraftexperience.experiencejails;

import it.minecraftexperience.experiencejails.commands.JailHandler;
import it.minecraftexperience.experiencejails.commands.tabCompleters.CoreTabCompleter;
import it.minecraftexperience.experiencejails.database.DatabaseFactory;
import it.minecraftexperience.experiencejails.listeners.PlayerJoinEvent;
import it.minecraftexperience.experiencejails.modules.Jail;
import it.minecraftexperience.experiencejails.utils.GlobalVariables;
import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

import static it.minecraftexperience.experiencejails.database.DatabaseStorage.initializeJailedPlayers;
import static it.minecraftexperience.experiencejails.database.DatabaseStorage.initializeJails;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Logging
        PluginLogger.log("DISABLED", """


                  ______                      _                          _       _ _    \s
                 |  ____|                    (_)                        | |     (_) |   \s
                 | |__  __  ___ __   ___ _ __ _  ___ _ __   ___ ___     | | __ _ _| |___\s
                 |  __| \\ \\/ / '_ \\ / _ \\ '__| |/ _ \\ '_ \\ / __/ _ \\_   | |/ _` | | / __|
                 | |____ >  <| |_) |  __/ |  | |  __/ | | | (_|  __/ |__| | (_| | | \\__ \\
                 |______/_/\\_\\ .__/ \\___|_|  |_|\\___|_| |_|\\___\\___|\\____/ \\__,_|_|_|___/
                             | |                                                        \s
                             |_|                                                        \s

                """
        );
        PluginLogger.log("INFO", "Plugin version: v" + this.getDescription().getVersion());
        PluginLogger.log("INFO", "Jails plugin for MinecraftExperience | Kingdoms");
        PluginLogger.log("INFO", "Made by tommaso.benatti#0001 : Sodio#2005");
        PluginLogger.log("INFO", "Plugin successfully loaded!");

        // Config
        saveDefaultConfig();

        initializeIMClasses(this);

        // Commands
        this.getCommand("jail").setExecutor(new JailHandler(this));
        // Tabs
        this.getCommand("jail").setTabCompleter(new CoreTabCompleter(this));

        this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(),this);
    }

    public void initializeIMClasses(Plugin plugin) {
        new DatabaseFactory(plugin);
        new GlobalVariables(plugin);
        try {
            initializeJails();
            initializeJailedPlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            DatabaseFactory.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
