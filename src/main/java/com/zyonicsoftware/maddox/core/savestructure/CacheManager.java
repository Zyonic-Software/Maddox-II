/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.savestructure;

import com.zyonicsoftware.maddox.core.main.Maddox;

import java.util.HashMap;

public class CacheManager {

    private final Maddox maddox;
    private final HashMap<String, HashMap<Datatype, String>> guildMap = new HashMap<>();
    private final HashMap<String, HashMap<Toggletype, Boolean>> guildToggleMap = new HashMap<>();

    public CacheManager(Maddox maddox) {
        this.maddox = maddox;
    }

    private void registerServerToCache(String guildID) {
        HashMap<Datatype, String> guildData = new HashMap<>();
        guildData.put(Datatype.PREFIX, this.maddox.getMySQLHandler().getPrefix(guildID));
        guildData.put(Datatype.LANGUAGE, this.maddox.getMySQLHandler().getServerLanguage(guildID));
        guildData.put(Datatype.AUTOROLES, this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(guildID));
        guildData.put(Datatype.JOINCHANNEL, this.maddox.getMySQLHandler().getJoinMessageChannel(guildID));
        guildData.put(Datatype.JOINMESSAGE, this.maddox.getMySQLHandler().getJoinMessage(guildID));
        guildData.put(Datatype.LEAVECHANNEL, this.maddox.getMySQLHandler().getLeaveMessageChannel(guildID));
        guildData.put(Datatype.LEAVEMESSAGE, this.maddox.getMySQLHandler().getLeaveMessage(guildID));
        guildData.put(Datatype.ENABLED_COMMANDS, this.maddox.getMySQLHandler().getEnabledCommands(guildID));

        guildMap.put(guildID, guildData);

        HashMap<Toggletype, Boolean> guildToggleData = new HashMap<>();
        guildToggleData.put(Toggletype.JOINMESSAGE, this.maddox.getMySQLHandler().isJoinMessageEnabled(guildID));
        guildToggleData.put(Toggletype.LEAVEMESSAGE, this.maddox.getMySQLHandler().isLeaveMessageEnabled(guildID));
    }

    public String getPrefix(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return guildMap.get(guildID).get(Datatype.PREFIX);
    }

    public void setPrefix(String prefix, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.PREFIX, prefix);
        this.maddox.getMySQLHandler().setPrefix(prefix, guildID);
    }


    public String getLanguage(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return guildMap.get(guildID).get(Datatype.LANGUAGE);
    }

    public void setLanguage(String language, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LANGUAGE, language);
        this.maddox.getMySQLHandler().setLanguage(language, guildID);
    }


    public String getRolesForAutomaticAssigning(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.AUTOROLES);
    }

    public void setRolesForAutomaticAssigning(String rolesforautomaticassigning, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.AUTOROLES, rolesforautomaticassigning);
        this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesforautomaticassigning, guildID);
    }


    public String getJoinMessage(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.JOINMESSAGE);
    }

    public void setJoinMessage(String joinmessage, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.JOINMESSAGE, joinmessage);
        this.maddox.getMySQLHandler().setJoinMessage(joinmessage, guildID);
    }


    public String getJoinMessageChannel(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.JOINCHANNEL);
    }

    public void setJoinMessageChannel(String joinMessageChannelID, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.JOINCHANNEL, joinMessageChannelID);
        this.maddox.getMySQLHandler().setJoinMessageChannel(joinMessageChannelID, guildID);
    }


    public String getLeaveMessage(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.LEAVEMESSAGE);
    }

    public void setLeaveMessage(String leavemessage, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVEMESSAGE, leavemessage);
        this.maddox.getMySQLHandler().setLeaveMessage(leavemessage, guildID);
    }


    public String getLeaveMessageChannel(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.LEAVECHANNEL);
    }

    public void setLeaveMessageChannel(String leaveMessageChannelID, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVECHANNEL, leaveMessageChannelID);
        this.maddox.getMySQLHandler().setLeaveMessageChannel(leaveMessageChannelID, guildID);
    }


    public String getEnabledCommands(String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.ENABLED_COMMANDS);
    }

    public void setEnabledCommands(String enabledCommandsInString, String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVECHANNEL, enabledCommandsInString);
        this.maddox.getMySQLHandler().setEnabledCommands(enabledCommandsInString, guildID);
    }
}
