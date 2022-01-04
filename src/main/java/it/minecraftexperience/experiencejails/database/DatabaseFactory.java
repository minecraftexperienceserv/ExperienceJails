package it.minecraftexperience.experiencejails.database;

import it.minecraftexperience.experiencejails.utils.PluginLogger;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author IllegalNatrium
 */
public class DatabaseFactory {


    // It will be instanced later
    private static Connection connection = null;

    // It will be instanced later
    static Configuration _config = null;

    public DatabaseFactory(Plugin pl) {
        _config = pl.getConfig();
        createConnection();
    }

    private static void initializeDatabase() {
        try {
            String _CREATE_DATABASE = String.format("CREATE DATABASE IF NOT EXISTS %s",_config.getString("DATABASE"));
            Statement statement = DatabaseFactory.getConnection().createStatement();
            statement.execute(_CREATE_DATABASE);
        } catch (SQLException e) {
            PluginLogger.log("ERROR",e.getLocalizedMessage());
        }
    }

    private static void initializeTables() {
        try {
            Statement statement = DatabaseFactory.getConnection().createStatement();
            String _CREATE_TABLE_JAILS = "CREATE TABLE IF NOT EXISTS JAILS (jailname varchar(255) not null, x double not null, y double not null, z double not null, world varchar(255) not null)";
            String _CREATE_TABLE_JAILED = "CREATE TABLE IF NOT EXISTS JAILED_PLAYERS (playerName varchar(255) not null, jailName varchar(255), isJailed boolean not null, reason varchar(255) not null, startDate varchar(255) not null, finishDate varchar(255) not null)";
            statement.execute(_CREATE_TABLE_JAILS);
            statement.execute(_CREATE_TABLE_JAILED);
            //statement.execute(_CREATE_TABLE_HISTORY);
        } catch (SQLException e) {
            PluginLogger.log("ERROR",e.getLocalizedMessage());
        }
    }

    private static void createConnection() {
        try {
            String HOST = "jdbc:mysql://" + _config.getString("ADDRESS") + ":" + _config.getString("PORT");
            String USERNAME = _config.getString("USERNAME");
            String PASSWORD = _config.getString("PASSWORD");
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);
            initializeDatabase();
            Statement statement = connection.createStatement();
            statement.execute("USE exp_jails");
            initializeTables();
            PluginLogger.log("SUCCESS","Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                return null;
            }
            return connection;
        } catch (SQLException e) {
            PluginLogger.log("ERROR",e.getLocalizedMessage());
        }
        return null;
    }

}
