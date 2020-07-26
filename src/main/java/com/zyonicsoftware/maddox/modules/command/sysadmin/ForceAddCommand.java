/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.modules.command.sysadmin;

import com.zyonicsoftware.maddox.core.engine.handling.command.Command;
import com.zyonicsoftware.maddox.core.engine.handling.command.CommandEvent;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.engine.objects.MaddoxMember;
import com.zyonicsoftware.maddox.core.main.Maddox;

public class ForceAddCommand extends Command {

    private final Maddox maddox;

    public ForceAddCommand(final Maddox maddox) {
        this.setName("forceadd");
        this.setSpecificPrefix("!");
        this.setShowInHelp(false);
        this.maddox = maddox;
    }

    @Override
    protected void execute(final CommandEvent event, final MaddoxMember sender, final MaddoxGuild server) {
        if (this.maddox.getBotAdministrator().contains(sender.getID())) {
            if (!event.getArguments().isEmpty()) {
                if (event.getArguments().get(0).equalsIgnoreCase("server")) {//Forcefully adds a Server to the provided Database
                    try {
                        this.maddox.getMySQLHandler().addServerToDatabase(event.getGuild().getId(), this.maddox.getDefaultPrefix(), this.maddox.getDefaultLanguage());
                        final StringBuilder commandsInString = new StringBuilder();
                        this.maddox.getCommandHandler().getCommands().forEach((name, command) -> {
                            if (command.isToggleable()) {
                                commandsInString.append(name).append(";");
                            }
                        });
                        this.maddox.getCacheManager().setEnabledCommands(commandsInString.toString(), event.getGuild().getId());
                        event.reply(server.getName() + " was added to Database");
                    } catch (final NullPointerException e) {
                        System.out.println("Force add Failed");
                        System.out.println("MySQL handler not Present, please enable MySQL in the 'config.yml' if you wish to use it");
                    } catch (final Exception e) {
                        System.out.println("Force add Failed");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
