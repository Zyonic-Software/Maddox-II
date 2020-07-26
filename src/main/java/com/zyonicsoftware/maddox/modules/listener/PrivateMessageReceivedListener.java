/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageReceivedListener extends ListenerAdapter {

    private final Maddox maddox;

    public PrivateMessageReceivedListener(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    public void onPrivateMessageReceived(final PrivateMessageReceivedEvent event) {
        if (event.getAuthor().equals(event.getAuthor().getJDA().getSelfUser())) {

            final String prefix = this.maddox.getDefaultPrefix();

            this.maddox.getPrivateMessageCommandHandler().handle(event, prefix, event.getMessage().getContentRaw());
        }
    }

}
