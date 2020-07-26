/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.moderation;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageHistory;

public class ClearCommand extends Command {

    public ClearCommand() {
        this.setName("clear");
        this.setDescription("Clear-Desc");
        this.setSyntax("Clear-Syntax");
        this.setCategory("Moderation-Category");
        this.setGetValuesFromLanguageYAML(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.MANAGE_MESSAGE);
        this.setToggleable(true);
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (!sender.hasPermission(Permission.MESSAGE_MANAGE)) {
            return;
        }

        if (event.getArguments().isEmpty()) {
            event.reply(LanguageAPI.getValue("Clear-NoNumberProvided", server.getLanguage()));
            return;
        }

        try {
            final int amount = Integer.parseInt(event.getArguments().get(0));

            if (amount > 1 && amount < 101) {
                event.deleteEventMessage();
                final MessageHistory hist = new MessageHistory(event.getChannel());
                event.getChannel().deleteMessages(hist.retrievePast(amount).complete()).queue();
            }
        } catch (final NumberFormatException numberFormatException) {
            event.reply(LanguageAPI.getValue("Clear-NoNumberProvided", server.getLanguage()));
        } catch (final IllegalArgumentException illegalArgumentException) {
            event.reply(LanguageAPI.getValue("Clear-MessagesTooOld", server.getLanguage()));
        } catch (final Exception ignored) {
        }
    }
}
