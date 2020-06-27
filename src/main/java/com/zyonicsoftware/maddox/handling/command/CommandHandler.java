/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.handling.command;

import com.zyonicsoftware.maddox.main.Maddox;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.util.HashMap;

public class CommandHandler {

    private final Maddox maddox;
    private HashMap<String, Command> commands;
    private HashMap<String, Command> specificPrefixCommands;

    public CommandHandler(Maddox maddox) {
        this.maddox = maddox;
    }


    public void registerCommand(Command command) {
        if (command.getSpecificPrefix() == null) {
            commands.put(command.getName(), command);
        } else {
            specificPrefixCommands.put(command.getSpecificPrefix() + command.getName(), command);
        }
    }


    public void handle(GuildMessageReceivedEvent event, String prefix, String messageContent) {

        if (messageContent.startsWith(prefix + " ")) {
            String[] seperatedStrings = messageContent.substring(prefix.length() + 1).split(" ");

            Command selectedCommand = commands.get(seperatedStrings[0]);

            selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));

            seperatedStrings = null;
            selectedCommand = null;
        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            Command selectedCommand = commands.get(seperatedStrings[0]);

            selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));

            seperatedStrings = null;
            selectedCommand = null;
        } else {
            String[] seperatedStrings = messageContent.split(" ");
            Command selectedCommand = specificPrefixCommands.get(seperatedStrings[0]);
            if (selectedCommand != null) {
                selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));
            }

            seperatedStrings = null;
            selectedCommand = null;
        }
    }


    public void handle(GuildMessageUpdateEvent event, String prefix, String messageContent) {

        if (messageContent.startsWith(prefix + " ")) {
            String[] seperatedStrings = messageContent.substring(prefix.length() + 1).split(" ");

            Command selectedCommand = commands.get(seperatedStrings[0]);

            selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));

            seperatedStrings = null;
            selectedCommand = null;
        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            Command selectedCommand = commands.get(seperatedStrings[0]);

            selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));

            seperatedStrings = null;
            selectedCommand = null;
        } else {
            String[] seperatedStrings = messageContent.split(" ");
            Command selectedCommand = specificPrefixCommands.get(seperatedStrings[0]);
            if (selectedCommand != null) {
                selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix));
            }

            seperatedStrings = null;
            selectedCommand = null;
        }
    }
}
