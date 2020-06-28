/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.privatemessage;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.HashMap;

public class PrivateMessageCommandHandler {

    private final Maddox maddox;
    private final HashMap<String, PrivateMessageCommand> privateCommands;

    public PrivateMessageCommandHandler(Maddox maddox) {
        this.maddox = maddox;

        privateCommands = new HashMap<>();
    }

    public void registerPrivateMessageCommand(PrivateMessageCommand privateMessageCommand) {
        privateCommands.put(this.maddox.getDefaultPrefix() + privateMessageCommand.getName(), privateMessageCommand);
    }

    public void handle(PrivateMessageReceivedEvent event, String prefix, String messageContent) {
        if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            if (seperatedStrings.length > 0) {

                PrivateMessageCommand selectedCommand = privateCommands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    selectedCommand.execute(new PrivateMessageCommandEvent(selectedCommand, event, this.maddox));

                    seperatedStrings = null;
                    selectedCommand = null;
                }
            }
        } else if (messageContent.startsWith(prefix + " ")) {

            if (messageContent.startsWith(prefix)) {

                String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

                if (seperatedStrings.length > 0) {

                    PrivateMessageCommand selectedCommand = privateCommands.get(seperatedStrings[0].toLowerCase());

                    if (selectedCommand != null) {
                        selectedCommand.execute(new PrivateMessageCommandEvent(selectedCommand, event, this.maddox));

                        seperatedStrings = null;
                        selectedCommand = null;
                    }
                }
            }
        }
    }
}
