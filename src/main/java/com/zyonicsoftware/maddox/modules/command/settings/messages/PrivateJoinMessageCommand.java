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
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;

public class PrivateJoinMessageCommand extends Command {

    public final Maddox maddox;

    public PrivateJoinMessageCommand(final Maddox maddox) {
        this.maddox = maddox;
        this.setName("pjoinmessage");
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (this.maddox.isMySQLConnected()) {
            if (sender.hasPermission(Permission.MANAGE_SERVER)) {
                String prefix = server.getPrefix();
                if (prefix.startsWith("<@") && prefix.endsWith(">")) {
                    prefix = this.maddox.getCacheManager().getPrefix(server.getID());
                }
                if (event.getArguments().size() > 1) {
                    if (event.getArguments().get(0).equalsIgnoreCase("set")) {
                        this.maddox.getCacheManager().setPrivateJoinMessage(event.getArgumentsAsString().substring(4), server.getID());
                        this.maddox.getCacheManager().setPrivateJoinMessageEnabled(true, server.getID());
                        event.reply(LanguageAPI.getValue("EventMessage-Set", server.getLanguage()).replace("<MESSAGE-TYPE>", LanguageAPI.getValue("PrivateJoinMessage", server.getLanguage())).replace("<MESSAGE>", event.getArgumentsAsString().substring(4)));
                    } else if (event.getArguments().get(0).equalsIgnoreCase("enable")) {
                        if (!this.maddox.getCacheManager().isPrivateJoinMessageEnabled(server.getID())) {
                            this.maddox.getCacheManager().setPrivateJoinMessageEnabled(true, server.getID());
                            event.reply(LanguageAPI.getValue("MessageEnabled", server.getLanguage()).replace("<MESSAGE-TYPE>", LanguageAPI.getValue("PrivateJoinMessage", server.getLanguage())));
                        } else {
                            event.reply(LanguageAPI.getValue("NoChange", server.getLanguage()));
                        }
                    } else if (event.getArguments().get(0).equalsIgnoreCase("disable")) {
                        if (this.maddox.getCacheManager().isPrivateJoinMessageEnabled(server.getID())) {
                            this.maddox.getCacheManager().setPrivateJoinMessageEnabled(false, server.getID());
                            event.reply(LanguageAPI.getValue("MessageDisabled", server.getLanguage()).replace("<MESSAGE-TYPE>", LanguageAPI.getValue("PrivateJoinMessage", server.getLanguage())));
                        } else {
                            event.reply(LanguageAPI.getValue("NoChange", server.getLanguage()));
                        }
                    }
                } else if (event.getArguments().size() == 1) {
                    if (event.getArguments().get(0).equalsIgnoreCase("set")) {
                        event.reply(LanguageAPI.getValue("NoMessageProvided", server.getLanguage()).replace("<PREFIX>", prefix).replace("<MESSAGE-TYPE>", "pjoin"));
                    }
                } else {
                    event.reply(LanguageAPI.getValue("PrivateJoinMessageArgumentsMissing", server.getLanguage()).replace("<PREFIX>", prefix));
                }
            }
        }
    }
}
