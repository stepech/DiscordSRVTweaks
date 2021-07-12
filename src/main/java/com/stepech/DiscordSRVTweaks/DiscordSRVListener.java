package com.stepech.DiscordSRVTweaks;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePreBroadcastEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.dependencies.kyori.adventure.text.Component;
import github.scarsz.discordsrv.dependencies.kyori.adventure.text.TextComponent;
import github.scarsz.discordsrv.dependencies.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DiscordSRVListener {

    private final Plugin plugin;
    public static JdaListener jdaListener;
    static final Pattern discordImageLink = Pattern.compile("https://cdn\\.discordapp\\.com/attachments/\\d+/\\d+/\\w+\\.(?:png|jpg)");

    public DiscordSRVListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        jdaListener = new JdaListener(plugin);
        DiscordUtil.getJda().addEventListener(jdaListener);

        plugin.getLogger().info("DiscordAddon successfully hooked into DiscordSRV");
    }

    @Subscribe
    public void DiscordGuildMessagePreBroadcastEvent(DiscordGuildMessagePreBroadcastEvent event) {
        Matcher matcher = discordImageLink.matcher(event.getMessage().toString());
        if (matcher.find()) {
            String originalMessage = LegacyComponentSerializer.legacyAmpersand().serialize(event.getMessage());
            Component newMessage = LegacyComponentSerializer.legacyAmpersand().deserialize(originalMessage.replace(matcher.group(0), " sent a picture."));
            event.setMessage(newMessage);

        }

    }


}
