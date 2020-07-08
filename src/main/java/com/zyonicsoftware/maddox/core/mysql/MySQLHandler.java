/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.mysql;

import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.core.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHandler {

    private final Maddox maddox;
    private MySQL mySQL;

    public MySQLHandler(Maddox maddox) {
        this.maddox = maddox;
    }


    public MySQL connectToMysql(String hostname, int port, String database, String user, String password) {
        MySQL mySQL = new MySQL(hostname, port, user, password, database);
        MySQL.using(mySQL);
        this.mySQL = mySQL;

        return mySQL;
    }

    public void addServerToDatabase(String serverID, String prefix, String language) {
        try {
            mySQL.executeUpdate("INSERT INTO Server_Settings(id, prefix, language) VALUES ('" + serverID + "','" + prefix + "','" + language + "');");
            mySQL.executeUpdate("INSERT INTO Server_Automatic_Roles(id) VALUES ('" + serverID + "');");
            mySQL.executeUpdate("INSERT INTO Server_Join_Messages(id) VALUES ('" + serverID + "');");
            mySQL.executeUpdate("INSERT INTO Server_Leave_Messages(id) VALUES ('" + serverID + "');");
            mySQL.executeUpdate("INSERT INTO Server_Command_Toggle(id) VALUES ('" + serverID + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeServerFromDatabase(String serverID) {
        try {
            mySQL.executeUpdate("DELETE FROM Server_Settings WHERE serverid = " + serverID);
            mySQL.executeUpdate("DELETE FROM Server_Automatic_Roles WHERE serverid = " + serverID);
            mySQL.executeUpdate("DELETE FROM Server_Join_Messages WHERE serverid = " + serverID);
            mySQL.executeUpdate("DELETE FROM Server_Join_Messages WHERE serverid = " + serverID);
            mySQL.executeUpdate("DELETE FROM Server_Command_Toggle WHERE serverid = " + serverID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(String language, String guildID) {
        language = mySQL.removeSQLInjectionPossibility(language);
        try {
            mySQL.executeUpdate("UPDATE Server_Settings SET language = '" + language + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getServerLanguage(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT language FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("language");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getPrefix(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT prefix FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("prefix");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void setPrefix(String prefix, String guildID) {
        prefix = mySQL.removeSQLInjectionPossibility(prefix);
        try {
            mySQL.executeUpdate("UPDATE Server_Settings SET prefix = '" + prefix + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRolesForAutomaticAssigning(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT roles FROM Server_Automatic_Roles WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("roles");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setRolesForAutomaticAssigning(String rolesInString, String id) {
        if (this.containsLetters(rolesInString)) {
            return;
        }
        try {
            mySQL.executeUpdate("UPDATE Server_Automatic_Roles SET roles = '" + rolesInString + "' WHERE id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //JoinMessages

    public void setJoinMessage(String message, String guildID) {
        message = mySQL.removeSQLInjectionPossibility(message);
        try {
            mySQL.executeUpdate("UPDATE Server_Join_Messages SET message = '" + message + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getJoinMessage(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT message FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("message");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setJoinMessageChannel(String channelID, String guildID) {
        try {
            mySQL.executeUpdate("UPDATE Server_Join_Messages SET channel = '" + channelID + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getJoinMessageChannel(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT channel FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("channel");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setJoinMessageEnabled(boolean isEnabled, String guildID) {
        try {
            if (isEnabled) {
                mySQL.executeUpdate("UPDATE Server_Join_Messages SET enabled = " + 1 + " WHERE id = '" + guildID + "';");
            } else {
                mySQL.executeUpdate("UPDATE Server_Join_Messages SET enabled = " + 0 + " WHERE id = '" + guildID + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isJoinMessageEnabled(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT enabled FROM Server_Join_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getBoolean("enabled");
            }
        } catch (Exception e) {
        }
        return false;
    }

    //LeaveMessages

    public void setLeaveMessage(String message, String guildID) {
        message = mySQL.removeSQLInjectionPossibility(message);
        try {
            mySQL.executeUpdate("UPDATE Server_Leave_Messages SET message = '" + message + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLeaveMessage(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT message FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("message");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setLeaveMessageChannel(String channelID, String guildID) {
        try {
            mySQL.executeUpdate("UPDATE Server_Leave_Messages SET channel = '" + channelID + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLeaveMessageChannel(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT channel FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("channel");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setLeaveMessageEnabled(boolean isEnabled, String guildID) {
        try {
            if (isEnabled) {
                mySQL.executeUpdate("UPDATE Server_Leave_Messages SET enabled = " + 1 + " WHERE id = '" + guildID + "';");
            } else {
                mySQL.executeUpdate("UPDATE Server_Leave_Messages SET enabled = " + 0 + " WHERE id = '" + guildID + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLeaveMessageEnabled(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT enabled FROM Server_Leave_Messages WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getBoolean("enabled");
            }
        } catch (Exception e) {
        }
        return false;
    }

    public String getEnabledCommands(String guildID){
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT enabled_commands FROM Server_Command_Toggle WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("enabled_commands");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setEnabledCommands(String enabledCommands, String guildID){
        try {
            mySQL.executeUpdate("UPDATE Server_Command_Toggle SET enabled_commands = '" + enabledCommands + "' WHERE id = '" + guildID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Seperate Stuff
    private boolean containsLetters(String value) {
        return value.matches(".*[AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz].*");
    }

}
