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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    private final Maddox maddox;

    public MessageReceivedListener(Maddox maddox) {
        this.maddox = maddox;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getAuthor().equals(event.getJDA().getSelfUser())) {

            String prefix = maddox.getDefaultPrefix();

            this.maddox.getCommandHandler().handle(event, prefix, event.getMessage().getContentRaw());

            prefix = null;
        }

    }

}