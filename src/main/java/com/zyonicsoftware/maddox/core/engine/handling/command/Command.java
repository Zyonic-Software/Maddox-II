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
    private boolean showExtendedHelp = true;
    private boolean toggleable;
    private boolean getValuesFromLanguage;
    private int commandHelpViewPermission = 0;

    protected void setName(final String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return this.name;
    }

    protected void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    protected void setSyntax(final String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() {
        return this.syntax;
    }

    protected void setSpecificPrefix(final String specificPrefix) {
        this.specificPrefix = specificPrefix;
    }

    public String getSpecificPrefix() {
        return this.specificPrefix;
    }

    protected void setAllowExecutionOnMessageEdit(final boolean allowExecutionOnMessageEdit) {
        this.allowExecutionOnMessageEdit = allowExecutionOnMessageEdit;
    }

    public boolean isExecutionOnMessageEdit() {
        return this.allowExecutionOnMessageEdit;
    }

    protected void setShowInHelp(final boolean showInHelp) {
        this.showInHelp = showInHelp;
    }

    public boolean ShowInHelp() {
        return this.showInHelp;
    }

    protected void setShowExtendedHelp(final boolean showExtendedHelp) {
        this.showExtendedHelp = showExtendedHelp;
    }

    public boolean isShowExtendedHelp() {
        return this.showExtendedHelp;
    }

    protected void setToggleable(final boolean isToggleable) {
        this.toggleable = isToggleable;
    }

    public boolean isToggleable() {
        return this.toggleable;
    }

    protected void setCommandHelpViewPermission(final CommandHelpViewPermission commandHelpViewPermission) {
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
        return this.commandHelpViewPermission;
    }

    protected void setCategory(final String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    protected void setGetValuesFromLanguageYAML(final boolean getValuesFromLanguage) {
        this.getValuesFromLanguage = getValuesFromLanguage;
    }

    public boolean isGetValuesFromLanguageYaml() {
        return this.getValuesFromLanguage;
    }

    protected abstract void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server);
}
