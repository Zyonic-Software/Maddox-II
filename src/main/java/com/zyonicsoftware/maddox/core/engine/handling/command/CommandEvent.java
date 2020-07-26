/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.command;

import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEvent {

    private final Command command;
    private GuildMessageReceivedEvent receivedEvent;
    private GuildMessageUpdateEvent updateEvent;
    private final MaddoxGuild server;
    private final String prefix;


    //Constructors Differenciating between Events, Command Event also gets Triggered on MessageEdit, if enabled in Command
    public CommandEvent(final Command command, final GuildMessageReceivedEvent receivedEvent, final String prefix) {
        this.command = command;
        this.receivedEvent = receivedEvent;
        this.prefix = prefix;
        this.server = new MaddoxGuild(receivedEvent.getGuild(), prefix);
    }

    public CommandEvent(final Command command, final GuildMessageUpdateEvent updateEvent, final String prefix) {
        this.command = command;
        this.updateEvent = updateEvent;
        this.prefix = prefix;
        this.server = new MaddoxGuild(updateEvent.getGuild(), prefix);
    }


    public Command getCommand() {
        return this.command;
    }

    public TextChannel getChannel() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getChannel();
        } else {
            return this.updateEvent.getChannel();
        }
    }

    public Category getCategory() {
        return this.getChannel().getParent();
    }

    public User getUser() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getMember().getUser();
        } else {
            return this.updateEvent.getMember().getUser();
        }
    }

    public MaddoxMember getSender() {
        if (this.receivedEvent != null) {
            return new MaddoxMember(this.receivedEvent.getMember());
        } else {
            return new MaddoxMember(this.updateEvent.getMember());
        }
    }

    public User getAuthor() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getAuthor();
        } else {
            return this.updateEvent.getAuthor();
        }
    }

    public Guild getGuild() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getGuild();
        } else {
            return this.updateEvent.getGuild();
        }
    }

    public JDA getJDA() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getJDA();
        } else {
            return this.updateEvent.getJDA();
        }
    }

    public String getMessageID() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getMessageId();
        } else {
            return this.updateEvent.getMessageId();
        }
    }

    public boolean isWebhookMessage() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.isWebhookMessage();
        } else {
            return false;
        }
    }

    public Message getMessage() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getMessage();
        } else {
            return this.updateEvent.getMessage();
        }
    }

    public String getMessageText() {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getMessage().getContentRaw();
        } else {
            return this.updateEvent.getMessage().getContentRaw();
        }
    }

    public GuildMessageReceivedEvent getMessageReceivedEvent() {
        if (this.receivedEvent != null) {
            return this.receivedEvent;
        } else {
            return null;
        }
    }

    public GuildMessageUpdateEvent getMessageUpdateEvent() {
        if (this.updateEvent != null) {
            return this.updateEvent;
        } else {
            return null;
        }
    }


    public String getPrefix() {
        return this.prefix;
    }

    public boolean equals(final CommandEvent commandEvent) {
        if (commandEvent.getMessageReceivedEvent() != null) {
            return this.receivedEvent.equals(commandEvent.getMessageReceivedEvent());
        } else {
            return this.updateEvent.equals(commandEvent.getMessageUpdateEvent());
        }
    }

    public String getArgumentsAsString() {
        if (!this.getArguments().isEmpty()) {
            if (this.receivedEvent != null) {
                return this.receivedEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length() + 1);
            } else {
                return this.updateEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length() + 1);
            }
        } else {
            return "";
        }
    }

    public List<String> getArguments() {
        String arguments;
        if (this.receivedEvent != null) {
            arguments = this.receivedEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length());
            if (this.receivedEvent.getMessage().getContentRaw().startsWith(this.prefix + " ")) {
                arguments = this.receivedEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length() + 1);
            }
        } else {
            arguments = this.updateEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length());
            if (this.updateEvent.getMessage().getContentRaw().startsWith(this.prefix + " ")) {
                arguments = this.updateEvent.getMessage().getContentRaw().substring(this.command.getName().length() + this.prefix.length() + 1);
            }
        }
        if (arguments.length() > 0) {
            arguments = arguments.substring(1);
            final List<String> argslist = new ArrayList<>(Arrays.asList(arguments.split(" ")));
            argslist.removeIf(String::isEmpty);

            if (argslist.isEmpty()) {
                return new ArrayList<>();
            }

            return argslist;
        } else {
            return new ArrayList<>();
        }
    }

    public String getExecutorAvatar(final int imageDimension) {
        if (this.receivedEvent != null) {
            return "https://cdn.discordapp.com/avatars/" + this.receivedEvent.getMember().getId() + "/" + this.receivedEvent.getMember().getUser().getAvatarId() + ".png?size=" + imageDimension;
        } else {
            return "https://cdn.discordapp.com/avatars/" + this.updateEvent.getMember().getId() + "/" + this.updateEvent.getMember().getUser().getAvatarId() + ".png?size=" + imageDimension;
        }
    }

    public List<MaddoxMember> getMentions() {
        final List<String> memberIDs = new ArrayList<>();
        final List<MaddoxMember> members = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<@") && argument.endsWith(">")) {
                argument = argument.replace("<@", "").replace(">", "").replace("!", "");
                memberIDs.add(argument);
            }
        });

        memberIDs.forEach(userID -> {
            try {
                final Member member = this.getGuild().retrieveMemberById(userID).complete(true);
                if (member != null) {
                    members.add(new MaddoxMember(member));
                }
            } catch (final Exception e) {

            }
        });

        return members;
    }

    public List<String> getMentionsAsIDs() {
        final List<String> memberIDs = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<@") && argument.endsWith(">")) {
                argument = argument.replace("<@", "").replace(">", "").replace("!", "");
                memberIDs.add(argument);
            }
        });

        return memberIDs;
    }

    public List<TextChannel> getTextChannelMentions() {
        final List<String> textChannelIDs = new ArrayList<>();
        final List<TextChannel> textChannels = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<#") && argument.endsWith(">")) {
                argument = argument.replace("<#", "").replace(">", "").replace("!", "");
                textChannelIDs.add(argument);
            }
        });

        textChannelIDs.forEach(channelID -> {
            try {
                textChannels.add(this.getGuild().getTextChannelById(channelID));
            } catch (final Exception e) {

            }
        });

        return textChannels;
    }

    public List<Role> getRoleMentions() {
        final List<String> roleIDs = new ArrayList<>();
        final List<Role> roles = new ArrayList<>();

        this.getArguments().forEach(argument -> {
            if (argument.startsWith("<@&") && argument.endsWith(">")) {
                argument = argument.replace("<@&", "").replace(">", "").replace("!", "");
                roleIDs.add(argument);
            }
        });

        roleIDs.forEach(roleid -> {
            try {
                roles.add(this.getGuild().getRoleById(roleid));
            } catch (final Exception e) {
            }
        });

        return roles;
    }


    //New Methods

    public void reply(final String pMessage) {
        if (this.receivedEvent != null) {
            this.receivedEvent.getChannel().sendMessage(pMessage).queue();
        } else {
            this.updateEvent.getChannel().sendMessage(pMessage).queue();
        }
    }

    public void reply(final MessageEmbed messageEmbed) {
        if (this.receivedEvent != null) {
            this.receivedEvent.getChannel().sendMessage(messageEmbed).queue();
        } else {
            this.updateEvent.getChannel().sendMessage(messageEmbed).queue();
        }
    }

    public Message returnReply(final String pMessage) {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getChannel().sendMessage(pMessage).complete();
        } else {
            return this.updateEvent.getChannel().sendMessage(pMessage).complete();
        }
    }

    public Message returnReply(final MessageEmbed messageEmbed) {
        if (this.receivedEvent != null) {
            return this.receivedEvent.getChannel().sendMessage(messageEmbed).complete();
        } else {
            return this.updateEvent.getChannel().sendMessage(messageEmbed).complete();
        }
    }

    public void deleteEventMessage() {
        if (this.receivedEvent != null) {
            this.receivedEvent.getMessage().delete().queue();
        } else {
            this.updateEvent.getMessage().delete().queue();
        }
    }

    public MaddoxGuild getServer() {
        return this.server;
    }
}
