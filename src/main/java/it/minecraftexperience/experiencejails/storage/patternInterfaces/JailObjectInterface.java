package it.minecraftexperience.experiencejails.storage.patternInterfaces;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@Setter
public class JailObjectInterface {

    private String jail;
    private Location location;
    private World world;

}
