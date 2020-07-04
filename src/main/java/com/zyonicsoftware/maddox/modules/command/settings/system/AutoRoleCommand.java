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

public class AutoRoleCommand extends Command {

    private final Maddox maddox;

    public AutoRoleCommand(Maddox maddox) {
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
    protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
        if (event.getArguments().size() > 1) {
            if (event.getArguments().get(0).equalsIgnoreCase("add")) {
                if (!event.getRoleMentions().isEmpty()) {
                    StringBuilder rolesInString = new StringBuilder().append(this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID()));
                    event.getRoleMentions().forEach(role -> {
                        rolesInString.append(role.getId()).append(";");
                    });
                    this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesInString.toString(), server.getID());
                    event.reply(LanguageAPI.getValue("AutoRole-Response-1", server.getLanguage()));
                }
            } else if (event.getArguments().get(0).equalsIgnoreCase("remove")) {
                if (!event.getRoleMentions().isEmpty()) {
                    final String[] rolesInString = {this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID())};
                    event.getRoleMentions().forEach(role -> {
                        rolesInString[0] = rolesInString[0].replace(role.getId() + ";", "");
                    });
                    this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesInString[0], server.getID());
                    event.reply(LanguageAPI.getValue("AutoRole-Response-2", server.getLanguage()));
                }
            }
        }
    }
}
