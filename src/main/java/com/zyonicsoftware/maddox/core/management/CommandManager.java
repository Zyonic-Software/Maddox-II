/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.management;

import com.zyonicsoftware.maddox.core.engine.handling.command.CommandHandler;
import com.zyonicsoftware.maddox.core.main.Maddox;
import com.zyonicsoftware.maddox.modules.command.information.HelpCommand;

public class CommandManager {

    private final Maddox maddox;

    public CommandManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public void registerCommands(CommandHandler commandHandler) {

        //Standard Commands

        commandHandler.registerCommand(new HelpCommand(this.maddox));

        //Add further Commands here
    }


}
