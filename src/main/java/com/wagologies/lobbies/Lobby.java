package com.wagologies.lobbies;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Lobby implements Listener {

    private List<Player> playerList = new ArrayList<>();

    private Class<?> gameClass;
    private IGame game;

    public Lobby(Class<?> gameClass)
    {
        this.gameClass = gameClass;
    }

    public void AddPlayer(Player player)
    {
        playerList.add(player);
    }

    public void PlayerLeave(Player player)
    {
        if(playerList.contains(player))
            playerList.remove(player);
    }

    public void StartGame()
    {
        try {
            game = (IGame) gameClass.getConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        game.AddPlayers(playerList);
        game.StartGame();
    }

    public boolean isPlayerInLobby(Player player)
    {
        return playerList.contains(player);
    }

    public String getTypeName()
    {
        try {
            return (String) gameClass.getMethod("GetName").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return "unknown";
        }
    }

    public List<Player> getPlayerList() { return playerList; }
}
