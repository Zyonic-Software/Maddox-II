/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.main;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.config.MySQLConfig;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandHandler;
import com.zyonicsoftware.maddox.core.engine.handling.privatemessage.PrivateMessageCommandHandler;
import com.zyonicsoftware.maddox.core.engine.helpbuilder.HelpBuilder;
import com.zyonicsoftware.maddox.core.language.LanguageLoader;
import com.zyonicsoftware.maddox.core.management.CommandManager;
import com.zyonicsoftware.maddox.core.mysql.MySQLHandler;
import com.zyonicsoftware.maddox.core.startup.StartupLoader;
import com.zyonicsoftware.maddox.modules.listener.MessageReceivedListener;
import com.zyonicsoftware.maddox.modules.listener.MessageUpdateListener;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;

public class Maddox {

    private PrivateMessageCommandHandler privateMessageCommandHandler;
    private CommandHandler commandHandler;
    private LanguageLoader languageLoader;
    private MySQLHandler mySQLHandler;
    private ShardManager shardManager;
    private String supportedLanguages;
    private HelpBuilder helpBuilder;
    private String botAdministrator;
    private String defaultLanguage;
    private String defaultPrefix;
    private Color defaultColor;
    private boolean isMySQL;
    private String name;


    public void startup(int amountShards, BaseValueConfig config, MySQLConfig mySQLConfig) {

        //Get Values from Primary Config (config.yml)
        this.loadConfigValues(config);

        StartupLoader startupLoader = new StartupLoader();//Used to Init Shards

        if (config.getToken() == null || config.getToken().equals("your_bots_token_goes_here")) {//Checks if token has been insertet
            System.out.println("Please enter your Bot's Token into the generated 'config.yml'");
            return;
        }

        System.out.println("Startup " + this.name);


        //If MySQL-Module is Enabled, this will automatically connect to the specified Database Server
        if (config.isMysql()) {
            if (mySQLConfig.getPassword() != null && !mySQLConfig.getPassword().equals("maddox_is_cool_please_use_a_safe_password_i_beg_you")) {
                mySQLHandler = new MySQLHandler(this);
                try {
                    mySQLHandler.connectToMysql(mySQLConfig.getHostname(), mySQLConfig.getPort(), mySQLConfig.getDatabase(), mySQLConfig.getUser(), mySQLConfig.getPassword());
                    System.out.println("MySQL enabled");
                    isMySQL = true;
                } catch (Exception e) {
                    System.out.println("MySQL connection failed, aborting");
                    return;
                }
            } else {
                System.out.println("Please enter your info in 'mysqlconfig.yml' for the MySQL module to work, aborting.");
                return;
            }
        }

        this.languageLoader = new LanguageLoader(this);

        this.languageLoader.initLanguages();//Loads languages from config files (and generates them)


        System.out.println("Bot-Administrator ID(s): " + config.getBotAdministrator());

        shardManager = this.initShards(amountShards, config, startupLoader);

        commandHandler = new CommandHandler(this);

        helpBuilder = new HelpBuilder(this);

        CommandManager commandManager = new CommandManager(this);

        commandManager.registerCommands(commandHandler);
    }

    //Creates ShardManager, specefies primary Listeners
    private ShardManager initShards(int amountShards, BaseValueConfig config, StartupLoader startupLoader) {
        return startupLoader.loadShards(
                amountShards,
                config.getToken(),
                new MessageReceivedListener(this),
                new MessageUpdateListener(this)
        );
    }

    private void loadConfigValues(BaseValueConfig config) {
        this.defaultPrefix = config.getDefaultPrefix();
        this.defaultColor = config.getDefaultColor();
        this.name = config.getDefaultBotName();
        this.botAdministrator = config.getBotAdministrator();
        this.defaultLanguage = config.getDefaultLanguage();
        this.supportedLanguages = config.getSupportedLanguages();
    }


    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public PrivateMessageCommandHandler getPrivateMessageCommandHandler() {
        return privateMessageCommandHandler;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public String getBotAdministrator() {
        return botAdministrator;
    }

    public HelpBuilder getHelpBuilder() {
        return helpBuilder;
    }

    public String getName() {
        return name;
    }

    public boolean isMySQLConnected() {
        return isMySQL;
    }

    public MySQLHandler getMySQLHandler() {
        return mySQLHandler;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }
}
