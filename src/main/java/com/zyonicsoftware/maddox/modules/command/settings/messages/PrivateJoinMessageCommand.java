/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings.messages;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.Permission;

public class PrivateJoinMessageCommand extends Command {

    public final Maddox maddox;

    public PrivateJoinMessageCommand(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (this.maddox.isMySQLConnected()) {
            if (sender.hasPermission(Permission.MANAGE_SERVER)) {
                if (event.getArguments().size() > 1) {
                    if (event.getArguments().get(0).equalsIgnoreCase("set")) {
                        this.maddox.getCacheManager().setPrivateJoinMessage(event.getArgumentsAsString().substring(4), server.getID());
                    }
                }
            }
        }
    }
}
