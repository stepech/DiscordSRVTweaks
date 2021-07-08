package com.stepech.DiscordSRVVoiceAddon;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

public class DiscordSRVListener {

    private final Plugin plugin;
    public static JdaListener jdaListener;

    public DiscordSRVListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        jdaListener = new JdaListener(plugin);
        DiscordUtil.getJda().addEventListener(jdaListener);

        plugin.getLogger().info("DiscordAddon successfully hooked into DiscordSRV");
    }



}
