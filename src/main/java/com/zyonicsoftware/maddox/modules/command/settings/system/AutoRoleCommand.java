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
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class AutoRoleCommand extends Command {

    private final Maddox maddox;

    public AutoRoleCommand(final Maddox maddox) {
        this.maddox = maddox;
        this.setName("autorole");
        this.setCategory("Settings-Category");
        this.setDescription("AutoRole-Desc");
        this.setSyntax("AutoRole-Syntax");
        this.setGetValuesFromLanguageYAML(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setShowInHelp(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.MANAGE_ROLE);
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (!sender.hasPermission(Permission.MANAGE_ROLES)) {
            event.deleteEventMessage();
            return;
        }

        if (event.getArguments().size() > 1) {
            if (event.getArguments().get(0).equalsIgnoreCase("add")) {
                if (!event.getRoleMentions().isEmpty()) {
                    if (this.maddox.getAutomaticRoleManager().addRolesToAutomaticAssigning((ArrayList<Role>) event.getRoleMentions(), server)) {
                        event.reply(LanguageAPI.getValue("AutoRole-Response-1", server.getLanguage()).replace("<ROLE>", event.getRoleMentions().get(0).getName()));//Success
                    } else {
                        event.reply(LanguageAPI.getValue("NoChange", server.getLanguage()));//Didnt Change
                    }
                }
            } else if (event.getArguments().get(0).equalsIgnoreCase("remove")) {
                if (!event.getRoleMentions().isEmpty()) {
                    if (this.maddox.getAutomaticRoleManager().removeRolesFromAutomaticAssigning((ArrayList<Role>) event.getRoleMentions(), server)) {
                        event.reply(LanguageAPI.getValue("AutoRole-Response-2", server.getLanguage()).replace("<ROLE>", event.getRoleMentions().get(0).getName()));//Success
                    } else {
                        event.reply(LanguageAPI.getValue("NoChange", server.getLanguage()));//Didnt Change
                    }
                }
            }
        } else {
            event.reply(LanguageAPI.getValue("AutoRole-Response-3", server.getLanguage()) + LanguageAPI.getValue("AutoRole-Syntax", server.getLanguage()).replace("<PREFIX>", server.getPrefix()));
        }
    }
}
