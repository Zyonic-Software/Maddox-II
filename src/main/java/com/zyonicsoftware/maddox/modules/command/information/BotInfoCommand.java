/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.information;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.EmbedBuilder;

public class BotInfoCommand extends Command {

    private final Maddox maddox;

    public BotInfoCommand(Maddox maddox) {
        this.maddox = maddox;
        this.setName("info");
        this.setCategory("Help-Category");
        this.setShowInHelp(true);
        this.setAllowExecutionOnMessageEdit(true);
        this.setGetValuesFromLanguageYAML(true);
    }

    @Override
    protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
        event.reply(new EmbedBuilder()
                .setTitle(this.maddox.getName() + " | Info", "https://github.com/Zyonic-Software/Maddox-V2")
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(this.maddox.getDefaultColor())
                .addField("Developer", "AzraAnimating", false)
                .addField("Programming-Language", "Java 14, LibericaJDK", false)
                .addField("APIs", "JDA (by DV8FromTheWorld)\nMysql- & Language-API (by Daschi1)", false)
                .setFooter("Maddox by ZyonicSoftware.com", "https://i.ibb.co/Jrn6L5Z/zy-128x128-smaller.png")
                .setDescription("Running on Maddox-V2-A-0.1.2a")
                .build()
        );
    }
}
