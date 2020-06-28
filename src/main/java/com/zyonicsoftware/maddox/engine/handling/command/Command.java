/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.engine.handling.command;

public abstract class Command {

    private String name;
    private String description;
    private String syntax;
    private String specificPrefix;
    private boolean allowExecutionOnMessageEdit;

    protected void setName(String name) {
        this.name = name;
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

    protected abstract void execute(CommandEvent event);
}
