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
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class LanguageCommand extends Command {

    private final Maddox maddox;

    public LanguageCommand(final Maddox maddox) {
        this.setName("lang");
        this.setCategory("Settings-Category");
        this.setSyntax("Language-Syntax");
        this.setDescription("Language-Desc");
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.ADMINISTRATOR);
        this.setGetValuesFromLanguageYAML(true);
        this.setShowInHelp(true);
        this.maddox = maddox;
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (sender.hasPermission(Permission.ADMINISTRATOR)) {
            if (!event.getArguments().isEmpty()) {
                if (event.getArguments().get(0).equalsIgnoreCase("list")) {
                    event.reply(new EmbedBuilder()
                            .setTitle(LanguageAPI.getValue("Language-List-Header", server.getLanguage()))
                            .addField(LanguageAPI.getValue("Language-List-SubHeader", server.getLanguage()), this.maddox.getSupportedLanguages(), false)
                            .setColor(this.maddox.getDefaultColor())
                            .build()
                    );
                } else if (this.maddox.getSupportedLanguages().contains(event.getArguments().get(0).toUpperCase())) {
                    final String selectedLanguage = event.getArguments().get(0).toUpperCase();
                    server.setLanguage(selectedLanguage);
                    event.reply(LanguageAPI.getValue("Language-Set", selectedLanguage).replace("<SERVER>", server.getName()).replace("<LANGUAGE>", selectedLanguage));
                }
            }
        } else {
            event.deleteEventMessage();
        }
    }
}
