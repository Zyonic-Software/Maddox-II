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

    public GuildMemberJoinListener(final Maddox maddox) {
        this.maddox = maddox;
    }

    @Override
    public void onGuildMemberJoin(final GuildMemberJoinEvent event) {
        if (this.maddox.isMySQLConnected()) {
            this.assignAutoRoles(event);
            this.sendJoinMessage(event);
        }
    }

    private void assignAutoRoles(final GuildMemberJoinEvent event) {
        final MaddoxMember member = new MaddoxMember(event.getMember());
        this.maddox.getAutomaticRoleManager().getRolesForAutomaticAssigning(new MaddoxGuild(event.getGuild(), this.maddox.getCacheManager().getPrefix(event.getGuild().getId()), this.maddox.getCacheManager())).forEach(role -> {
            member.addRole(role).queue();
        });
    }

    private void sendJoinMessage(final GuildMemberJoinEvent event) {
        if (this.maddox.getCacheManager().isJoinMessageEnabled(event.getGuild().getId())) {
            final String channelID = this.maddox.getCacheManager().getJoinMessageChannel(event.getGuild().getId());
            if (this.maddox.getCacheManager().isJoinMessageEnabled(event.getGuild().getId()) && channelID != null) {
                try {
                    Objects.requireNonNull(event.getGuild().getTextChannelById(channelID)).sendMessage(this.maddox.getCacheManager().getJoinMessage(event.getGuild().getId()).replace("<USER>", event.getMember().getAsMention()).replace("<SERVER>", "**" + event.getGuild().getName() + "**")).queue();
                } catch (final Exception ignored) {
                }
            }
            if (this.maddox.getCacheManager().isPrivateJoinMessageEnabled(event.getGuild().getId())) {
                final String privateMessage = this.maddox.getCacheManager().getPrivateJoinMessage(event.getGuild().getId());
                if (privateMessage != null) {
                    try {
                        event.getMember().getUser().openPrivateChannel().complete(true).sendMessage(privateMessage.replace("<USER>", event.getMember().getAsMention()).replace("<SERVER>", event.getGuild().getName())).queue();
                    } catch (final Exception ignored) {
                    }
                }
            }
        }
    }

}
