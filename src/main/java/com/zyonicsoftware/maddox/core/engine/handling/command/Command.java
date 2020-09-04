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
    private String[] alias;
    private String specificPrefix;
    private String category = "none";
    private boolean allowExecutionOnMessageEdit;
    private boolean showInHelp = true;
    private boolean showExtendedHelp = true;
    private boolean toggleable;
    private boolean getValuesFromLanguage;
    private int commandHelpViewPermission = 0;

    /**
     * Sets the Name of the Command. This is the keyword that triggers a run of the Command's code when paired with the Prefix
     *
     * @param name
     */
    protected void setName(final String name) {
        this.name = name.toLowerCase();
    }

    /**
     * Returns the Name of the command
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the description for Command which will be listed in the extended help menu when using the help-builder
     *
     * @param description
     */
    protected void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Returns the description used in the help-builder
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the Syntax-example in the extended Help Menu when using the help-builder
     *
     * @param syntax
     */
    protected void setSyntax(final String syntax) {
        this.syntax = syntax;
    }

    /**
     * Returns the Syntax-example from the extended Help Menu when using the help-builder
     *
     * @return
     */
    public String getSyntax() {
        return this.syntax;
    }

    /**
     * When this value is set it limits the command in such a way, that it can only be triggered with that command
     * This Mode does not use MySQL
     *
     * @param specificPrefix
     */
    protected void setSpecificPrefix(final String specificPrefix) {
        this.specificPrefix = specificPrefix;
    }

    /**
     * Returns the specific Prefix which is in prefix-per-command mode
     *
     * @return
     */
    public String getSpecificPrefix() {
        return this.specificPrefix;
    }

    /**
     * Governs if the command can be executed when the Message is edited
     *
     * @param allowExecutionOnMessageEdit
     */
    protected void setAllowExecutionOnMessageEdit(final boolean allowExecutionOnMessageEdit) {
        this.allowExecutionOnMessageEdit = allowExecutionOnMessageEdit;
    }

    /**
     * Checks if the command can be triggerd by editing a message
     *
     * @return
     */
    public boolean isExecutionOnMessageEdit() {
        return this.allowExecutionOnMessageEdit;
    }

    /**
     * Governs if the Command should show up in the Help-Menu built by the help-builder
     *
     * @param showInHelp
     */
    protected void setShowInHelp(final boolean showInHelp) {
        this.showInHelp = showInHelp;
    }

    /**
     * Checks if command is shown in help
     *
     * @return
     */
    public boolean ShowInHelp() {
        return this.showInHelp;
    }

    /**
     * Governs if command has extended help-syntax
     *
     * @param showExtendedHelp
     */
    protected void setShowExtendedHelp(final boolean showExtendedHelp) {
        this.showExtendedHelp = showExtendedHelp;
    }

    /**
     * Returns if the Command shows an extended help syntax
     *
     * @return
     */
    public boolean isShowExtendedHelp() {
        return this.showExtendedHelp;
    }

    /**
     * Governs if the Command can be switched on and off by the end user
     *
     * @param isToggleable
     */
    protected void setToggleable(final boolean isToggleable) {
        this.toggleable = isToggleable;
    }

    /**
     * Checks if the Command can be switched on and off by the end user
     *
     * @return
     */
    public boolean isToggleable() {
        return this.toggleable;
    }

    /**
     * Governs which Permission is required to see the command in the built help-menu
     *
     * @param commandHelpViewPermission
     */
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

    /**
     * Returns the numeric value for the designated CommandHelp view permission [INTERNAL]
     *
     * @return
     */
    public int getCommandHelpViewPermissionValue() {
        return this.commandHelpViewPermission;
    }

    /**
     * Governs the Category the Command is listed under in the help menu
     *
     * @param category
     */
    protected void setCategory(final String category) {
        this.category = category;
    }

    /**
     * Returns the Category the Command is listed under
     *
     * @return Category the Command is listed under in help menu
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Governs if the Command should get it's description and Syntax Value via the language system
     *
     * @param getValuesFromLanguage
     */
    protected void setGetValuesFromLanguageYAML(final boolean getValuesFromLanguage) {
        this.getValuesFromLanguage = getValuesFromLanguage;
    }

    /**
     * Returns if command gets values via the language system
     *
     * @return
     */
    public boolean isGetValuesFromLanguageYaml() {
        return this.getValuesFromLanguage;
    }

    /**
     * Returns arry of Aliases
     *
     * @return String array of Command Aliases
     */
    public String[] getAlias() {
        return this.alias;
    }

    /**
     * This Method is used to add Alternative "keywords" to the command, enabling it to be triggered by more than one "name"
     *
     * @param alias String(s), Aliases
     */
    protected void addAlias(final String... alias) {
        this.alias = alias;
    }

    /**
     * Abstract Method which is triggered on command Execution via Command Handler
     *
     * @param event  Command Event, built upon GuildMessageReceivedEvent or GuildMessageUpdateEvent with added methods
     * @param sender Member, built upon Member with added Features
     * @param server Guild, built upon Guild with added Maddox-Specific features
     */
    protected abstract void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server);
}
