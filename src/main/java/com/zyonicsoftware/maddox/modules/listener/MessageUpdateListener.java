/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */
package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUpdateListener extends ListenerAdapter {

    private final Maddox maddox;

    public MessageUpdateListener(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    public void onGuildMessageUpdate(final GuildMessageUpdateEvent event) {

        if (!event.getAuthor().equals(event.getJDA().getSelfUser())) {

            final String prefix;

            if (this.maddox.isMySQLConnected()) {
                prefix = this.maddox.getCacheManager().getPrefix(event.getGuild().getId());
            } else {
                prefix = this.maddox.getDefaultPrefix();
            }

            this.maddox.getCommandHandler().handle(event, prefix, event.getMessage().getContentRaw());
        }

    }

}
