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
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.simpleyaml.configuration.file.YamlConfiguration;

import javax.security.auth.login.LoginException;
import java.io.File;

public class PreLoader {

    private final Maddox maddox;

    public PreLoader(Maddox maddox) {
        this.maddox = maddox;
    }

    public void loadConfigFile(BaseValueConfig config, MySQLConfig mySQLConfig) {

        try {

            //Config
            File configFile = new File("config.yml");

            if (!configFile.createNewFile()) {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

                config.setToken(yamlConfiguration.get("token"));
                config.setAmountShards(yamlConfiguration.get("amountShards"));
                config.setDefaultPrefix(yamlConfiguration.get("defaultPrefix"));
                config.setDefaultColor(yamlConfiguration.get("defaultColor"));
                config.setDefaultBotName(yamlConfiguration.get("defaultBotName"));
                config.setMysql(yamlConfiguration.get("mysql"));

            } else {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

                yamlConfiguration.createSection("token");
                yamlConfiguration.createSection("amountShards");
                yamlConfiguration.createSection("defaultPrefix");
                yamlConfiguration.createSection("defaultColor");
                yamlConfiguration.createSection("defaultBotName");
                yamlConfiguration.createSection("mysql");

                yamlConfiguration.save(configFile);

                yamlConfiguration.set("token", "token");
                yamlConfiguration.set("amountShards", "1");
                yamlConfiguration.set("defaultPrefix", "!");
                yamlConfiguration.set("defaultColor", "#0231a8");
                yamlConfiguration.set("defaultBotName", "Maddox");
                yamlConfiguration.set("mysql", "false '[Remove this before use]#config will be generated on activation[Remove this before use]'");

                yamlConfiguration.save(configFile);
            }

            if (config.isMysql()) {

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
                    yamlConfiguration.set("port", "3306");
                    yamlConfiguration.set("database", "Maddox");
                    yamlConfiguration.set("user", "maddox");
                    yamlConfiguration.set("password", "maddox_is_cool_please_use_a_safe_password_i_beg_you");

                    yamlConfiguration.save(mysqlFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ShardManager loadShards(int amountOfShards, String token, ListenerAdapter... listenerAdapters) {
        try {
            return DefaultShardManagerBuilder
                    .createLight(
                            token,
                            GatewayIntent.GUILD_BANS,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_INVITES,
                            GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_PRESENCES,
                            GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_MESSAGES
                    )
                    .addEventListeners(listenerAdapters)
                    .setShardsTotal(amountOfShards)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
            return null;
        }
    }

}
