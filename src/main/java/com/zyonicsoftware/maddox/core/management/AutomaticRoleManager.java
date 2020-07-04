/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.management;

import com.zyonicsoftware.maddox.core.engine.objects.MaddoxGuild;
import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class AutomaticRoleManager {

    private final Maddox maddox;

    public AutomaticRoleManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public ArrayList<Role> getRolesForAutomaticAssigning(MaddoxGuild server) {
        ArrayList<Role> rolesForAutomaticAssigning = new ArrayList<>();

        String rolesInString = this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID());

        String[] roleIDs = rolesInString.split(";");

        for (String roleID : roleIDs) {
            if (server.getRolesSortedByIDs().containsKey(roleID)) {
                rolesForAutomaticAssigning.add(server.getRolesSortedByIDs().get(roleID));
            }
        }

        return rolesForAutomaticAssigning;
    }

    public void setAutomaticRoles(ArrayList<Role> rolesForAutomaticAssigning, MaddoxGuild server) {
        StringBuilder rolesInString = new StringBuilder();

        rolesForAutomaticAssigning.forEach(role -> {
            rolesInString.append(role.getId()).append(";");
        });

        this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesInString.toString(), server.getID());
    }

}
