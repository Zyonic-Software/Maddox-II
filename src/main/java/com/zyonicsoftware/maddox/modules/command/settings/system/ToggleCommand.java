/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings.system;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;

public class ToggleCommand extends Command {

  private final Maddox maddox;

  public ToggleCommand(final Maddox maddox) {
    this.maddox = maddox;
    this.setName("toggle");
    this.setCategory("Settings-Category");
    this.setDescription("Toggle-Desc");
    this.setSyntax("Toggle-Syntax");
    this.setGetValuesFromLanguageYAML(true);
    this.setCommandHelpViewPermission(CommandHelpViewPermission.MANAGE_SERVER);
  }

  @Override
  protected void execute(final CommandEvent event, final MaddoxMember sender,
                         final MaddoxGuild server) {
    if (sender.hasPermission(Permission.MANAGE_SERVER)) {
      if (event.getArguments().size() > 1) {
        if (this.maddox.getCommandHandler().getCommands().containsKey(
                event.getArguments().get(0))) {
          if (this.maddox.getCommandHandler()
                  .getCommands()
                  .get(event.getArguments().get(0))
                  .isToggleable()) {
            if (event.getArguments().get(1).equalsIgnoreCase("on")) {
              final String initialCommandsInString =
                  this.maddox.getCacheManager().getEnabledCommands(
                      event.getGuild().getId());
              if (initialCommandsInString.contains(
                      event.getArguments().get(0))) {
                event.reply(
                    LanguageAPI.getValue("NoChange", server.getLanguage()));
              } else {
                this.maddox.getCacheManager().setEnabledCommands(
                    initialCommandsInString + event.getArguments().get(0) + ";",
                    event.getGuild().getId());
                event.reply(
                    LanguageAPI.getValue("Toggle-ON", server.getLanguage())
                        .replace("<COMMAND>", event.getArguments().get(0))
                        .replace("<PREFIX>", server.getPrefix()));
              }
            } else if (event.getArguments().get(1).equalsIgnoreCase("off")) {
              final String initialCommandsInString =
                  this.maddox.getCacheManager().getEnabledCommands(
                      event.getGuild().getId());
              if (!initialCommandsInString.contains(
                      event.getArguments().get(0))) {
                event.reply(
                    LanguageAPI.getValue("NoChange", server.getLanguage()));
              } else {
                this.maddox.getCacheManager().setEnabledCommands(
                    initialCommandsInString.replace(
                        event.getArguments().get(0) + ";", ""),
                    event.getGuild().getId());
                event.reply(
                    LanguageAPI.getValue("Toggle-OFF", server.getLanguage())
                        .replace("<COMMAND>", event.getArguments().get(0))
                        .replace("<PREFIX>", server.getPrefix()));
              }
            } else {
              event.reply(LanguageAPI.getValue("NoCommandOptionProvided",
                                               server.getLanguage()));
            }
          } else {
            event.reply(LanguageAPI.getValue("NoCommandOptionProvided",
                                             server.getLanguage()));
          }
        } else {
          event.reply(
              LanguageAPI.getValue("NoCommandProvided", server.getLanguage())
                  .replace("<PREFIX>", server.getPrefix()));
        }
      } else {
        event.reply(
            LanguageAPI.getValue("NoCommandProvided", server.getLanguage())
                .replace("<PREFIX>", server.getPrefix()));
      }
    }
  }
}
