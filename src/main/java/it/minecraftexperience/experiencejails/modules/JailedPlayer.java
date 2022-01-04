package it.minecraftexperience.experiencejails.modules;

import it.minecraftexperience.experiencejails.modules.modulesInterface.JailedPlayerInterface;

import java.util.Date;

public class JailedPlayer extends JailedPlayerInterface {

    private String name;
    private String jailname;
    private String reason;
    private Date start;
    private Date finish;
    private boolean isJailed;

    public JailedPlayer(String name, String jailname, boolean isJailed, String reason, Date start, Date finish) {
        this.name = name;
        this.jailname = jailname;
        this.isJailed = isJailed;
        this.reason = reason;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    @Override
    public String getJailName() {
        return jailname;
    }

    @Override
    public boolean isJailed() {
        return isJailed;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public Date getStartDate() {
        return start;
    }

    @Override
    public Date getFinishDate() {
        return finish;
    }
}
