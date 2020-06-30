/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.util;

import com.zyonicsoftware.maddox.core.main.Maddox;
import net.dv8tion.jda.api.entities.Member;

public class MemberTools {

    private final Maddox maddox;
    private final Member member;

    public MemberTools(Maddox maddox, Member member) {
        this.maddox = maddox;
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public String getAvatarURL(int imageSize) {
        return "https://cdn.discordapp.com/avatars/" + member.getId() + "/" + member.getUser().getAvatarId() + ".png?size=" + imageSize;
    }

}
