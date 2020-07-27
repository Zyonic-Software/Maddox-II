/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.savestructure;

import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.core.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHandler {

    private final Maddox maddox;
    private MySQL mySQL;

    public MySQLHandler(final Maddox maddox) {
        this.maddox = maddox;
    }


    public MySQL connectToMysql(final String hostname, final int port, final String database, final String user, final String password) {
        final MySQL mySQL = new MySQL(hostname, port, user, password, database);
        MySQL.using(mySQL);
        this.mySQL = mySQL;

        return mySQL;
    }

    public void addServerToDatabase(final String serverID, final String prefix, final String language) {
        try {
            this.mySQL.executeUpdate("INSERT INTO Server_Settings(id, prefix, language) VALUES ('" + serverID + "','" + prefix + "','" + language + "');");
            this.mySQL.executeUpdate("INSERT INTO Server_Automatic_Roles(id) VALUES ('" + serverID + "');");
            this.mySQL.executeUpdate("INSERT INTO Server_Join_Messages(id) VALUES ('" + serverID + "');");
            this.mySQL.executeUpdate("INSERT INTO Server_Leave_Messages(id) VALUES ('" + serverID + "');");
            this.mySQL.executeUpdate("INSERT INTO Private_Join_Message(id) VALUES ('" + serverID + "');");
            this.mySQL.executeUpdate("INSERT INTO Server_Command_Toggle(id) VALUES ('" + serverID + "');");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeServerFromDatabase(final String serverID) {
        try {
            this.mySQL.executeUpdate("DELETE FROM Server_Settings WHERE serverid = " + serverID);
            this.mySQL.executeUpdate("DELETE FROM Server_Automatic_Roles WHERE serverid = " + serverID);
            this.mySQL.executeUpdate("DELETE FROM Server_Join_Messages WHERE serverid = " + serverID);
            this.mySQL.executeUpdate("DELETE FROM Server_Join_Messages WHERE serverid = " + serverID);
            this.mySQL.executeUpdate("DELETE FROM Private_Join_Messages WHERE serverid = " + serverID);
            this.mySQL.executeUpdate("DELETE FROM Server_Command_Toggle WHERE serverid = " + serverID);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(String language, final String guildID) {
        language = this.mySQL.removeSQLInjectionPossibility(language);
        try {
            this.mySQL.executeUpdate("UPDATE Server_Settings SET language = '" + language + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getServerLanguage(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT language FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("language");
            }
        } catch (final Exception e) {
        }
        return null;
    }

    public String getPrefix(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT prefix FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("prefix");
            }
        } catch (final Exception e) {
        }
        return null;
    }

    public void setPrefix(String prefix, final String guildID) {
        prefix = this.mySQL.removeSQLInjectionPossibility(prefix);
        try {
            this.mySQL.executeUpdate("UPDATE Server_Settings SET prefix = '" + prefix + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRolesForAutomaticAssigning(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT roles FROM Server_Automatic_Roles WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("roles");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setRolesForAutomaticAssigning(final String rolesInString, final String id) {
        if (this.containsLetters(rolesInString)) {
            return;
        }
        try {
            this.mySQL.executeUpdate("UPDATE Server_Automatic_Roles SET roles = '" + rolesInString + "' WHERE id = '" + id + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    //JoinMessages

    public void setJoinMessage(String message, final String guildID) {
        message = this.mySQL.removeSQLInjectionPossibility(message);
        try {
            this.mySQL.executeUpdate("UPDATE Server_Join_Messages SET message = '" + message + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getJoinMessage(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT message FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("message");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setJoinMessageChannel(final String channelID, final String guildID) {
        try {
            this.mySQL.executeUpdate("UPDATE Server_Join_Messages SET channel = '" + channelID + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getJoinMessageChannel(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT channel FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("channel");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setJoinMessageEnabled(final boolean isEnabled, final String guildID) {
        try {
            if (isEnabled) {
                this.mySQL.executeUpdate("UPDATE Server_Join_Messages SET enabled = " + 1 + " WHERE id = '" + guildID + "';");
            } else {
                this.mySQL.executeUpdate("UPDATE Server_Join_Messages SET enabled = " + 0 + " WHERE id = '" + guildID + "';");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isJoinMessageEnabled(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT enabled FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getBoolean("enabled");
            }
        } catch (final Exception e) {
        }
        return false;
    }

    //LeaveMessages

    public void setLeaveMessage(String message, final String guildID) {
        message = this.mySQL.removeSQLInjectionPossibility(message);
        try {
            this.mySQL.executeUpdate("UPDATE Server_Leave_Messages SET message = '" + message + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLeaveMessage(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT message FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("message");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setLeaveMessageChannel(final String channelID, final String guildID) {
        try {
            this.mySQL.executeUpdate("UPDATE Server_Leave_Messages SET channel = '" + channelID + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLeaveMessageChannel(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT channel FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("channel");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setLeaveMessageEnabled(final boolean isEnabled, final String guildID) {
        try {
            if (isEnabled) {
                this.mySQL.executeUpdate("UPDATE Server_Leave_Messages SET enabled = " + 1 + " WHERE id = '" + guildID + "';");
            } else {
                this.mySQL.executeUpdate("UPDATE Server_Leave_Messages SET enabled = " + 0 + " WHERE id = '" + guildID + "';");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLeaveMessageEnabled(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT enabled FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getBoolean("enabled");
            }
        } catch (final Exception e) {
        }
        return false;
    }

    public String getPrivateJoinMessage(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT message FROM Private_Join_Message WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("message");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setPrivateJoinMessage(String message, final String guildID) {
        message = this.mySQL.removeSQLInjectionPossibility(message);
        try {
            this.mySQL.executeUpdate("UPDATE Private_Join_Message SET message = '" + message + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPrivateJoinMessageEnabled(final boolean isEnabled, final String guildID) {
        try {
            if (isEnabled) {
                this.mySQL.executeUpdate("UPDATE Private_Join_Message SET enabled = " + 1 + " WHERE id = '" + guildID + "';");
            } else {
                this.mySQL.executeUpdate("UPDATE Private_Join_Message SET enabled = " + 0 + " WHERE id = '" + guildID + "';");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPrivateJoinMessageEnabled(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT enabled FROM Private_Join_Message WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getBoolean("enabled");
            }
        } catch (final Exception e) {
        }
        return false;
    }

    public String getEnabledCommands(final String guildID) {
        try {
            final ResultSet resultSet = this.mySQL.executeQuery("SELECT enabled_commands FROM Server_Command_Toggle WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("enabled_commands");
            }
        } catch (final Exception e) {
        }
        return "";
    }

    public void setEnabledCommands(final String enabledCommands, final String guildID) {
        try {
            this.mySQL.executeUpdate("UPDATE Server_Command_Toggle SET enabled_commands = '" + enabledCommands + "' WHERE id = '" + guildID + "';");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }


    //Seperate Stuff
    private boolean containsLetters(final String value) {
        return value.matches(".*[AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz].*");
    }

}
