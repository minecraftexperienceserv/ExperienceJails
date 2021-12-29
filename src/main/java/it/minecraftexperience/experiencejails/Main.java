package it.minecraftexperience.experiencejails;

import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Logging
        PluginLogger.log("DISABLED", "\n\n"+
                "  ______                      _                          _       _ _     \n"+
                " |  ____|                    (_)                        | |     (_) |    \n"+
                " | |__  __  ___ __   ___ _ __ _  ___ _ __   ___ ___     | | __ _ _| |___ \n"+
                " |  __| \\ \\/ / '_ \\ / _ \\ '__| |/ _ \\ '_ \\ / __/ _ \\_   | |/ _` | | / __|\n"+
                " | |____ >  <| |_) |  __/ |  | |  __/ | | | (_|  __/ |__| | (_| | | \\__ \\\n"+
                " |______/_/\\_\\ .__/ \\___|_|  |_|\\___|_| |_|\\___\\___|\\____/ \\__,_|_|_|___/\n"+
                "             | |                                                         \n"+
                "             |_|                                                         "+
                "\n\n"
        );
        PluginLogger.log("INFO", "Plugin version: v" + this.getDescription().getVersion());
        PluginLogger.log("INFO", "Jails plugin for MinecraftExperience | Kingdoms");
        PluginLogger.log("INFO", "Made by tommaso.benatti#0001 : Sodio#2005");
        PluginLogger.log("INFO", "Plugin successfully loaded!");

        // config
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
