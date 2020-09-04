/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.helpbuilder;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpBuilder {

    private final Maddox maddox;

    public HelpBuilder(final Maddox maddox) {
        this.maddox = maddox;
    }

    public MessageEmbed assembleHelp(final MaddoxMember maddoxMember, final String prefix, final String language) {
        final HashMap<String, Command> commands = this.maddox.getCommandHandler().getCommands();
        final HashMap<String, Command> specefiedPrefixCommands = this.maddox.getCommandHandler().getSpecificPrefixCommands();
        final HashMap<String, ArrayList<Command>> sortedCommands = new HashMap<>();
        final HashMap<String, ArrayList<Command>> sortedSpecefiedPrefixCommands = new HashMap<>();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("**" + this.maddox.getName() + " Help**").setThumbnail(maddoxMember.getCurrentJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setColor(this.maddox.getDefaultColor());

        //Sorting commands into specefied Categorys
        commands.forEach((name, command) -> {
            if (command.ShowInHelp()) {
                if (command.getCommandHelpViewPermissionValue() <= maddoxMember.getCommandHelpViewPermissionValue()) {
                    if (command.isGetValuesFromLanguageYaml()) {//if Gets Values from Language-Yaml
                        if (sortedCommands.containsKey(LanguageAPI.getValue(command.getCategory(), language))) {
                            sortedCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                        } else {
                            sortedCommands.put(LanguageAPI.getValue(command.getCategory(), language), new ArrayList<>());
                            sortedCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                        }
                    } else {
                        if (sortedCommands.containsKey(command.getCategory())) {
                            sortedCommands.get(command.getCategory()).add(command);
                        } else {
                            sortedCommands.put(command.getCategory(), new ArrayList<>());
                            sortedCommands.get(command.getCategory()).add(command);
                        }
                    }
                }
            }
        });

        //generating list
        sortedCommands.forEach((categoryName, selectedCommands) -> {
            if (!categoryName.equals("none")) {

                final StringBuilder commandShow = new StringBuilder();

                selectedCommands.forEach(command -> {
                    commandShow.append("``" + prefix + command.getName() + "`` ");
                });

                embedBuilder.addField("**" + categoryName + " • " + selectedCommands.size() + "**", commandShow.toString(), false);
            }
        });


        //The same for SpecefiedPrefixCommands
        specefiedPrefixCommands.forEach((name, command) -> {
            if (command.getCommandHelpViewPermissionValue() <= maddoxMember.getCommandHelpViewPermissionValue()) {
                if (command.isGetValuesFromLanguageYaml()) {//if Gets Values from Language-Yaml
                    if (sortedCommands.containsKey(LanguageAPI.getValue(command.getCategory(), language))) {
                        sortedSpecefiedPrefixCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                    } else {
                        sortedSpecefiedPrefixCommands.put(LanguageAPI.getValue(command.getCategory(), language), new ArrayList<>());
                        sortedSpecefiedPrefixCommands.get(LanguageAPI.getValue(command.getCategory(), language)).add(command);
                    }
                } else {
                    if (command.ShowInHelp()) {
                        if (sortedSpecefiedPrefixCommands.containsKey(command.getCategory())) {
                            sortedSpecefiedPrefixCommands.get(command.getCategory()).add(command);
                        } else {
                            sortedSpecefiedPrefixCommands.put(command.getCategory(), new ArrayList<>());
                            sortedSpecefiedPrefixCommands.get(command.getCategory()).add(command);
                        }
                    }
                }
            }
        });

        //generating list
        sortedSpecefiedPrefixCommands.forEach((categoryName, selectedCommands) -> {
            if (!categoryName.equals("none")) {

                final StringBuilder commandShow = new StringBuilder();

                selectedCommands.forEach(command -> {
                    commandShow.append("``" + prefix + command.getName() + "`` ");
                });

                embedBuilder.addField("**" + categoryName + " • " + selectedCommands.size() + "**", commandShow.toString(), false);
            }
        });

        //showing unsorted commands
        if (sortedSpecefiedPrefixCommands.get("none") != null) {
            final StringBuilder commandsWithoutCategoryShow = new StringBuilder();

            sortedSpecefiedPrefixCommands.get("none").forEach(command -> {
                commandsWithoutCategoryShow.append("``" + prefix + command.getName() + "`` ");
            });

            embedBuilder.addField("**Other • " + sortedSpecefiedPrefixCommands.get("none").size() + "**", commandsWithoutCategoryShow.toString(), false);
        }

        return embedBuilder.build();
    }

    public MessageEmbed generateCommandHelp(final Command command, final String prefix, final MaddoxMember maddoxMember, final MaddoxGuild server) {

        if (command.isShowExtendedHelp()) {
            if (command.isGetValuesFromLanguageYaml()) {
                if (command.getDescription() != null) {
                    return new EmbedBuilder()
                            .setTitle("**" + prefix + command.getName() + "**")
                            .setColor(this.maddox.getDefaultColor())
                            .addField(LanguageAPI.getValue(command.getSyntax(), server.getLanguage()).replace("<PREFIX>", prefix), LanguageAPI.getValue(command.getDescription(), server.getLanguage()).replace("<PREFIX>", prefix), false)
                            .build();
                } else {
                    return new EmbedBuilder()
                            .setTitle("**" + prefix + command.getName() + "**")
                            .setColor(this.maddox.getDefaultColor())
                            .addField("No Description defined", "Please either Disable the .showExtendedHelp option in your CommandClass or add a Description & a Syntax-Description", false)
                            .setThumbnail(maddoxMember.getCurrentJDA().getSelfUser().getAvatarUrl())
                            .build();
                }
            } else {
                if (command.getDescription() != null) {
                    return new EmbedBuilder()
                            .setTitle("**" + prefix + command.getName() + "**")
                            .setColor(this.maddox.getDefaultColor())
                            .addField(command.getSyntax().replace("<PREFIX>", prefix), command.getDescription().replace("<PREFIX>", prefix), false)
                            .setThumbnail(maddoxMember.getCurrentJDA().getSelfUser().getAvatarUrl())
                            .build();
                } else {

                    return new EmbedBuilder()
                            .setTitle("**" + prefix + command.getName() + "**")
                            .setColor(this.maddox.getDefaultColor())
                            .addField("No Description defined", "Please either Disable the .showExtendedHelp option in your CommandClass or add a Description & a Syntax-Description", false)
                            .setThumbnail(maddoxMember.getCurrentJDA().getSelfUser().getAvatarUrl())
                            .build();
                }
            }
        } else {
            return null;
        }
    }
}
