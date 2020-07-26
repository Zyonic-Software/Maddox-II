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

    public AutomaticRoleManager(final Maddox maddox) {
        this.maddox = maddox;
    }

    public ArrayList<Role> getRolesForAutomaticAssigning(final MaddoxGuild server) {
        final ArrayList<Role> rolesForAutomaticAssigning = new ArrayList<>();

        final String rolesInString = this.maddox.getCacheManager().getRolesForAutomaticAssigning(server.getID()).replace("null", "");

        final String[] roleIDs = rolesInString.split(";");

        for (final String roleID : roleIDs) {
            if (server.getRolesSortedByIDs().containsKey(roleID)) {
                rolesForAutomaticAssigning.add(server.getRolesSortedByIDs().get(roleID));
            }
        }

        return rolesForAutomaticAssigning;
    }

    public void setAutomaticRoles(final ArrayList<Role> rolesForAutomaticAssigning, final MaddoxGuild server) {
        final StringBuilder rolesInString = new StringBuilder();

        rolesForAutomaticAssigning.forEach(role -> {
            rolesInString.append(role.getId()).append(";");
        });

        this.maddox.getCacheManager().setRolesForAutomaticAssigning(rolesInString.toString(), server.getID());
    }

    public boolean removeRolesFromAutomaticAssigning(final ArrayList<Role> roles, final MaddoxGuild server) {
        final String[] rolesInString = {this.maddox.getCacheManager().getRolesForAutomaticAssigning(server.getID())};
        final String originalString = rolesInString[0];
        roles.forEach(role -> {
            rolesInString[0] = rolesInString[0].replace(role.getId() + ";", "");
        });
        if (!originalString.equals(rolesInString[0])) {
            this.maddox.getCacheManager().setRolesForAutomaticAssigning(rolesInString[0], server.getID());
            return true;
        } else {
            return false;
        }
    }

    public boolean addRolesToAutomaticAssigning(final ArrayList<Role> roles, final MaddoxGuild server) {
        final StringBuilder rolesInString = new StringBuilder();
        String originalRolesInString = this.maddox.getCacheManager().getRolesForAutomaticAssigning(server.getID());
        if (originalRolesInString == null) {
            originalRolesInString = "";
        }
        originalRolesInString = originalRolesInString.replace("null", "");
        rolesInString.append(rolesInString);
        final String originalString = rolesInString.toString();
        roles.forEach(role -> {
            if (!rolesInString.toString().contains(role.getId())) {
                rolesInString.append(role.getId()).append(";");
            }
        });
        if (!rolesInString.toString().equals(originalString)) {
            this.maddox.getCacheManager().setRolesForAutomaticAssigning(rolesInString.toString(), server.getID());
            return true;
        } else {
            return false;
        }
    }

}
