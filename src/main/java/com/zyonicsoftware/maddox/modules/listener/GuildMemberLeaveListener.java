/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.listener;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class GuildMemberLeaveListener extends ListenerAdapter {

    private final Maddox maddox;

    public GuildMemberLeaveListener(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    public void onGuildMemberRemove(final GuildMemberRemoveEvent event) {
        if (this.maddox.isMySQLConnected()) {
            this.sendLeaveMessage(event);
        }
    }


    private void sendLeaveMessage(final GuildMemberRemoveEvent event) {
        if (this.maddox.getCacheManager().isLeaveMessageEnabled(event.getGuild().getId())) {
            final String channelID = this.maddox.getCacheManager().getLeaveMessageChannel(event.getGuild().getId());
            if (channelID != null) {
                Objects.requireNonNull(event.getGuild().getTextChannelById(channelID)).sendMessage(this.maddox.getCacheManager().getLeaveMessage(event.getGuild().getId()).replace("<USER>", "**" + event.getMember().getEffectiveName() + "**").replace("<SERVER>", "**" + event.getGuild().getName() + "**")).queue();
            }
        }
    }
}
