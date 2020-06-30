/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.helpbuilder;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.objects.Sender;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpBuilder {

    private final Maddox maddox;

    public HelpBuilder(Maddox maddox) {
        this.maddox = maddox;
    }

    public MessageEmbed assembleHelp(Sender sender, String prefix, String language) {
        HashMap<String, Command> commands = this.maddox.getCommandHandler().getCommands();
        HashMap<String, Command> specefiedPrefixCommands = this.maddox.getCommandHandler().getSpecificPrefixCommands();
        HashMap<String, ArrayList<Command>> sortedCommands = new HashMap<>();
        HashMap<String, ArrayList<Command>> sortedSpecefiedPrefixCommands = new HashMap<>();


        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("**" + this.maddox.getName() + " Help**").setThumbnail(sender.getCurrentJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setColor(maddox.getDefaultColor());

        //Sorting commands into specefied Categorys
        commands.forEach((name, command) -> {
            if (command.ShowInHelp()) {
                if (command.getCommandHelpViewPermission() < sender.getCommandHelpViewPermissionValue()) {
                    if (command.isGetValuesFromLanguageYaml()) {//if Gets Values from Language-Yaml
                        if (sortedCommands.containsKey(LanguageAPI.getValue(command.getCategory(), language))) {
                            sortedCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                        } else {
                            sortedCommands.put(LanguageAPI.getValue(command.getCategory(), language), new ArrayList<Command>());
                            sortedCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                        }
                    } else {
                        if (sortedCommands.containsKey(command.getCategory())) {
                            sortedCommands.get(command.getCategory()).add(command);
                        } else {
                            sortedCommands.put(command.getCategory(), new ArrayList<Command>());
                            sortedCommands.get(command.getCategory()).add(command);
                        }
                    }
                }
            }
        });

        //generating list
        sortedCommands.forEach((categoryName, selectedCommands) -> {
            if (!categoryName.equals("none")) {

                StringBuilder commandShow = new StringBuilder();

                selectedCommands.forEach(command -> {
                    commandShow.append("``" + prefix + command.getName() + "`` ");
                });

                embedBuilder.addField("**" + categoryName + " • " + selectedCommands.size() + "**", commandShow.toString(), true);
            }
        });


        //The same for SpecefiedPrefixCommands
        specefiedPrefixCommands.forEach((name, command) -> {
            if (command.getCommandHelpViewPermission() < sender.getCommandHelpViewPermissionValue()) {
                if (command.isGetValuesFromLanguageYaml()) {//if Gets Values from Language-Yaml
                    if (sortedCommands.containsKey(LanguageAPI.getValue(command.getCategory(), language))) {
                        sortedSpecefiedPrefixCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                    } else {
                        sortedSpecefiedPrefixCommands.put(LanguageAPI.getValue(command.getCategory(), language), new ArrayList<Command>());
                        sortedSpecefiedPrefixCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                    }
                } else {
                    if (command.ShowInHelp()) {
                        if (sortedSpecefiedPrefixCommands.containsKey(command.getCategory())) {
                            sortedSpecefiedPrefixCommands.get(command.getCategory()).add(command);
                        } else {
                            sortedSpecefiedPrefixCommands.put(command.getCategory(), new ArrayList<Command>());
                            sortedSpecefiedPrefixCommands.get(command.getCategory()).add(command);
                        }
                    }
                }
            }
        });

        //generating list
        sortedSpecefiedPrefixCommands.forEach((categoryName, selectedCommands) -> {
            if (!categoryName.equals("none")) {

                StringBuilder commandShow = new StringBuilder();

                selectedCommands.forEach(command -> {
                    commandShow.append("``" + prefix + command.getName() + "`` ");
                });

                embedBuilder.addField("**" + categoryName + " • " + selectedCommands.size() + "**", commandShow.toString(), true);
            }
        });

        //showing unsorted commands
        if (sortedSpecefiedPrefixCommands.get("none") != null) {
            StringBuilder commandsWithoutCategoryShow = new StringBuilder();

            sortedSpecefiedPrefixCommands.get("none").forEach(command -> {
                commandsWithoutCategoryShow.append("``" + prefix + command.getName() + "`` ");
            });

            embedBuilder.addField("**Other • " + sortedSpecefiedPrefixCommands.get("none").size() + "**", commandsWithoutCategoryShow.toString(), true);
        }

        return embedBuilder.build();
    }

    public MessageEmbed generateCommandHelp(Command command, String prefix) {

        if (command.isGetValuesFromLanguageYaml()) {
            return new EmbedBuilder()
                    .setTitle("**" + command.getName() + " Help**")
                    .setColor(this.maddox.getDefaultColor())
                    .addField(prefix + LanguageAPI.getValue(command.getSyntax()), LanguageAPI.getValue(command.getDescription()), false)
                    .build();
        } else {
            return new EmbedBuilder()
                    .setTitle("**" + command.getName() + " Help**")
                    .setColor(this.maddox.getDefaultColor())
                    .addField(prefix + command.getSyntax(), command.getDescription(), false)
                    .build();
        }
    }
}
