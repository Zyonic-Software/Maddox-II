/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings.system;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import net.dv8tion.jda.api.Permission;

public class SetPrefixCommand extends Command {

    public SetPrefixCommand() {
        this.setName("setprefix");
        this.setCategory("SetPrefix-Category");
        this.setSyntax("SetPrefix-Syntax");
        this.setDescription("SetPrefix-Desc");
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.ADMINISTRATOR);
        this.setGetValuesFromLanguageYAML(true);
        this.setShowInHelp(true);
    }

    @Override
    protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
        if (sender.hasPermission(Permission.ADMINISTRATOR)) {
            if (!event.getArguments().isEmpty()) {
                server.setPrefix(event.getArguments().get(0));
            }
        } else {
            event.deleteEventMessage();
        }
    }
}
