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
import com.zyonicsoftware.maddox.core.management.AutomaticRoleManager;
import com.zyonicsoftware.maddox.core.management.CommandToggleManager;
import com.zyonicsoftware.maddox.core.savestructure.CacheManager;
import com.zyonicsoftware.maddox.core.savestructure.MySQLHandler;
import com.zyonicsoftware.maddox.core.startup.StartupLoader;
import com.zyonicsoftware.maddox.modules.listener.*;
import com.zyonicsoftware.maddox.modules.registration.CommandManager;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;

public class Maddox {

    private PrivateMessageCommandHandler privateMessageCommandHandler;
    private CommandToggleManager commandToggleManager;
    private AutomaticRoleManager automaticRoleManager;
    private boolean areCommandsToggleable;
    private CommandHandler commandHandler;
    private LanguageLoader languageLoader;
    private MySQLHandler mySQLHandler;
    private ShardManager shardManager;
    private CacheManager cacheManager;
    private String supportedLanguages;
    private HelpBuilder helpBuilder;
    private String botAdministrator;
    private String defaultLanguage;
    private String defaultPrefix;
    private Color defaultColor;
    private boolean isMySQL;
    private String name;


    public void startup(final int amountShards, final BaseValueConfig config, final MySQLConfig mySQLConfig) {

        //Get Values from Primary Config (config.yml)
        this.loadConfigValues(config);

        final StartupLoader startupLoader = new StartupLoader();//Used to Init Shards

        if (config.getToken() == null || config.getToken().equals("your_bots_token_goes_here")) {//Checks if token has been insertet
            System.out.println("Please enter your Bot's Token into the generated 'config.yml'");
            return;
        }

        System.out.println("Startup " + this.name);


        //If MySQL-Module is Enabled, this will automatically connect to the specified Database Server
        if (config.isMysql()) {
            if (mySQLConfig.getPassword() != null && !mySQLConfig.getPassword().equals("maddox_is_cool_please_use_a_safe_password_i_beg_you")) {
                this.mySQLHandler = new MySQLHandler(this);
                try {
                    this.mySQLHandler.connectToMysql(mySQLConfig.getHostname(), mySQLConfig.getPort(), mySQLConfig.getDatabase(), mySQLConfig.getUser(), mySQLConfig.getPassword());
                    System.out.println("MySQL enabled");
                    this.isMySQL = true;
                } catch (final Exception e) {
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

        this.shardManager = this.initShards(amountShards, config, startupLoader);

        this.commandHandler = new CommandHandler(this);
        this.privateMessageCommandHandler = new PrivateMessageCommandHandler(this);
        this.helpBuilder = new HelpBuilder(this);

        final CommandManager commandManager = new CommandManager(this);

        commandManager.registerCommands(this.commandHandler);

        this.automaticRoleManager = new AutomaticRoleManager(this);
        this.commandToggleManager = new CommandToggleManager(this);
        this.cacheManager = new CacheManager(this);
    }

    //Creates ShardManager, specefies primary Listeners
    private ShardManager initShards(final int amountShards, final BaseValueConfig config, final StartupLoader startupLoader) {
        return startupLoader.loadShards(
                amountShards,
                config.getToken(),
                new MessageReceivedListener(this),
                new MessageUpdateListener(this),
                new GuildMemberJoinListener(this),
                new GuildMemberLeaveListener(this),
                new PrivateMessageReceivedListener(this),
                new GuildJoinListener(this),
                new GuildLeaveListener(this)
        );
    }

    private void loadConfigValues(final BaseValueConfig config) {
        this.defaultPrefix = config.getDefaultPrefix();
        this.defaultColor = config.getDefaultColor();
        this.name = config.getDefaultBotName();
        this.botAdministrator = config.getBotAdministrator();
        this.defaultLanguage = config.getDefaultLanguage();
        this.supportedLanguages = config.getSupportedLanguages();
        this.areCommandsToggleable = config.areCommandsToggleable();
    }


    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public PrivateMessageCommandHandler getPrivateMessageCommandHandler() {
        return this.privateMessageCommandHandler;
    }

    public ShardManager getShardManager() {
        return this.shardManager;
    }

    public String getDefaultPrefix() {
        return this.defaultPrefix;
    }

    public Color getDefaultColor() {
        return this.defaultColor;
    }

    public String getBotAdministrator() {
        return this.botAdministrator;
    }

    public HelpBuilder getHelpBuilder() {
        return this.helpBuilder;
    }

    public String getName() {
        return this.name;
    }

    public boolean isMySQLConnected() {
        return this.isMySQL;
    }

    public MySQLHandler getMySQLHandler() {
        return this.mySQLHandler;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public String getSupportedLanguages() {
        return this.supportedLanguages;
    }

    public AutomaticRoleManager getAutomaticRoleManager() {
        return this.automaticRoleManager;
    }

    public CommandToggleManager getCommandToggleManager() {
        return this.commandToggleManager;
    }

    public boolean areCommandsToggleable() {
        return this.areCommandsToggleable;
    }

    public CacheManager getCacheManager() {
        return this.cacheManager;
    }
}
