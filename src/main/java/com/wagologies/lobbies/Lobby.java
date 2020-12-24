package com.wagologies.lobbies;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Lobby implements Listener {

    public Lobby()
    {
        Bukkit.getPluginManager().registerEvents(this, Lobbies.getInstance());
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
        event.getPlayer().sendMessage("Game");
        for (Class<?> aClass : Lobbies.getClasses()) {
            event.getPlayer().sendMessage(aClass.getCanonicalName());
        }
    }
}
