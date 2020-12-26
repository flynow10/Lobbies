package com.wagologies.lobbies;

import org.bukkit.entity.Player;

import java.util.List;

public interface IGame {
    void AddPlayers(List<Player> players);

    void StartGame();

    static String GetName() {
        return "undefined";
    }
}
