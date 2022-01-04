package it.minecraftexperience.experiencejails.modules;

import it.minecraftexperience.experiencejails.modules.modulesInterface.JailInterface;

public class Jail extends JailInterface {

    private String name;
    private double x;
    private double y;
    private double z;
    private String world;

    public Jail(String name, double x, double y, double z, String world) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Jail(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPositionX() {
        return x;
    }

    @Override
    public double getPositionY() {
        return y;
    }

    @Override
    public double getPositionZ() {
        return z;
    }

    @Override
    public String getWorld() {
        return world;
    }
}
