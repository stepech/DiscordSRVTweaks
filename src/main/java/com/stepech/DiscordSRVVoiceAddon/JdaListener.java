package com.stepech.DiscordSRVVoiceAddon;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import github.scarsz.discordsrv.objects.managers.AccountLinkManager;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class JdaListener extends ListenerAdapter {

    private final Plugin plugin;

    public JdaListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        List<Member> connectedMembers = event.getChannelJoined().getMembers();
        connectedMembers.remove(event.getMember());
        TextComponent message = Component.text(event.getMember().getEffectiveName() + " joined your voice");
        informPlayers(connectedMembers, message);
    }


    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        List<Member> connectedMembers = event.getChannelLeft().getMembers();
        TextComponent message = Component.text(event.getMember().getEffectiveName() + " left your voice");
        informPlayers(connectedMembers, message);

    }

    private void informPlayers(List<Member> connectedMembers, TextComponent message) {
        AccountLinkManager linkManager = DiscordSRV.getPlugin().getAccountLinkManager();
        for (Member member : connectedMembers) {
            UUID uuid = linkManager.getUuid(member.getId());
            if (uuid != null) {
                Player player = plugin.getServer().getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    player.sendMessage(Identity.nil(), message, MessageType.SYSTEM);
                }
            }
        }
    }
}
