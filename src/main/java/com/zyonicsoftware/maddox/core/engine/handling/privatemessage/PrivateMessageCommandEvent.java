/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.privatemessage;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class PrivateMessageCommandEvent {

    private final PrivateMessageReceivedEvent event;
    private final PrivateMessageCommand command;
    private final Maddox maddox;

    public PrivateMessageCommandEvent(final PrivateMessageCommand command, final PrivateMessageReceivedEvent event, final Maddox maddox) {
        this.event = event;
        this.command = command;
        this.maddox = maddox;
    }

    public PrivateMessageCommand getCommand() {
        return this.command;
    }

    public PrivateMessageReceivedEvent getEvent() {
        return this.event;
    }

    public User getAuthor() {
        return this.event.getAuthor();
    }

    public PrivateChannel getChannel() {
        return this.event.getChannel();
    }

    public Message getMessage() {
        return this.event.getMessage();
    }

    public JDA getJDA() {
        return this.event.getJDA();
    }

    public void reply(final String pMessage) {
        this.getChannel().sendMessage(pMessage).queue();
    }

    public void reply(final MessageEmbed messageEmbed) {
        this.getChannel().sendMessage(messageEmbed).queue();
    }

    public Message returnReply(final String pMessage) {
        return this.getChannel().sendMessage(pMessage).complete();
    }

    public Message returnReply(final MessageEmbed messageEmbed) {
        return this.getChannel().sendMessage(messageEmbed).complete();
    }

    public ArrayList<String> getArguments() {
        String arguments;
        arguments = this.event.getMessage().getContentRaw().substring(this.command.getName().length() + this.maddox.getDefaultPrefix().length());
        if (this.event.getMessage().getContentRaw().startsWith(this.maddox.getDefaultPrefix() + " ")) {
            arguments = this.event.getMessage().getContentRaw().substring(this.command.getName().length() + this.maddox.getDefaultPrefix().length() + 1);
        }

        if (arguments.length() > 0) {
            arguments = arguments.substring(1);
            final String[] args = arguments.split(" ");
            return (ArrayList<String>) Arrays.asList(args);
        } else {
            return new ArrayList<>();
        }
    }


}
