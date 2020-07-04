/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings.system;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;

public class SetAutoRoleCommand extends Command {

    private final Maddox maddox;

    public SetAutoRoleCommand(Maddox maddox){
        this.maddox = maddox;
        this.setName("setprefix");
    }

    @Override
    protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
        if(!event.getArguments().isEmpty()){ //ToDo
        }
    }
}
