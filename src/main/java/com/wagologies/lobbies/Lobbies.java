package com.wagologies.lobbies;

import com.wagologies.lobbies.commands.Games;
import com.wagologies.lobbies.commands.NewLobby;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Lobbies extends JavaPlugin {

    private static Lobbies instance;
    public List<Lobby> lobbies = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        NewLobby newLobbyCommand = new NewLobby();
        getCommand("newlobby").setExecutor(newLobbyCommand);
        getCommand("newlobby").setTabCompleter(newLobbyCommand);
        getCommand("games").setExecutor(new Games());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static List<Class<?>> getClasses()
    {
        List<Class<?>> classes = new ArrayList<>();
        try {
            classes.add(Class.forName("com.wagologies.bedwarsv2.game.Game"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            ClassLoader classLoader = plugin.getClass().getClassLoader();
            try {
                Field f = ClassLoader.class.getDeclaredField("classes");
                f.setAccessible(true);
                List<Class<?>> classes1 = new ArrayList<>((Vector<Class<?>>) f.get(classLoader));
                for (Class<?> c : classes1) {
                    if(IGame.class.isAssignableFrom(c))
                        classes.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        return classes;
    }

    public static Lobbies getInstance()
    {
        return instance;
    }
}