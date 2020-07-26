/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
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
  private String defaultLanguage;
  private String supportedLanguages;
  private String botAdministrator;
  private boolean isMysql;
  private boolean commandsToggleable;

  public void setToken(final Object token) { this.token = (String)token; }

  public void setAmountShards(final Object amountShards) {
    this.amountShards = (int)amountShards;
  }

  public void setDefaultColor(final Object defaultColor) {
    System.out.println(defaultColor);
    this.defaultColor = Color.decode((String)defaultColor);
  }

  public void setDefaultPrefix(final Object defaultPrefix) {
    this.defaultPrefix = (String)defaultPrefix;
  }

  public void setDefaultBotName(final Object defaultBotName) {
    this.defaultBotName = (String)defaultBotName;
  }

  public void setDefaultLanguage(final Object defaultLanguage) {
    this.defaultLanguage = (String)defaultLanguage;
  }

  public void setSupportedLanguages(final Object supportedLanguages) {
    this.supportedLanguages = (String)supportedLanguages;
  }

  public void setMysql(final Object mysql) { this.isMysql = (boolean)mysql; }

  public void setBotAdministrator(final Object botAdministrator) {
    this.botAdministrator = (String)botAdministrator;
  }

  public void setCommandsToggleable(final Object commandsToggleable) {
    this.commandsToggleable = (boolean)commandsToggleable;
  }

  public String getToken() { return this.token; }

  public int getAmountShards() { return this.amountShards; }

  public String getDefaultPrefix() { return this.defaultPrefix; }

  public Color getDefaultColor() { return this.defaultColor; }

  public String getDefaultBotName() { return this.defaultBotName; }

  public String getDefaultLanguage() { return this.defaultLanguage; }

  public String getSupportedLanguages() { return this.supportedLanguages; }

  public String getBotAdministrator() { return this.botAdministrator; }

  public boolean isMysql() { return this.isMysql; }

  public boolean areCommandsToggleable() { return this.commandsToggleable; }
}
