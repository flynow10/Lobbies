package com.wagologies.lobbies.commands;

import com.wagologies.lobbies.Lobbies;
import com.wagologies.lobbies.Lobby;
import com.wagologies.utilsplugin.utils.gui.Gui;
import com.wagologies.utilsplugin.utils.gui.pagenation.Page;
import com.wagologies.utilsplugin.utils.gui.pagenation.Pagenation;
import com.wagologies.utilsplugin.utils.gui.pagenation.Preset;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Games implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;
        if(args.length != 0)
            return false;
        new Gui((Player) sender, new Pagenation(new Preset() {
            @Override
            public String getName() {
                return "All Games";
            }

            @Override
            public int getSize() {
                return 36;
            }

            @Override
            public List<Page> getPages() {
                HashMap<Function<Player, ItemStack>, Consumer<Player>> items = new LinkedHashMap<>();
                for (Lobby lobby : Lobbies.getInstance().lobbies) {
                    items.put(player -> {
                        ItemStack stack;
                        switch (lobby.getTypeName())
                        {
                            case "Bedwars":
                                stack = new ItemStack(Material.BED);
                                break;
                            default:
                                stack = new ItemStack(Material.PAPER);
                        }
                        List<String> lore = new ArrayList<>();
                        lore.add("Players:");
                        for (Player player1 : lobby.getPlayerList()) {
                            lore.add(ChatColor.RESET + ChatColor.GRAY.toString() + "  - " + player1.getDisplayName());
                        }
                        ItemMeta meta = stack.getItemMeta();
                        meta.setLore(lore);
                        stack.setItemMeta(meta);
                        return stack;
                    }, player -> {
                        lobby.AddPlayer(player);
                        player.closeInventory();
                    });
                }
                return createPageList(items);
            }
            private List<Page> createPageList(HashMap<Function<Player, ItemStack>, Consumer<Player>> items)
            {
                List<Page> pages = new ArrayList<>();
                int usableRows = (getSize()/9) - 2;
                int pageSpace = 7*usableRows;
                for (int i = 0; i < (int) Math.ceil((float) pageSpace / items.size()); i++) {
                    LinkedHashMap<Function<Player, ItemStack>, Consumer<Player>> currentPageItems = new LinkedHashMap<>();
                    for (int j = i * pageSpace; j < Math.min(items.size(), (i + 1) * pageSpace); j++) {
                        Function<Player, ItemStack> itemStack = (Function<Player, ItemStack>)items.keySet().toArray()[j];
                        Consumer<Player> itemCallback = (Consumer<Player>) items.values().toArray()[j];
                        currentPageItems.put(itemStack, itemCallback);
                    }
                    pages.add(new Page(currentPageItems));
                }
                return pages;
            }
        }));
        return true;
    }
}
