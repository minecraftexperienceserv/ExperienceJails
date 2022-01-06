package it.minecraftexperience.experiencejails;

import it.minecraftexperience.experiencejails.commands.managers.CoreManager;
import it.minecraftexperience.experiencejails.storage.SQLInitializer;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    SQLInitializer initializer = new SQLInitializer(this);

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
        initializeClasses();

        getCommand("jcore").setExecutor(new CoreManager(this,initializer));

    }

    @SneakyThrows
    private void initializeClasses() {
        initializer.run();

    }

    @SneakyThrows
    @Override
    public void onDisable() {
        if(initializer.getConnection() != null) {
            initializer.getConnection().close();
        }
    }

}
