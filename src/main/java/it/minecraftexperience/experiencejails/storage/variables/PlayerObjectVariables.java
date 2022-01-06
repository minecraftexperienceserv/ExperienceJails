package it.minecraftexperience.experiencejails.storage.variables;

import it.minecraftexperience.experiencejails.storage.patternInterfaces.PlayerObjectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayerObjectVariables {
    
    private static List<PlayerObjectInterface> playerObjectInterfaces = new ArrayList<>();

    public static void add(PlayerObjectInterface o) { playerObjectInterfaces.add(o); }
    public static void forEach(Consumer<? super PlayerObjectInterface> c) { playerObjectInterfaces.forEach(c); }
    public static void removeIf(Predicate<? super PlayerObjectInterface> p) {
        playerObjectInterfaces.removeIf(p);
    }
    public static int size() { return playerObjectInterfaces.size(); }
    public static List<PlayerObjectInterface> getObjects() {
        return playerObjectInterfaces;
    }

}
