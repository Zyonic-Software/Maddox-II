/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.config;

public class MySQLConfig {

  private String hostname;
  private int port;
  private String database;
  private String user;
  private String password;
  private boolean isEnabled;

  public void setHostname(final Object hostname) {
    this.hostname = (String)hostname;
  }

  public void setPort(final Object port) { this.port = (int)port; }

  public void setDatabase(final Object database) {
    this.database = (String)database;
  }

  public void setUser(final Object user) { this.user = (String)user; }

  public void setPassword(final Object password) {
    this.password = (String)password;
  }

  public void setEnabled(final boolean enabled) { this.isEnabled = enabled; }

  public String getHostname() { return this.hostname; }

  public int getPort() { return this.port; }

  public String getDatabase() { return this.database; }

  public String getUser() { return this.user; }

  public String getPassword() { return this.password; }

  public boolean isEnabled() { return this.isEnabled; }
}
