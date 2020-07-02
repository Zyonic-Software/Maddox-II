/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.command;

import com.zyonicsoftware.maddox.core.engine.helpbuilder.CommandHelpViewPermission;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;

public abstract class Command {

    private String name;
    private String description;
    private String syntax;
    private String specificPrefix;
    private String category = "none";
    private boolean allowExecutionOnMessageEdit;
    private boolean showInHelp = true;
    private boolean toggleable;
    private boolean getValuesFromLanguage;
    private int commandHelpViewPermission = 0;

    protected void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    protected void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() {
        return syntax;
    }

    protected void setSpecificPrefix(String specificPrefix) {
        this.specificPrefix = specificPrefix;
    }

    public String getSpecificPrefix() {
        return specificPrefix;
    }

    protected void setAllowExecutionOnMessageEdit(boolean allowExecutionOnMessageEdit) {
        this.allowExecutionOnMessageEdit = allowExecutionOnMessageEdit;
    }

    public boolean isExecutionOnMessageEdit() {
        return allowExecutionOnMessageEdit;
    }

    protected void setShowInHelp(boolean showInHelp) {
        this.showInHelp = showInHelp;
    }

    public boolean ShowInHelp() {
        return showInHelp;
    }

    protected void setToggleable(boolean isToggleable) {
        this.toggleable = isToggleable;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    protected void setCommandHelpViewPermission(CommandHelpViewPermission commandHelpViewPermission) {
        switch (commandHelpViewPermission) {
            case EVERYONE:
                this.commandHelpViewPermission = 0;
                break;
            case MANAGE_MESSAGE:
                this.commandHelpViewPermission = 1;
                break;
            case MANAGE_CHANNEL:
                this.commandHelpViewPermission = 2;
                break;
            case MEMBER_KICK:
                this.commandHelpViewPermission = 3;
                break;
            case MEMBER_BAN:
                this.commandHelpViewPermission = 4;
                break;
            case MANAGE_ROLE:
                this.commandHelpViewPermission = 5;
                break;
            case MANAGE_SERVER:
                this.commandHelpViewPermission = 6;
                break;
            case ADMINISTRATOR:
                this.commandHelpViewPermission = 7;
                break;
        }
    }

    public int getCommandHelpViewPermission() {
        return commandHelpViewPermission;
    }

    protected void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    protected void setGetValuesFromLanguageYAML(boolean getValuesFromLanguage) {
        this.getValuesFromLanguage = getValuesFromLanguage;
    }

    public boolean isGetValuesFromLanguageYaml() {
        return getValuesFromLanguage;
    }

    protected abstract void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server);
}
