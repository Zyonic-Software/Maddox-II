/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.startup;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.config.MySQLConfig;
import com.zyonicsoftware.maddox.core.main.Maddox;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;

public class PreStartupLoader {

    private final Maddox maddox;

    public PreStartupLoader(Maddox maddox) {
        this.maddox = maddox;
    }

    public void loadConfigFile(BaseValueConfig config, MySQLConfig mySQLConfig) {

        this.initMainConfig(config);

        if (config.isMysql()) {
            this.initMysqlConfig(mySQLConfig);
        }
    }

    private void initMainConfig(BaseValueConfig config) {
        try {
            File configFile = new File("config.yml");

            if (!configFile.createNewFile()) {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

                config.setToken(yamlConfiguration.get("token"));
                config.setAmountShards(yamlConfiguration.get("amountShards"));
                config.setDefaultPrefix(yamlConfiguration.get("defaultPrefix"));
                config.setDefaultColor(yamlConfiguration.get("defaultColor"));
                config.setDefaultBotName(yamlConfiguration.get("defaultBotName"));
                config.setBotAdministrator(yamlConfiguration.get("botAdministrator"));
                config.setMysql(yamlConfiguration.get("mysql"));
                config.setCommandsToggleable(yamlConfiguration.get("commandsToggleable"));

            } else {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

                yamlConfiguration.createSection("token");
                yamlConfiguration.createSection("amountShards");
                yamlConfiguration.createSection("defaultPrefix");
                yamlConfiguration.createSection("defaultColor");
                yamlConfiguration.createSection("defaultBotName");
                yamlConfiguration.createSection("botAdministrator");
                yamlConfiguration.createSection("mysql");
                yamlConfiguration.createSection("commandsToggleable");

                yamlConfiguration.save(configFile);

                yamlConfiguration.set("token", "your_bots_token_goes_here");
                yamlConfiguration.set("amountShards", 1);
                yamlConfiguration.set("defaultPrefix", "!");
                yamlConfiguration.set("defaultColor", "#0231a8");
                yamlConfiguration.set("defaultBotName", "Maddox");
                yamlConfiguration.set("botAdministrator", "309007975060602882,498077062775570432");
                yamlConfiguration.set("mysql", false);
                yamlConfiguration.set("commandsToggleable", false);

                yamlConfiguration.save(configFile);
            }
        } catch (Exception e) {
            System.out.println("Config generated, please Restart");
            e.printStackTrace();
        }
    }

    private void initMysqlConfig(MySQLConfig mySQLConfig) {
        try {
            File mysqlFile = new File("mysqlconfig.yml");

            if (!mysqlFile.createNewFile()) {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(mysqlFile);

                mySQLConfig.setHostname(yamlConfiguration.get("hostname"));
                mySQLConfig.setPort(yamlConfiguration.get("port"));
                mySQLConfig.setDatabase(yamlConfiguration.get("database"));
                mySQLConfig.setUser(yamlConfiguration.get("user"));
                mySQLConfig.setPassword(yamlConfiguration.get("password"));

            } else {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(mysqlFile);

                yamlConfiguration.createSection("hostname");
                yamlConfiguration.createSection("port");
                yamlConfiguration.createSection("database");
                yamlConfiguration.createSection("user");
                yamlConfiguration.createSection("password");

                yamlConfiguration.save(mysqlFile);

                yamlConfiguration.set("hostname", "localhost");
                yamlConfiguration.set("port", 3306);
                yamlConfiguration.set("database", "Maddox");
                yamlConfiguration.set("user", "maddox");
                yamlConfiguration.set("password", "maddox_is_cool_please_use_a_safe_password_i_beg_you");

                yamlConfiguration.save(mysqlFile);

                System.out.println("Generated mySQL config, please enter your Information and restart");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
