/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.objects;

import com.zyonicsoftware.maddox.core.savestructure.CacheManager;
import de.daschi.javalanguageapi.api.LanguageAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.GuildManager;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.MemberAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.RoleOrderAction;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import net.dv8tion.jda.api.utils.cache.MemberCacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import net.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import net.dv8tion.jda.api.utils.concurrent.Task;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MaddoxGuild { //Huge thanks to Spark61 for saving my fingers from typing all this crap

    private final Guild guild;
    private String prefix;
    private String language;
    private CacheManager cacheManager;

    public MaddoxGuild(final Guild guild, final String prefix) {
        this.guild = guild;
        this.prefix = prefix;
        this.language = LanguageAPI.getLanguageHandler().getLanguage();
    }

    public MaddoxGuild(final Guild guild, final String prefix, final CacheManager cacheManager) {
        this.guild = guild;
        this.prefix = prefix;
        this.language = cacheManager.getLanguage(guild.getId());
        this.cacheManager = cacheManager;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public String getID() {
        return this.guild.getId();
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        if (this.cacheManager != null) {
            this.cacheManager.setLanguage(language, this.guild.getId());
            this.language = language;
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(final String prefix) {
        if (this.cacheManager != null) {
            this.prefix = prefix;
            this.cacheManager.setPrefix(prefix, this.getID());
        }
    }

    public ArrayList<String> getRoleIDs() {
        final ArrayList<String> roleIDs = new ArrayList<>();
        this.guild.getRoles().forEach(role -> {
            roleIDs.add(role.getId());
        });
        return roleIDs;
    }

    public HashMap<String, Role> getRolesSortedByIDs() {
        final HashMap<String, Role> roleIDs = new HashMap<>();
        this.guild.getRoles().forEach(role -> {
            roleIDs.put(role.getId(), role);
        });
        return roleIDs;
    }

    @Nonnull
    public RestAction<EnumSet<Region>> retrieveRegions(final boolean b) {
        return this.guild.retrieveRegions(b);
    }

    @Nonnull
    public MemberAction addMember(@Nonnull final String s, @Nonnull final String s1) {
        return this.guild.addMember(s, s1);
    }

    public boolean isLoaded() {
        return this.guild.isLoaded();
    }

    public void pruneMemberCache() {
        this.guild.pruneMemberCache();
    }

    public boolean unloadMember(final long l) {
        return this.guild.unloadMember(l);
    }

    public int getMemberCount() {
        return this.guild.getMemberCount();
    }

    @Nonnull
    public String getName() {
        return this.guild.getName();
    }

    @Nullable
    public String getIconId() {
        return this.guild.getIconId();
    }

    @Nullable
    public String getIconUrl() {
        return this.guild.getIconUrl();
    }

    @Nonnull
    public Set<String> getFeatures() {
        return this.guild.getFeatures();
    }

    @Nullable
    public String getSplashId() {
        return this.guild.getSplashId();
    }

    @Nonnull
    public RestAction<String> retrieveVanityUrl() {
        return this.guild.retrieveVanityUrl();
    }

    @Nullable
    public String getVanityCode() {
        return this.guild.getVanityCode();
    }

    @Nullable
    public String getDescription() {
        return this.guild.getDescription();
    }

    @Nullable
    public String getBannerId() {
        return this.guild.getBannerId();
    }

    @Nullable
    public String getBannerUrl() {
        return this.guild.getBannerUrl();
    }

    @Nonnull
    public Guild.BoostTier getBoostTier() {
        return this.guild.getBoostTier();
    }

    public int getBoostCount() {
        return this.guild.getBoostCount();
    }

    @Nonnull
    public List<Member> getBoosters() {
        return this.guild.getBoosters();
    }

    public int getMaxMembers() {
        return this.guild.getMaxMembers();
    }

    public int getMaxPresences() {
        return this.guild.getMaxPresences();
    }

    @Nonnull
    public RestAction<Guild.MetaData> retrieveMetaData() {
        return this.guild.retrieveMetaData();
    }

    @Nullable
    public VoiceChannel getAfkChannel() {
        return this.guild.getAfkChannel();
    }

    @Nullable
    public TextChannel getSystemChannel() {
        return this.guild.getSystemChannel();
    }

    @Nullable
    public Member getOwner() {
        return this.guild.getOwner();
    }

    public long getOwnerIdLong() {
        return this.guild.getOwnerIdLong();
    }

    @Nonnull
    public String getOwnerId() {
        return this.guild.getOwnerId();
    }

    @Nonnull
    public Guild.Timeout getAfkTimeout() {
        return this.guild.getAfkTimeout();
    }

    @Nonnull
    public Region getRegion() {
        return this.guild.getRegion();
    }

    @Nonnull
    public String getRegionRaw() {
        return this.guild.getRegionRaw();
    }

    public boolean isMember(@Nonnull final User user) {
        return this.guild.isMember(user);
    }

    @Nonnull
    public Member getSelfMember() {
        return this.guild.getSelfMember();
    }

    @Nonnull
    public User getSelfUser() {
        return this.guild.getJDA().getSelfUser();
    }

    @Nullable
    public Member getMember(@Nonnull final User user) {
        return this.guild.getMember(user);
    }

    @Nullable
    public Member getMemberById(@Nonnull final String userId) {
        return this.guild.getMemberById(userId);
    }

    @Nonnull
    public List<Member> getMembers() {
        return this.guild.getMembers();
    }

    @Nonnull
    public MemberCacheView getMemberCache() {
        return this.guild.getMemberCache();
    }

    @Nullable
    public GuildChannel getGuildChannelById(@Nonnull final String id) {
        return this.guild.getGuildChannelById(id);
    }

    @Nullable
    public Category getCategoryById(@Nonnull final String id) {
        return this.guild.getCategoryById(id);
    }

    @Nonnull
    public List<Category> getCategories() {
        return this.guild.getCategories();
    }

    @Nonnull
    public SortedSnowflakeCacheView<Category> getCategoryCache() {
        return this.guild.getCategoryCache();
    }

    @Nullable
    public StoreChannel getStoreChannelById(@Nonnull final String id) {
        return this.guild.getStoreChannelById(id);
    }

    @Nonnull
    public List<StoreChannel> getStoreChannels() {
        return this.guild.getStoreChannels();
    }

    @Nonnull
    public SortedSnowflakeCacheView<StoreChannel> getStoreChannelCache() {
        return this.guild.getStoreChannelCache();
    }


    @Nullable
    public TextChannel getTextChannelById(@Nonnull final String id) {
        return this.guild.getTextChannelById(id);
    }

    @Nonnull
    public List<TextChannel> getTextChannels() {
        return this.guild.getTextChannels();
    }

    @Nonnull
    public SortedSnowflakeCacheView<TextChannel> getTextChannelCache() {
        return this.guild.getTextChannelCache();
    }


    @Nullable
    public VoiceChannel getVoiceChannelById(@Nonnull final String id) {
        return this.guild.getVoiceChannelById(id);
    }


    @Nonnull
    public List<VoiceChannel> getVoiceChannels() {
        return this.guild.getVoiceChannels();
    }

    @Nonnull

    public SortedSnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return this.guild.getVoiceChannelCache();
    }


    @Nonnull
    public List<GuildChannel> getChannels() {
        return this.guild.getChannels();
    }

    @Nonnull

    public List<GuildChannel> getChannels(final boolean b) {
        return this.guild.getChannels(b);
    }


    @Nullable
    public Role getRoleById(@Nonnull final String id) {
        return this.guild.getRoleById(id);
    }


    @Nonnull
    public List<Role> getRoles() {
        return this.guild.getRoles();
    }

    @Nonnull

    public SortedSnowflakeCacheView<Role> getRoleCache() {
        return this.guild.getRoleCache();
    }


    @Nullable
    public Emote getEmoteById(@Nonnull final String id) {
        return this.guild.getEmoteById(id);
    }


    @Nonnull
    public List<Emote> getEmotes() {
        return this.guild.getEmotes();
    }

    @Nonnull

    public SnowflakeCacheView<Emote> getEmoteCache() {
        return this.guild.getEmoteCache();
    }

    @Nonnull

    public RestAction<List<ListedEmote>> retrieveEmotes() {
        return this.guild.retrieveEmotes();
    }

    @Nonnull

    public RestAction<ListedEmote> retrieveEmoteById(@Nonnull final String s) {
        return this.guild.retrieveEmoteById(s);
    }


    @Nonnull
    public RestAction<List<Guild.Ban>> retrieveBanList() {
        return this.guild.retrieveBanList();
    }


    @Nonnull
    public RestAction<Guild.Ban> retrieveBanById(@Nonnull final String userId) {
        return this.guild.retrieveBanById(userId);
    }


    @Nonnull
    public RestAction<Guild.Ban> retrieveBan(@Nonnull final User bannedUser) {
        return this.guild.retrieveBan(bannedUser);
    }

    @Nonnull

    public RestAction<Integer> retrievePrunableMemberCount(final int i) {
        return this.guild.retrievePrunableMemberCount(i);
    }


    @Nonnull
    public Role getPublicRole() {
        return this.guild.getPublicRole();
    }

    @Nullable

    public TextChannel getDefaultChannel() {
        return this.guild.getDefaultChannel();
    }


    @Nonnull
    public GuildManager getManager() {
        return this.guild.getManager();
    }


    @Nonnull
    public AuditLogPaginationAction retrieveAuditLogs() {
        return this.guild.retrieveAuditLogs();
    }


    @Nonnull
    public RestAction<Void> leave() {
        return this.guild.leave();
    }

    @Nonnull

    public RestAction<Void> delete() {
        return this.guild.delete();
    }

    @Nonnull

    public RestAction<Void> delete(@Nullable final String s) {
        return this.guild.delete(s);
    }


    @Nonnull
    public AudioManager getAudioManager() {
        return this.guild.getAudioManager();
    }


    @Nonnull
    public JDA getJDA() {
        return this.guild.getJDA();
    }


    @Nonnull
    public RestAction<List<Invite>> retrieveInvites() {
        return this.guild.retrieveInvites();
    }


    @Nonnull
    public RestAction<List<Webhook>> retrieveWebhooks() {
        return this.guild.retrieveWebhooks();
    }


    @Nonnull
    public List<GuildVoiceState> getVoiceStates() {
        return this.guild.getVoiceStates();
    }

    @Nonnull

    public Guild.VerificationLevel getVerificationLevel() {
        return this.guild.getVerificationLevel();
    }

    @Nonnull

    public Guild.NotificationLevel getDefaultNotificationLevel() {
        return this.guild.getDefaultNotificationLevel();
    }

    @Nonnull

    public Guild.MFALevel getRequiredMFALevel() {
        return this.guild.getRequiredMFALevel();
    }

    @Nonnull

    public Guild.ExplicitContentLevel getExplicitContentLevel() {
        return this.guild.getExplicitContentLevel();
    }


    public boolean checkVerification() {
        return this.guild.checkVerification();
    }


    public boolean isAvailable() {
        return this.guild.isAvailable();
    }

    @Nonnull

    public CompletableFuture<Void> retrieveMembers() {
        return this.guild.retrieveMembers();
    }

    @Nonnull

    public RestAction<Member> retrieveMemberById(final long l, final boolean b) {
        return this.guild.retrieveMemberById(l, b);
    }

    @Nonnull

    public Task<List<Member>> retrieveMembersByIds(final boolean b, @Nonnull final long... longs) {
        return this.guild.retrieveMembersByIds(b, longs);
    }

    @Nonnull

    public Task<List<Member>> retrieveMembersByPrefix(@Nonnull final String s, final int i) {
        return this.guild.retrieveMembersByPrefix(s, i);
    }


    @Nonnull
    public RestAction<Void> moveVoiceMember(@Nonnull final Member member, @Nullable final VoiceChannel voiceChannel) {
        return this.guild.moveVoiceMember(member, voiceChannel);
    }


    @Nonnull
    public RestAction<Void> kickVoiceMember(@Nonnull final Member member) {
        return this.guild.kickVoiceMember(member);
    }


    @Nonnull
    public AuditableRestAction<Void> modifyNickname(@Nonnull final Member member, @Nullable final String nickname) {
        return this.guild.modifyNickname(member, nickname);
    }

    @Nonnull

    public AuditableRestAction<Integer> prune(final int i, final boolean b, @Nonnull final Role... roles) {
        return this.getGuild().prune(i, b, roles);
    }


    @Nonnull
    public AuditableRestAction<Void> kick(@Nonnull final Member member, @Nullable final String reason) {
        return this.guild.kick(member, reason);
    }


    @Nonnull
    public AuditableRestAction<Void> kick(@Nonnull final String userId, @Nullable final String reason) {
        return this.guild.kick(userId, reason);
    }


    @Nonnull
    public AuditableRestAction<Void> kick(@Nonnull final Member member) {
        return this.guild.kick(member);
    }


    @Nonnull
    public AuditableRestAction<Void> kick(@Nonnull final String userId) {
        return this.guild.kick(userId);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final User user, final int delDays, @Nullable final String reason) {
        return this.guild.ban(user, delDays, reason);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final String userId, final int delDays, @Nullable final String reason) {
        return this.guild.ban(userId, delDays, reason);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final Member member, final int delDays, @Nullable final String reason) {
        return this.guild.ban(member, delDays, reason);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final Member member, final int delDays) {
        return this.guild.ban(member, delDays);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final User user, final int delDays) {
        return this.guild.ban(user, delDays);
    }


    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull final String userId, final int delDays) {
        return this.guild.ban(userId, delDays);
    }


    @Nonnull
    public AuditableRestAction<Void> unban(@Nonnull final User user) {
        return this.guild.unban(user);
    }


    @Nonnull
    public AuditableRestAction<Void> unban(@Nonnull final String userId) {
        return this.guild.unban(userId);
    }

    @Nonnull

    public AuditableRestAction<Void> deafen(@Nonnull final Member member, final boolean b) {
        return this.guild.deafen(member, b);
    }

    @Nonnull

    public AuditableRestAction<Void> mute(@Nonnull final Member member, final boolean b) {
        return this.guild.mute(member, b);
    }


    @Nonnull
    public AuditableRestAction<Void> addRoleToMember(@Nonnull final Member member, @Nonnull final Role role) {
        return this.guild.addRoleToMember(member, role);
    }


    @Nonnull
    public AuditableRestAction<Void> addRoleToMember(@Nonnull final String userId, @Nonnull final Role role) {
        return this.guild.addRoleToMember(userId, role);
    }


    @Nonnull
    public AuditableRestAction<Void> removeRoleFromMember(@Nonnull final Member member, @Nonnull final Role role) {
        return this.guild.removeRoleFromMember(member, role);
    }


    @Nonnull
    public AuditableRestAction<Void> removeRoleFromMember(@Nonnull final String userId, @Nonnull final Role role) {
        return this.guild.removeRoleFromMember(userId, role);
    }


    @Nonnull
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull final Member member, @Nullable final Collection<Role> rolesToAdd, @Nullable final Collection<Role> rolesToRemove) {
        return this.guild.modifyMemberRoles(member, rolesToAdd, rolesToRemove);
    }


    @Nonnull
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull final Member member, @Nonnull final Role... roles) {
        return this.guild.modifyMemberRoles(member, roles);
    }


    @Nonnull
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull final Member member, @Nonnull final Collection<Role> roles) {
        return this.guild.modifyMemberRoles(member, roles);
    }

    @Nonnull

    public AuditableRestAction<Void> transferOwnership(@Nonnull final Member member) {
        return this.guild.transferOwnership(member);
    }


    @Nonnull
    public ChannelAction<TextChannel> createTextChannel(@Nonnull final String name) {
        return this.guild.createTextChannel(name);
    }


    @Nonnull
    public ChannelAction<VoiceChannel> createVoiceChannel(@Nonnull final String name) {
        return this.guild.createVoiceChannel(name);
    }


    @Nonnull
    public ChannelAction<Category> createCategory(@Nonnull final String name) {
        return this.guild.createCategory(name);
    }


    @Nonnull
    public <T extends GuildChannel> ChannelAction<T> createCopyOfChannel(@Nonnull final T channel) {
        return this.guild.createCopyOfChannel(channel);
    }


    @Nonnull
    public RoleAction createRole() {
        return this.guild.createRole();
    }


    @Nonnull
    public RoleAction createCopyOfRole(@Nonnull final Role role) {
        return this.guild.createCopyOfRole(role);
    }


    @Nonnull
    public AuditableRestAction<Emote> createEmote(@Nonnull final String name, @Nonnull final Icon icon, @Nonnull final Role... roles) {
        return this.guild.createEmote(name, icon, roles);
    }


    @Nonnull
    public ChannelOrderAction modifyCategoryPositions() {
        return this.guild.modifyCategoryPositions();
    }


    @Nonnull
    public ChannelOrderAction modifyTextChannelPositions() {
        return this.guild.modifyTextChannelPositions();
    }


    @Nonnull
    public ChannelOrderAction modifyVoiceChannelPositions() {
        return this.guild.modifyVoiceChannelPositions();
    }


    @Nonnull
    public CategoryOrderAction modifyTextChannelPositions(@Nonnull final Category category) {
        return this.guild.modifyTextChannelPositions(category);
    }


    @Nonnull
    public CategoryOrderAction modifyVoiceChannelPositions(@Nonnull final Category category) {
        return this.guild.modifyVoiceChannelPositions(category);
    }


    @Nonnull
    public RoleOrderAction modifyRolePositions() {
        return this.guild.modifyRolePositions();
    }


    @Nonnull
    public RoleOrderAction modifyRolePositions(final boolean useAscendingOrder) {
        return this.guild.modifyRolePositions(useAscendingOrder);
    }


    public long getIDLong() {
        return this.guild.getIdLong();
    }


    @Nonnull
    public OffsetDateTime getTimeCreated() {
        return this.guild.getTimeCreated();
    }
}
