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
        if (sender.hasPermission(Permission.KICK_MEMBERS)) {
            if (!event.getMentions().isEmpty()) {
                if (event.getArguments().size() > 1) {
                    final String memberID = event.getMentions().get(0).getID();
                    final String memberName = event.getMentions().get(0).getUsername();
                    event.getMentions().get(0).kick(event.getArgumentsAsString().replace("<@!" + memberID + ">" + " ", "")).queue();
                    event.reply(
                            new EmbedBuilder()
                                    .addField("Kick", LanguageAPI.getValue("Kick-With-Reason").replace("<USER>", memberName).replace("<REASON>", event.getArgumentsAsString().replace("<@!" + memberID + ">" + " ", "")), false)
                                    .setColor(this.maddox.getDefaultColor())
                                    .build()
                    );
                } else {
                    final String memberName = event.getMentions().get(0).getUsername();
                    event.getMentions().get(0).kick().queue();
                    event.reply(
                            new EmbedBuilder()
                                    .addField("Kick", LanguageAPI.getValue("Kick-Without-Reason").replace("<USER>", memberName), false)
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
