/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.command;

import com.zyonicsoftware.maddox.core.engine.objects.DiscordServer;
import com.zyonicsoftware.maddox.core.engine.objects.Sender;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.util.HashMap;

public class CommandHandler {

    private final Maddox maddox;
    private final HashMap<String, Command> commands;
    private final HashMap<String, Command> specificPrefixCommands;

    public CommandHandler(Maddox maddox) {
        this.maddox = maddox;

        commands = new HashMap<>();
        specificPrefixCommands = new HashMap<>();
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

            if (seperatedStrings.length > 0) {

                Command selectedCommand = commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            if (seperatedStrings.length > 0) {

                Command selectedCommand = commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        } else {
            String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                Command selectedCommand = specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        }
    }


    public void handle(GuildMessageUpdateEvent event, String prefix, String messageContent) {

        if (messageContent.startsWith(prefix + " ")) {

            String[] seperatedStrings = messageContent.substring(prefix.length() + 1).split(" ");

            if (seperatedStrings.length > 0) {

                Command selectedCommand = commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            if (seperatedStrings.length > 0) {

                Command selectedCommand = commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        } else {
            String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                Command selectedCommand = specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix, this.maddox.getMySQLHandler()));
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new Sender(event.getMember()), new DiscordServer(event.getGuild(), prefix));
                    }
                }
            }
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public HashMap<String, Command> getSpecificPrefixCommands() {
        return specificPrefixCommands;
    }
}
