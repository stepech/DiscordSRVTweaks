package com.stepech.DiscordSRVVoiceAddon;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class VoiceAddon extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    @Override
    public void onEnable() {
        DiscordSRV.api.subscribe(discordsrvListener);

    }

    @Override
    public void onDisable() {
        DiscordUtil.getJda().removeEventListener(DiscordSRVListener.jdaListener);
        DiscordSRV.api.unsubscribe(discordsrvListener);
        this.getLogger().info("Disconnected DiscordSRV hooks");

    }
}
