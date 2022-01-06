package it.minecraftexperience.experiencejails.storage.patternInterfaces;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PlayerObjectInterface {

    private JailObjectInterface jail;
    private UUID targetUuid;
    private String reason;
    private UUID staffObjectUuid;
    private Date start;
    private Date finish;

}
