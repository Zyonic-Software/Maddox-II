/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
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

    public void setHostname(Object hostname) {
        this.hostname = (String) hostname;
    }

    public void setPort(Object port) {
        this.port = (int) port;
    }

    public void setDatabase(Object database) {
        this.database = (String) database;
    }

    public void setUser(Object user) {
        this.user = (String) user;
    }

    public void setPassword(Object password) {
        this.password = (String) password;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
