/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be
 * used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.startup;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.config.MySQLConfig;
import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.core.MySQL;

public class Initializer {

  public static void main(final String[] args) {
    final Maddox maddox = new Maddox();
    final PreStartupLoader preStartupLoader = new PreStartupLoader(maddox);
    final BaseValueConfig config = new BaseValueConfig();
    final MySQLConfig mySQLConfig = new MySQLConfig();

    preStartupLoader.loadConfigFile(config, mySQLConfig);

    maddox.startup(config.getAmountShards(), config, mySQLConfig);

    // ShutdownHook
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        MySQL.disconnect();
        System.out.println("Shutdown Starting");
        System.out.println("Application Terminating ...");
      }
    });
  }
}
