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
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class KickCommand extends Command {

    private final Maddox maddox;

    public KickCommand(final Maddox maddox) {
        this.maddox = maddox;
        this.setName("kick");
        this.setDescription("Kick-Desc");
        this.setSyntax("Kick-Syntax");
        this.setCategory("Moderation-Category");
        this.setGetValuesFromLanguageYAML(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.MEMBER_KICK);
        this.setToggleable(true);
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        event.getArguments();
        if (sender.hasPermission(Permission.KICK_MEMBERS)) {
            if (!event.getMentions().isEmpty()) {
                if (event.getArguments().size() > 1) {
                    event.getMentions().get(0).kick(event.getArguments().get(1)).queue();
                    event.reply(
                            new EmbedBuilder()
                                    .addField("Kick", LanguageAPI.getValue("Kick-With-Reason").replace("<USER>", event.getMentions().get(0).getUsername()).replace("<REASON>", event.getArgumentsAsString().replace(" " + event.getMentions().get(0).getAsMention() + " ", "")), false)
                                    .setColor(this.maddox.getDefaultColor())
                                    .build()
                    );
                } else {
                    event.getMentions().get(0).kick().queue();
                    event.reply(
                            new EmbedBuilder()
                                    .addField("Kick", LanguageAPI.getValue("Kick-Without-Reason").replace("<USER>", event.getMentions().get(0).getUsername()), false)
                                    .setColor(this.maddox.getDefaultColor())
                                    .build()
                    );
                }
            } else {
                event.reply(LanguageAPI.getValue("Kick-NoPersonDefined", server.getLanguage()).replace("<PREFIX>", event.getPrefix()));
            }
        }
    }
}
