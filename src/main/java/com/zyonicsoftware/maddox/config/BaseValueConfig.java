/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.config;

import java.awt.*;

public class BaseValueConfig {
    private String token;
    private int amountShards;
    private String defaultPrefix;
    private Color defaultColor;
    private String defaultBotName;
    private String botAdministrator;
    private boolean isMysql;
    private boolean commandsToggleable;

    public void setToken(Object token) {
        this.token = (String) token;
    }

    public void setAmountShards(Object amountShards) {
        this.amountShards = (int) amountShards;
    }

    public void setDefaultColor(Object defaultColor) {
        this.defaultColor = Color.decode((String) defaultColor);
    }

    public void setDefaultPrefix(Object defaultPrefix) {
        this.defaultPrefix = (String) defaultPrefix;
    }

    public void setDefaultBotName(Object defaultBotName) {
        this.defaultBotName = (String) defaultBotName;
    }

    public void setMysql(Object mysql) {
        isMysql = (boolean) mysql;
    }

    public void setBotAdministrator(Object botAdministrator) {
        this.botAdministrator = (String) botAdministrator;
    }

    public void setCommandsToggleable(Object commandsToggleable) {
        this.commandsToggleable = (boolean) commandsToggleable;
    }

    public String getToken() {
        return token;
    }

    public int getAmountShards() {
        return amountShards;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public String getDefaultBotName() {
        return defaultBotName;
    }

    public String getBotAdministrator() {
        return botAdministrator;
    }

    public boolean isMysql() {
        return isMysql;
    }

    public boolean areCommandsToggleable() {
        return commandsToggleable;
    }
}
