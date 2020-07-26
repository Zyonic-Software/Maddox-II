/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeaveListener extends ListenerAdapter {

    private final Maddox maddox;

    public GuildLeaveListener(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    public void onGuildLeave(final GuildLeaveEvent event) {
        if (this.maddox.isMySQLConnected()) {
            this.maddox.getMySQLHandler().removeServerFromDatabase(event.getGuild().getId());
        }
    }

}
