/*
 * 2020. Zyonic Software - 2020. - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.startup;

import com.zyonicsoftware.maddox.config.BaseValueConfig;
import com.zyonicsoftware.maddox.main.Maddox;
import com.zyonicsoftware.maddox.yaml.YMLReader;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.Map;

public class PreLoader {

    private final Maddox maddox;

    public PreLoader(Maddox maddox) {
        this.maddox = maddox;
    }

    public void loadConfigFile(BaseValueConfig config) {
        YMLReader ymlReader = new YMLReader();

        Map baseValueConfigMap = ymlReader.readYML("config.yml");

        config.setToken(baseValueConfigMap.get("token"));
        config.setAmountShards(baseValueConfigMap.get("amountShards"));
        config.setDefaultPrefix(baseValueConfigMap.get("defaultPrefix"));
        config.setDefaultColor(baseValueConfigMap.get("defaultColor"));
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
