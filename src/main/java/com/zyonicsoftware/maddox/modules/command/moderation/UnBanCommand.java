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
import net.dv8tion.jda.api.exceptions.RateLimitedException;

public class UnBanCommand extends Command {

    private final Maddox maddox;

    public UnBanCommand(final Maddox maddox) {
        this.maddox = maddox;
        this.setName("unban");
        this.setDescription("UnBan-Desc");
        this.setSyntax("UnBan-Syntax");
        this.setCategory("Moderation-Category");
        this.setGetValuesFromLanguageYAML(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.MEMBER_BAN);
        this.setToggleable(true);
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (sender.hasPermission(Permission.BAN_MEMBERS)) {
            if (!event.getArguments().isEmpty()) {
                if (this.isTag(event.getArguments().get(0))) {
                    try {
                        server.getGuild().retrieveBanList().complete(true).forEach(ban -> {
                            if (ban.getUser().getAsTag().equalsIgnoreCase(event.getArguments().get(0))) {
                                server.unban(ban.getUser()).queue();
                                event.reply(
                                        new EmbedBuilder()
                                                .addField("Unban", LanguageAPI.getValue("UnBan").replace("<USER>", ban.getUser().getName()), false)
                                                .setColor(this.maddox.getDefaultColor())
                                                .build()
                                );
                                return;
                            }
                        });
                    } catch (final RateLimitedException e) {
                        System.out.println("Ratelimit@Unban-" + event.getArguments().get(0) + "-" + server.getGuild().getName());
                    }
                }
            }
        }
    }

    private boolean isTag(final String value) {
        return value.matches(".*[#].*") && value.matches(".*[1234567890].*");
    }
}
