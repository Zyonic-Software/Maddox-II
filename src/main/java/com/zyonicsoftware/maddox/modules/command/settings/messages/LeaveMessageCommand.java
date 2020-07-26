/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings.messages;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;

public class LeaveMessageCommand extends Command {

  private final Maddox maddox;

  public LeaveMessageCommand(final Maddox maddox) {
    this.maddox = maddox;
    this.setName("leavemessage");
    this.setSyntax("LeaveMessage-Syntax");
    this.setDescription("LeaveMessage-Desc");
    this.setCategory("Settings-Category");
    this.setAllowExecutionOnMessageEdit(true);
    this.setGetValuesFromLanguageYAML(true);
    this.setCommandHelpViewPermission(CommandHelpViewPermission.MANAGE_SERVER);
  }

  @Override
  protected void execute(final CommandEvent event, final MaddoxMember sender,
                         final MaddoxGuild server) {
    if (sender.hasPermission(Permission.MANAGE_SERVER)) {
      if (event.getArguments().size() > 1) {
        if (event.getArguments().get(0).equalsIgnoreCase(
                "set")) { // Setting the LeaveMessage
          this.maddox.getCacheManager().setLeaveMessage(
              event.getArgumentsAsString().substring("set ".length()),
              server.getID());
          this.maddox.getCacheManager().setLeaveMessageEnabled(true,
                                                               server.getID());
          event.reply(
              LanguageAPI.getValue("EventMessage-Set", server.getLanguage())
                  .replace("<MESSAGE-TYPE>",
                           LanguageAPI.getValue("LeaveMessage",
                                                server.getLanguage()))
                  .replace("<MESSAGE>",
                           "'" +
                               event.getArgumentsAsString().substring(
                                   "set ".length()) +
                               "'"));
        } else if (event.getArguments().get(0).equalsIgnoreCase("setchannel")) {
          if (!event.getTextChannelMentions().isEmpty()) {
            this.maddox.getCacheManager().setLeaveMessageChannel(
                event.getTextChannelMentions().get(0).getId(), server.getID());
            event.reply(
                LanguageAPI.getValue("ChannelSet", server.getLanguage())
                    .replace("<MESSAGE-TYPE>",
                             LanguageAPI.getValue("LeaveMessage",
                                                  server.getLanguage()))
                    .replace(
                        "<CHANNEL>",
                        event.getTextChannelMentions().get(0).getAsMention()));
          } else {
            event.reply(
                LanguageAPI.getValue("NoChannelProvided", server.getLanguage())
                    .replace("<PREFIX>", server.getPrefix())
                    .replace("<MESSAGE-TYPE>", "leave"));
          }
        }
      } else if (!event.getArguments().isEmpty()) {
        if (event.getArguments().get(0).equalsIgnoreCase("disable")) {
          this.maddox.getCacheManager().setLeaveMessageEnabled(false,
                                                               server.getID());
          event.reply(
              LanguageAPI.getValue("MessageDisabled", server.getLanguage())
                  .replace("<MESSAGE-TYPE>",
                           LanguageAPI.getValue("LeaveMessage",
                                                server.getLanguage())));
        } else if (event.getArguments().get(0).equalsIgnoreCase("enable")) {
          this.maddox.getCacheManager().setLeaveMessageEnabled(true,
                                                               server.getID());
          event.reply(
              LanguageAPI.getValue("MessageEnabled", server.getLanguage())
                  .replace("<MESSAGE-TYPE>",
                           LanguageAPI.getValue("LeaveMessage",
                                                server.getLanguage())));
        } else if (event.getArguments().get(0).equalsIgnoreCase("set")) {
          event.reply(
              LanguageAPI.getValue("NoMessageProvided", server.getLanguage())
                  .replace("<PREFIX>", server.getPrefix())
                  .replace("<MESSAGE-TYPE>", "leave"));
        } else if (event.getArguments().get(0).equalsIgnoreCase("setchannel")) {
          event.reply(
              LanguageAPI.getValue("NoChannelProvided", server.getLanguage())
                  .replace("<PREFIX>", server.getPrefix())
                  .replace("<MESSAGE-TYPE>", "leave"));
        }
      } else {
        event.reply(
            LanguageAPI
                .getValue("MessageArgumentsMissing", server.getLanguage())
                .replace("<PREFIX>", server.getPrefix())
                .replace("<MESSAGE-TYPE>", "leave"));
      }
    }
  }
}
