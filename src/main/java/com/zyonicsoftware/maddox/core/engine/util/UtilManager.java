/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.util;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.entities.Member;

public class UtilManager {

    private final Maddox maddox;

    public UtilManager(Maddox maddox) {
        this.maddox = maddox;
    }

    public MemberTools getMemberHandler(Member member) {
        return new MemberTools(this.maddox, member);
    }

}
