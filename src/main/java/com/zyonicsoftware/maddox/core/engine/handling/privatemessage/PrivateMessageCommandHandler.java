/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.privatemessage;

import com.zyonicsoftware.maddox.core.main.Maddox;
import java.util.HashMap;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class PrivateMessageCommandHandler {

  private final Maddox maddox;
  private final HashMap<String, PrivateMessageCommand> privateCommands;

  public PrivateMessageCommandHandler(final Maddox maddox) {
    this.maddox = maddox;

    this.privateCommands = new HashMap<>();
  }

  public void registerPrivateMessageCommand(
      final PrivateMessageCommand privateMessageCommand) {
    this.privateCommands.put(this.maddox.getDefaultPrefix() +
                                 privateMessageCommand.getName(),
                             privateMessageCommand);
  }

  public void handle(final PrivateMessageReceivedEvent event,
                     final String prefix, final String messageContent) {
    if (messageContent.startsWith(prefix)) {

      final String[] seperatedStrings =
          messageContent.substring(prefix.length()).split(" ");

      if (seperatedStrings.length > 0) {

        final PrivateMessageCommand selectedCommand =
            this.privateCommands.get(seperatedStrings[0].toLowerCase());

        if (selectedCommand != null) {
          selectedCommand.execute(new PrivateMessageCommandEvent(
              selectedCommand, event, this.maddox));
        }
      }
    } else if (messageContent.startsWith(prefix + " ")) {

      if (messageContent.startsWith(prefix)) {

        final String[] seperatedStrings =
            messageContent.substring(prefix.length()).split(" ");

        if (seperatedStrings.length > 0) {

          final PrivateMessageCommand selectedCommand =
              this.privateCommands.get(seperatedStrings[0].toLowerCase());

          if (selectedCommand != null) {
            selectedCommand.execute(new PrivateMessageCommandEvent(
                selectedCommand, event, this.maddox));
          }
        }
      }
    }
  }
}
