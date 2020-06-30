/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.settings;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.DiscordServer;
import com.zyonicsoftware.maddox.core.engine.objects.Sender;

public class LanguageCommand extends Command {

    public LanguageCommand(){
        this.setName("lang");
        this.setCategory("Language-Category");
        this.setSyntax("Language-Syntax");
        this.setDescription("Language-Desc");
        this.setAllowExecutionOnMessageEdit(true);
        this.setCommandHelpViewPermission(CommandHelpViewPermission.ADMINISTRATOR);
        this.setGetValuesFromLanguageYAML(true);
        this.setShowInHelp(true);
    }

    @Override
    protected void execute(CommandEvent event, Sender sender, DiscordServer server) {

    }
}
