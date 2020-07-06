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

        String rolesInString = this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID()).replace("null", "");

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

    public boolean removeRolesFromAutomaticAssigning(ArrayList<Role> roles, MaddoxGuild server) {
        final String[] rolesInString = {this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID())};
        String originalString = rolesInString[0];
        roles.forEach(role -> {
            rolesInString[0] = rolesInString[0].replace(role.getId() + ";", "");
        });
        if (!originalString.equals(rolesInString[0])) {
            this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesInString[0], server.getID());
            return true;
        } else {
            return false;
        }
    }

    public boolean addRolesToAutomaticAssigning(ArrayList<Role> roles, MaddoxGuild server) {
        StringBuilder rolesInString = new StringBuilder();
        String originalRolesInString = this.maddox.getMySQLHandler().getRolesForAutomaticAssigning(server.getID());
        if (originalRolesInString == null) {
            originalRolesInString = "";
        }
        originalRolesInString = originalRolesInString.replace("null", "");
        rolesInString.append(rolesInString);
        String originalString = rolesInString.toString();
        roles.forEach(role -> {
            if (!rolesInString.toString().contains(role.getId())) {
                rolesInString.append(role.getId()).append(";");
            }
        });
        if (!rolesInString.toString().equals(originalString)) {
            this.maddox.getMySQLHandler().setRolesForAutomaticAssigning(rolesInString.toString(), server.getID());
            return true;
        } else {
            return false;
        }
    }

}
