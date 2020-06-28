/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.engine.handling.privatemessage;

import com.zyonicsoftware.maddox.main.Maddox;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class PrivateMessageCommandEvent {

    private PrivateMessageReceivedEvent event;
    private PrivateMessageCommand command;
    private Maddox maddox;

    public PrivateMessageCommandEvent(PrivateMessageCommand command, PrivateMessageReceivedEvent event, Maddox maddox) {
        this.event = event;
        this.command = command;
        this.maddox = maddox;
    }

    public PrivateMessageCommand getCommand() {
        return command;
    }

    public PrivateMessageReceivedEvent getEvent() {
        return event;
    }

    public User getAuthor() {
        return event.getAuthor();
    }

    public PrivateChannel getChannel() {
        return event.getChannel();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public JDA getJDA() {
        return event.getJDA();
    }

    public void reply(String pMessage) {
        this.getChannel().sendMessage(pMessage).queue();
    }

    public void reply(MessageEmbed messageEmbed) {
        this.getChannel().sendMessage(messageEmbed).queue();
    }

    public Message returnReply(String pMessage) {
        return this.getChannel().sendMessage(pMessage).complete();
    }

    public Message returnReply(MessageEmbed messageEmbed) {
        return this.getChannel().sendMessage(messageEmbed).complete();
    }

    public ArrayList<String> getArguments() {
        String arguments;
        arguments = event.getMessage().getContentRaw().substring(command.getName().length() + this.maddox.getDefaultPrefix().length());
        if (event.getMessage().getContentRaw().startsWith(this.maddox.getDefaultPrefix() + " ")) {
            arguments = event.getMessage().getContentRaw().substring(command.getName().length() + this.maddox.getDefaultPrefix().length() + 1);
        }

        if (arguments.length() > 0) {
            arguments = arguments.substring(1);
            String[] args = arguments.split(" ");
            return (ArrayList<String>) Arrays.asList(args);
        } else {
            return new ArrayList<String>();
        }
    }


}
