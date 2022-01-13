package it.minecraftexperience.experiencejails.storage;

import lombok.Getter;

@Getter
public class SQLQueries {

    // Create Operators
    private String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS ?";
    private String CREATE_JAILS_TABLE = "CREATE TABLE IF NOT EXISTS JAILS " +
            "(jail varchar(255) not null, x double not null, y double not null, z double not null, yaw double not null, pitch double not null, world varchar(255) not null);";
    private String CREATE_JAILED_TABLE = "CREATE TABLE IF NOT EXISTS JAILED " +
            "(jail varchar(255) not null,target varchar(255) not null, reason varchar(255) not null, staffer varchar(255) not null, start varchar(255) not null, finish varchar(255) not null);";

    // Insertion Operators
    private String INSERT_JAIL = "INSERT INTO JAILS VALUES (?,?,?,?,?,?,?);";
    private String INSERT_JAILED = "INSERT INTO JAILED VALUES (?,?,?,?,?,?)'";

}
