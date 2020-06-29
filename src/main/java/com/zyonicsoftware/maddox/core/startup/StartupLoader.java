/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.startup;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class StartupLoader {

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
        } catch (Exception e) {
            System.out.println("The provided token is invalid, please enter a valid one");
            return null;
        }
    }

}
