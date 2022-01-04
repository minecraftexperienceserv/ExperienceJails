package it.minecraftexperience.experiencejails.modules.modulesInterface;

import java.util.Date;

public abstract class JailedPlayerInterface {

    public abstract String getPlayerName();
    public abstract String getJailName();
    public abstract boolean isJailed();
    public abstract String getReason();
    public abstract Date getStartDate();
    public abstract Date getFinishDate();

}
