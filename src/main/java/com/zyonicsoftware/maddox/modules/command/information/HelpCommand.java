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
import com.zyonicsoftware.maddox.core.engine.objects.DiscordServer;
import com.zyonicsoftware.maddox.core.engine.objects.Sender;
import com.zyonicsoftware.maddox.core.main.Maddox;

public class HelpCommand extends Command {

    private Maddox maddox;

    public HelpCommand(Maddox maddox) {
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
    protected void execute(CommandEvent event, Sender sender, DiscordServer server) {
        if (event.getArguments().isEmpty()) {
            event.reply(maddox.getHelpBuilder().assembleHelp(sender, server.getPrefix(), server.getLanguage()));
        } else if (event.getArguments().size() > 0 && this.maddox.getCommandHandler().getCommands().containsKey(event.getArguments().get(0).toLowerCase())) {
            event.reply(this.maddox.getHelpBuilder().generateCommandHelp(this.maddox.getCommandHandler().getCommands().get(event.getArguments().get(0).toLowerCase()), server.getPrefix(), sender));
        }
    }
}
