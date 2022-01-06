package it.minecraftexperience.experiencejails;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();



        getLogger().info("""


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
        getLogger().info("Plugin version: v" + this.getDescription().getVersion());
        getLogger().info("Jails plugin for MinecraftExperience | Kingdoms");
        getLogger().info("Made by tommaso.benatti#0001 : Sodio#2005");
        getLogger().info("Plugin successfully loaded!");
    }

    @Override
    public void onDisable() {

    }

}
