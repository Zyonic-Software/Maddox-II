/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

    private final Maddox maddox;

    public GuildJoinListener(Maddox maddox) {
        this.maddox = maddox;
    }

    public void onGuildJoin(GuildJoinEvent event) {
        if (this.maddox.isMySQLConnected()) {
            this.maddox.getMySQLHandler().addServerToDatabase(event.getGuild().getId(), this.maddox.getDefaultPrefix(), this.maddox.getDefaultLanguage());
            StringBuilder commandsInString = new StringBuilder();
            this.maddox.getCommandHandler().getCommands().forEach((name, command) -> {
                if(command.isToggleable()) {
                    commandsInString.append(name).append(";");
                }
            });
            this.maddox.getMySQLHandler().setEnabledCommands(commandsInString.toString(), event.getGuild().getId());
        }
    }

}
