/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.main;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandHandler;
import com.zyonicsoftware.maddox.core.engine.handling.privatemessage.PrivateMessageCommandHandler;
import com.zyonicsoftware.maddox.modules.listener.MessageReceivedListener;
import com.zyonicsoftware.maddox.modules.listener.MessageUpdateListener;
import com.zyonicsoftware.maddox.core.startup.PreLoader;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;

public class Maddox {

    private CommandHandler commandHandler;
    private PrivateMessageCommandHandler privateMessageCommandHandler;
    private ShardManager shardManager;
    private String defaultPrefix;
    private Color defaultColor;
    private String name;


    public void startup(int amountShards, BaseValueConfig config, PreLoader preLoader) {

        this.loadConfigValues(config);

        System.out.println("Startup " + this.name);

        shardManager = this.initShards(amountShards, config, preLoader);

        commandHandler = new CommandHandler(this);

    }

    private ShardManager initShards(int amountShards, BaseValueConfig config, PreLoader preLoader) {
        return preLoader.loadShards(
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
}
