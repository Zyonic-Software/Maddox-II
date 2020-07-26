/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.information;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpCommand extends Command {

    private final Maddox maddox;

    public HelpCommand(final Maddox maddox) {
        this.setName("help");
        this.setCategory("Help-Category");
        this.setDescription("Help-Desc");
        this.setSyntax("Help-Syntax");
        this.setShowInHelp(true);
        this.setGetValuesFromLanguageYAML(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.EVERYONE);
        this.maddox = maddox;
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (event.getArguments().isEmpty()) {
            event.reply(this.maddox.getHelpBuilder().assembleHelp(sender, this.getTruePrefixIfPrefixIsMention(event), server.getLanguage()));
        } else if (event.getArguments().size() > 0 && this.maddox.getCommandHandler().getCommands().containsKey(event.getArguments().get(0).toLowerCase())) {
            final MessageEmbed messageEmbed = this.maddox.getHelpBuilder().generateCommandHelp(this.maddox.getCommandHandler().getCommands().get(event.getArguments().get(0).toLowerCase()), this.getTruePrefixIfPrefixIsMention(event), sender, server);
            if (messageEmbed != null) {
                event.reply(messageEmbed);
            }
        }
    }

    private String getTruePrefixIfPrefixIsMention(final CommandEvent event) {
        String prefix = event.getPrefix();
        if (prefix.equals("<@" + event.getJDA().getSelfUser().getId() + ">")) {
            prefix = this.maddox.getCacheManager().getPrefix(event.getServer().getID());
        }

        if (prefix.equals("<@!" + event.getJDA().getSelfUser().getId() + ">")) {
            prefix = this.maddox.getCacheManager().getPrefix(event.getServer().getID());
        }
        return prefix;
    }
}
