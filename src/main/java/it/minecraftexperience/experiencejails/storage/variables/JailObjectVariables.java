package it.minecraftexperience.experiencejails.storage.variables;

import it.minecraftexperience.experiencejails.storage.patternInterfaces.JailObjectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JailObjectVariables {

    private static final List<JailObjectInterface> jailObjectInterfaces = new ArrayList<>();

    public static void add(JailObjectInterface i) {
        jailObjectInterfaces.add(i);
    }
    public static void forEach(Consumer<? super JailObjectInterface> body) {
        jailObjectInterfaces.forEach(body);
    }
    public static void removeIf(Predicate<? super JailObjectInterface> j) { jailObjectInterfaces.removeIf(j); }
    public static List<JailObjectInterface> getObjects() {
        return jailObjectInterfaces;
    }
    public static int size() {
        return jailObjectInterfaces.size();
    }
    public static JailObjectInterface findByName(String key) {
        for(JailObjectInterface j : jailObjectInterfaces) {
            if(j.getJail().equals(key)) {
                return j;
            }
        }
        return null;
    }



}
