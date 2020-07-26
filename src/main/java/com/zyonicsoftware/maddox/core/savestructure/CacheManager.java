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

    public CacheManager(final Maddox maddox) {
        this.maddox = maddox;
    }

    private void registerServerToCache(final String guildID) {
        final HashMap<Datatype, String> guildData = new HashMap<>();
        guildData.put(Datatype.PREFIX, this.maddox.getMySQLHandler().getPrefix(guildID));
        guildData.put(Datatype.LANGUAGE, this.maddox.getMySQLHandler().getServerLanguage(guildID));
        guildData.put(Datatype.AUTOROLES, this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(guildID));
        guildData.put(Datatype.JOINCHANNEL, this.maddox.getMySQLHandler().getJoinMessageChannel(guildID));
        guildData.put(Datatype.JOINMESSAGE, this.maddox.getMySQLHandler().getJoinMessage(guildID));
        guildData.put(Datatype.LEAVECHANNEL, this.maddox.getMySQLHandler().getLeaveMessageChannel(guildID));
        guildData.put(Datatype.LEAVEMESSAGE, this.maddox.getMySQLHandler().getLeaveMessage(guildID));
        guildData.put(Datatype.ENABLED_COMMANDS, this.maddox.getMySQLHandler().getEnabledCommands(guildID));

        this.guildMap.put(guildID, guildData);

        final HashMap<Toggletype, Boolean> guildToggleData = new HashMap<>();
        try {
            guildToggleData.put(Toggletype.JOINMESSAGE, this.maddox.getMySQLHandler().isJoinMessageEnabled(guildID));
        } catch (final Exception ignored) {
            guildToggleData.put(Toggletype.JOINMESSAGE, false);
            this.maddox.getMySQLHandler().setJoinMessageEnabled(false, guildID);
        }
        try {
            guildToggleData.put(Toggletype.LEAVEMESSAGE, this.maddox.getMySQLHandler().isLeaveMessageEnabled(guildID));
        } catch (final Exception ignored) {
            guildToggleData.put(Toggletype.LEAVEMESSAGE, false);
            this.maddox.getMySQLHandler().setLeaveMessageEnabled(false, guildID);
        }

        this.guildToggleMap.put(guildID, guildToggleData);
    }

    public String getPrefix(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.PREFIX);
    }

    public void setPrefix(final String prefix, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.PREFIX, prefix);
        this.maddox.getMySQLHandler().setPrefix(prefix, guildID);
    }


    public String getLanguage(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.LANGUAGE);
    }

    public void setLanguage(final String language, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LANGUAGE, language);
        this.maddox.getMySQLHandler().setLanguage(language, guildID);
    }


    public String getRolesForAutomaticAssigning(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.AUTOROLES);
    }

    public void setRolesForAutomaticAssigning(final String rolesforautomaticassigning, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.AUTOROLES, rolesforautomaticassigning);
        this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesforautomaticassigning, guildID);
    }


    public String getJoinMessage(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.JOINMESSAGE);
    }

    public void setJoinMessage(final String joinmessage, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.JOINMESSAGE, joinmessage);
        this.maddox.getMySQLHandler().setJoinMessage(joinmessage, guildID);
    }


    public String getJoinMessageChannel(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.JOINCHANNEL);
    }

    public void setJoinMessageChannel(final String joinMessageChannelID, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.JOINCHANNEL, joinMessageChannelID);
        this.maddox.getMySQLHandler().setJoinMessageChannel(joinMessageChannelID, guildID);
    }


    public String getLeaveMessage(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.LEAVEMESSAGE);
    }

    public void setLeaveMessage(final String leavemessage, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVEMESSAGE, leavemessage);
        this.maddox.getMySQLHandler().setLeaveMessage(leavemessage, guildID);
    }


    public String getLeaveMessageChannel(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.LEAVECHANNEL);
    }

    public void setLeaveMessageChannel(final String leaveMessageChannelID, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVECHANNEL, leaveMessageChannelID);
        this.maddox.getMySQLHandler().setLeaveMessageChannel(leaveMessageChannelID, guildID);
    }


    public String getEnabledCommands(final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildMap.get(guildID).get(Datatype.ENABLED_COMMANDS);
    }

    public void setEnabledCommands(final String enabledCommandsInString, final String guildID) {
        if (!this.guildMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildMap.get(guildID).put(Datatype.LEAVECHANNEL, enabledCommandsInString);
        this.maddox.getMySQLHandler().setEnabledCommands(enabledCommandsInString, guildID);
    }

    public boolean isJoinMessageEnabled(final String guildID) {
        if (!this.guildToggleMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildToggleMap.get(guildID).get(Toggletype.JOINMESSAGE);
    }

    public void setJoinMessageEnabled(final boolean state, final String guildID) {
        if (!this.guildToggleMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildToggleMap.get(guildID).put(Toggletype.JOINMESSAGE, state);
        this.maddox.getMySQLHandler().setJoinMessageEnabled(true, guildID);
    }

    public boolean isLeaveMessageEnabled(final String guildID) {
        if (!this.guildToggleMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        return this.guildToggleMap.get(guildID).get(Toggletype.JOINMESSAGE);
    }

    public void setLeaveMessageEnabled(final boolean state, final String guildID) {
        if (!this.guildToggleMap.containsKey(guildID)) {
            this.registerServerToCache(guildID);
        }
        this.guildToggleMap.get(guildID).put(Toggletype.LEAVEMESSAGE, state);
        this.maddox.getMySQLHandler().setLeaveMessageEnabled(true, guildID);
    }
}
