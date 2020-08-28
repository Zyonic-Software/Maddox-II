/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.command;

import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandHandler {

    private final Maddox maddox;
    private final HashMap<String, Command> commands;
    private final HashMap<String, Command> specificPrefixCommands;

    public CommandHandler(final Maddox maddox) {
        this.maddox = maddox;

        this.commands = new HashMap<>();
        this.specificPrefixCommands = new HashMap<>();
    }

    /**
     * Registers a Command to the Handling-List.
     *
     * @param command
     */
    public void registerCommand(final Command command) {
        if (command.getSpecificPrefix() == null) {
            this.commands.put(command.getName(), command);
        } else {
            this.specificPrefixCommands.put(command.getSpecificPrefix() + command.getName(), command);
        }
    }

    /**
     * Registers multible Commands to the Handling-list at once.
     *
     * @param commands
     */
    public void registerCommands(final Command... commands) {
        for (int i = 0; i < commands.length; i++) {
            final Command command = commands[i];
            if (command.getSpecificPrefix() == null) {
                this.commands.put(command.getName(), command);
            } else {
                this.specificPrefixCommands.put(command.getSpecificPrefix() + command.getName(), command);
            }
        }
    }

    /**
     * Is used to process the GuildMessageReceivedEvent and filter out the useful info.
     * <p>
     * Seperates between different Prefixes and if Prefixes include Spaces or if they are Tags, Executes Command accordingly.
     * <p>
     * Chooses from a HashMap with all Command-Objects inside
     *
     * @param event
     * @param prefix
     * @param messageContent
     */
    public void handle(final GuildMessageReceivedEvent event, String prefix, String messageContent) {

        if (messageContent.isEmpty()) {
            return;
        }

        if (messageContent.startsWith(prefix + " ")) {

            String[] seperatedStrings = messageContent.substring(prefix.length() + 1).split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase().substring(1));
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        } else if (messageContent.startsWith("<@" + event.getJDA().getSelfUser().getId() + "> ") || messageContent.startsWith("<@!" + event.getJDA().getSelfUser().getId() + "> ")) {

            if (messageContent.startsWith("<@" + event.getJDA().getSelfUser().getId() + ">")) {
                messageContent = messageContent.substring(2 + event.getJDA().getSelfUser().getId().length() + 2);
                prefix = "<@" + event.getJDA().getSelfUser().getId() + ">";
            } else if (messageContent.startsWith("<@!" + event.getJDA().getSelfUser().getId() + ">")) {
                messageContent = messageContent.substring(3 + event.getJDA().getSelfUser().getId().length() + 2);
                prefix = "<@!" + event.getJDA().getSelfUser().getId() + ">";
            }

            String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        } else {
            final String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        }
    }


    /**
     * Is used to process the GuildMessageUpdateEvent and filter out the useful info.
     * <p>
     * Seperates between different Prefixes and if Prefixes include Spaces or if they are Tags, Executes Command accordingly.
     * <p>
     * Chooses from a HashMap with all Command-Objects inside
     *
     * @param event
     * @param prefix
     * @param messageContent
     */
    public void handle(final GuildMessageUpdateEvent event, String prefix, String messageContent) {

        if (messageContent.isEmpty()) {
            return;
        }

        if (messageContent.startsWith(prefix + " ")) {

            String[] seperatedStrings = messageContent.substring(prefix.length() + 1).split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase().substring(1));
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

        } else if (messageContent.startsWith(prefix)) {

            String[] seperatedStrings = messageContent.substring(prefix.length()).split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        } else if (messageContent.startsWith("<@" + event.getJDA().getSelfUser().getId() + "> ") || messageContent.startsWith("<@!" + event.getJDA().getSelfUser().getId() + "> ")) {

            if (messageContent.startsWith("<@" + event.getJDA().getSelfUser().getId() + ">")) {
                messageContent = messageContent.substring(2 + event.getJDA().getSelfUser().getId().length() + 2);
                prefix = "<@" + event.getJDA().getSelfUser().getId() + ">";
            } else if (messageContent.startsWith("<@!" + event.getJDA().getSelfUser().getId() + ">")) {
                messageContent = messageContent.substring(3 + event.getJDA().getSelfUser().getId().length() + 2);
                prefix = "<@!" + event.getJDA().getSelfUser().getId() + ">";
            }

            String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.commands.get(seperatedStrings[0].toLowerCase());

                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, prefix), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }

            seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        } else {
            final String[] seperatedStrings = messageContent.split(" ");

            if (seperatedStrings.length > 0) {

                final Command selectedCommand = this.specificPrefixCommands.get(seperatedStrings[0].toLowerCase());
                if (selectedCommand != null) {
                    if (this.maddox.isMySQLConnected()) {
                        if (this.maddox.areCommandsToggleable()) {
                            if (selectedCommand.isToggleable()) {
                                if (!this.maddox.getCommandToggleManager().getCommandsForToggling(event.getGuild()).contains(selectedCommand.getName())) {
                                    return;
                                }
                            }
                        }
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix, this.maddox.getCacheManager()));
                        return;
                    } else {
                        selectedCommand.execute(new CommandEvent(selectedCommand, event, selectedCommand.getSpecificPrefix()), new MaddoxMember(event.getMember()), new MaddoxGuild(event.getGuild(), prefix));
                        return;
                    }
                }
            }
        }
    }

    public HashMap<String, Command> getCommands() {
        return this.commands;
    }

    public HashMap<String, Command> getSpecificPrefixCommands() {
        return this.specificPrefixCommands;
    }

    /**
     * Returns all the different Command-Categorys (Important for automated help-building)
     *
     * @return
     */
    public ArrayList<String> getCategories() {
        final ArrayList<String> categories = new ArrayList<>();
        this.commands.forEach((name, command) -> {
            if (!categories.contains(command.getCategory())) {
                categories.add(command.getCategory());
            }
        });
        return categories;
    }
}
