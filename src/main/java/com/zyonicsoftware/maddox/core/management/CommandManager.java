/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.management;

import com.zyonicsoftware.maddox.core.engine.handling.command.CommandHandler;
import com.zyonicsoftware.maddox.core.main.Maddox;
import com.zyonicsoftware.maddox.modules.command.information.BotInfoCommand;
import com.zyonicsoftware.maddox.modules.command.information.HelpCommand;
import com.zyonicsoftware.maddox.modules.command.settings.messages.LanguageCommand;
import com.zyonicsoftware.maddox.modules.command.settings.system.AutoRoleCommand;
import com.zyonicsoftware.maddox.modules.command.settings.system.SetPrefixCommand;
import com.zyonicsoftware.maddox.modules.command.sysadmin.ForceAddCommand;

public class CommandManager {

    private final Maddox maddox;

    public CommandManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public void registerCommands(CommandHandler commandHandler) {

        commandHandler.registerCommands(
                //Sys-Admin
                new ForceAddCommand(this.maddox),
                //Guild-Admin
                new LanguageCommand(this.maddox),
                new SetPrefixCommand(),
                new AutoRoleCommand(this.maddox),
                //Normal
                ///Information
                new HelpCommand(this.maddox),
                new BotInfoCommand(this.maddox)
                //Add further commands here
        );
    }


}
