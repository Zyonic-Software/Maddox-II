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
import net.dv8tion.jda.api.Permission;

public class KickCommand extends Command {

    public KickCommand(){
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
    protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
        if(sender.hasPermission(Permission.KICK_MEMBERS)){
            if(!event.getMentions().isEmpty()){
                if(event.getArguments().size() > 1) {
                    event.getMentions().get(0).kick(event.getArguments().get(1)).queue();
                } else {
                    event.getMentions().get(0).kick().queue();
                }
            }
        }
    }
}
