/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.handling.privatemessage;

public abstract class PrivateMessageCommand {

  private String name;
  private String description;
  private String syntax;
  private String specificPrefix;
  private boolean allowExecutionOnMessageEdit;

  protected void setName(final String name) { this.name = name; }

  public String getName() { return this.name; }

  protected void setDescription(final String description) {
    this.description = description;
  }

  public String getDescription() { return this.description; }

  protected void setSyntax(final String syntax) { this.syntax = syntax; }

  public String getSyntax() { return this.syntax; }

  protected void setSpecificPrefix(final String specificPrefix) {
    this.specificPrefix = specificPrefix;
  }

  public String getSpecificPrefix() { return this.specificPrefix; }

  protected void
  setAllowExecutionOnMessageEdit(final boolean allowExecutionOnMessageEdit) {
    this.allowExecutionOnMessageEdit = allowExecutionOnMessageEdit;
  }

  public boolean isExecutionOnMessageEdit() {
    return this.allowExecutionOnMessageEdit;
  }

  protected abstract void execute(PrivateMessageCommandEvent event);
}
