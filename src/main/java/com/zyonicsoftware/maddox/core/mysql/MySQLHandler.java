/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.mysql;

import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.core.MySQL;

public class MySQLHandler {

    private final Maddox maddox;

    public MySQLHandler(Maddox maddox) {
        this.maddox = maddox;
    }


    public MySQL connectToMysql(String hostname, int port, String database, String user, String password) {
        MySQL mySQL = new MySQL(hostname, port, user, password, database);
        MySQL.using(mySQL);

        return mySQL;
    }

}
