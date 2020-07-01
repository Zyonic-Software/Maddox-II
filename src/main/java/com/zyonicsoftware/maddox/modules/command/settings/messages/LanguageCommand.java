/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */
package com.zyonicsoftware.maddox.modules.command.settings.messages;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.DiscordServer;
import com.zyonicsoftware.maddox.core.engine.objects.Sender;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.Permission;

public class LanguageCommand extends Command {

    private final Maddox maddox;

    public LanguageCommand(Maddox maddox) {
        this.setName("lang");
        this.setCategory("Language-Category");
        this.setSyntax("Language-Syntax");
        this.setDescription("Language-Desc");
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.ADMINISTRATOR);
        this.setGetValuesFromLanguageYAML(true);
        this.setShowInHelp(true);
        this.maddox = maddox;
    }

    @Override
    protected void execute(CommandEvent event, Sender sender, DiscordServer server) {
        if (sender.hasPermission(Permission.ADMINISTRATOR)) {
            if (!event.getArguments().isEmpty()) {
                if (event.getArguments().get(0).equalsIgnoreCase("list")) {
                    //toDo
                } else if (this.maddox.getSupportedLanguages().contains(event.getArguments().get(0).toUpperCase())) {
                    String selectedLanguage = event.getArguments().get(0).toUpperCase();
                    server.setLanguage(event.getArguments().get(0));
                    event.reply(LanguageAPI.getValue("Language-Set-1", selectedLanguage) + server.getServerName() + LanguageAPI.getValue("Language-Set-2", selectedLanguage) + selectedLanguage + LanguageAPI.getValue("Language-Set-3", selectedLanguage));
                }
            }
        } else {
            event.deleteEventMessage();
        }
    }
}