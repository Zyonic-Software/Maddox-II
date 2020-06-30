/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.objects;

import com.zyonicsoftware.maddox.core.mysql.MySQLHandler;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.entities.Guild;

public class DiscordServer {

    private final Guild guild;
    private String prefix;
    private String language;
    private MySQLHandler mySQLHandler;

    public DiscordServer(Guild guild, String prefix) {
        this.guild = guild;
        this.prefix = prefix;
        this.language = LanguageAPI.getLanguageHandler().getLanguage();
    }

    public DiscordServer(Guild guild, String prefix, MySQLHandler mySQLHandler) {
        this.guild = guild;
        this.prefix = prefix;
        this.language = mySQLHandler.getServerLanguage(guild.getId());
        this.mySQLHandler = mySQLHandler;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getServerName() {
        return this.guild.getName();
    }

    public String getID() {
        return this.guild.getId();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        mySQLHandler.setLanguage(language, this.guild.getId());
        this.language = language;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        mySQLHandler.setPrefix(prefix, this.getID());
    }
}
