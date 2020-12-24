package com.wagologies.lobbies;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class Lobbies extends JavaPlugin {

    private static Lobbies instance;

    @Override
    public void onEnable() {
        instance = this;
        new Lobby();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static List<Class<?>> getClasses()
    {
        List<Class<?>> classes = new ArrayList<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
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
        }
        return classes;
    }

    public static Lobbies getInstance()
    {
        return instance;
    }
}