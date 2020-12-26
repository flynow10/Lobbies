package com.wagologies.lobbies.commands;

import com.wagologies.lobbies.Lobbies;
import com.wagologies.lobbies.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewLobby implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1)
            return false;
        HashMap<String, Class<?>> nameClassHashMap = new HashMap<>();
        for (Class<?> aClass : Lobbies.getClasses()) {
            try {
                nameClassHashMap.put((String) aClass.getMethod("GetName").invoke(null), aClass);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if(nameClassHashMap.containsKey(args[0]))
            Lobbies.getInstance().lobbies.add(new Lobby(nameClassHashMap.get(args[0])));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("newlobby")) {
                Lobbies.getInstance().getLogger().info(String.valueOf(args.length));
                if (args.length == 1) {
                    List<String> tabs = new ArrayList<>();
                    for (Class<?> aClass : Lobbies.getClasses()) {
                        try {
                            tabs.add((String) aClass.getMethod("GetName").invoke(null));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    return tabs;
                }
            }
        }
        return null;
    }
}
