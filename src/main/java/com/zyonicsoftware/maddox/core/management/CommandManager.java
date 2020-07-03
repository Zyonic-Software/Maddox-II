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
import com.zyonicsoftware.maddox.modules.command.settings.system.SetPrefixCommand;
import com.zyonicsoftware.maddox.modules.command.sysadmin.ForceAddCommand;

public class CommandManager {

    private final Maddox maddox;

    public CommandManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public void registerCommands(CommandHandler commandHandler) {

        //Sys-Admin Comman

        commandHandler.registerCommand(new ForceAddCommand(this.maddox));

        //Guild-Admin Commands

        commandHandler.registerCommand(new LanguageCommand(this.maddox));
        commandHandler.registerCommand(new SetPrefixCommand());

        //Standard Commands

        ///Infos
        commandHandler.registerCommand(new HelpCommand(this.maddox));
        commandHandler.registerCommand(new BotInfoCommand(this.maddox));
        //Add further Commands here
    }


}
