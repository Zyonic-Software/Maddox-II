/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.management;

import com.zyonicsoftware.maddox.core.main.Maddox;
import java.util.ArrayList;
import java.util.Arrays;
import net.dv8tion.jda.api.entities.Guild;

public class CommandToggleManager {

  private final Maddox maddox;

  public CommandToggleManager(final Maddox maddox) { this.maddox = maddox; }

  public ArrayList<String> getCommandsForToggling(final Guild guild) {
    return (ArrayList<String>)Arrays.asList(
        this.maddox.getCacheManager()
            .getEnabledCommands(guild.getId())
            .split(";"));
  }

  public void setCommandsForToggling(final Guild guild,
                                     final ArrayList<String> commands) {

    final StringBuilder commandsInString = new StringBuilder();

    commands.forEach(
        commandName -> { commandsInString.append(commandName).append(";"); });

    this.maddox.getCacheManager().setEnabledCommands(
        commandsInString.toString(), guild.getId());
  }
}
