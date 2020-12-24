package com.wagologies.lobbies;

import com.wagologies.utilsplugin.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public final class Lobbies extends JavaPlugin {

    private static Lobbies instance;
    private static Scanner scanner;

    @Override
    public void onEnable() {
        instance = this;
        scanner = new SubTypesScanner(false);
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
                    if(!c.equals(Game.class) && Game.class.isAssignableFrom(c))
                        classes1.add(c);
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