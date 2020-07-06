/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class GuildMemberJoinListener extends ListenerAdapter {

    private final Maddox maddox;

    public GuildMemberJoinListener(Maddox maddox) {
        this.maddox = maddox;
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (this.maddox.isMySQLConnected()) {
            this.assignAutoRoles(event);
            this.sendJoinMessage(event);
        }
    }

    private void assignAutoRoles(GuildMemberJoinEvent event) {
        MaddoxMember member = new MaddoxMember(event.getMember());
        this.maddox.getAutomaticRoleManager().getRolesForAutomaticAssigning(new MaddoxGuild(event.getGuild(), this.maddox.getMySQLHandler().getPrefix(event.getGuild().getId()), this.maddox.getMySQLHandler())).forEach(role -> {
            member.addRole(role).queue();
        });
    }

    private void sendJoinMessage(GuildMemberJoinEvent event) {
        if (this.maddox.getMySQLHandler().isJoinMessageEnabled(event.getGuild().getId())) {
            String channelID = this.maddox.getMySQLHandler().getJoinMessageChannel(event.getGuild().getId());
            if (channelID != null) {
                Objects.requireNonNull(event.getGuild().getTextChannelById(channelID)).sendMessage(this.maddox.getMySQLHandler().getJoinMessage(event.getGuild().getId()).replace("<USER>", event.getMember().getAsMention()).replace("<SERVER>","**" + event.getGuild().getName() + "**")).queue();
            }
        }
    }

}
