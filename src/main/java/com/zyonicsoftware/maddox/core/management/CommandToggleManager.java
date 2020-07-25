/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.management;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandToggleManager {

    private final Maddox maddox;

    public CommandToggleManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public ArrayList<String> getCommandsForToggling(Guild guild) {
        return (ArrayList<String>) Arrays.asList(this.maddox.getMySQLHandler().getEnabledCommands(guild.getId()).split(";"));
    }

    public void setCommandsForToggling(Guild guild, ArrayList<String> commands) {

        StringBuilder commandsInString = new StringBuilder();

        commands.forEach(commandName -> {
            commandsInString.append(commandName).append(";");
        });

        this.maddox.getMySQLHandler().setEnabledCommands(commandsInString.toString(), guild.getId());
    }


}
