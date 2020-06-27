/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.main;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.handling.command.CommandHandler;
import com.zyonicsoftware.maddox.listener.MessageReceivedListener;
import com.zyonicsoftware.maddox.listener.MessageUpdateListener;
import com.zyonicsoftware.maddox.startup.PreLoader;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;

public class Maddox {

    private CommandHandler commandHandler;
    private ShardManager shardManager;
    private String defaultPrefix;
    private Color defaultColor;


    public void startup(int amountShards, BaseValueConfig config, PreLoader preLoader) {

        shardManager = this.initShards(amountShards, config, preLoader);
        this.loadConfigValues(config);

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
    }


    public CommandHandler getCommandHandler() {
        return commandHandler;
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
