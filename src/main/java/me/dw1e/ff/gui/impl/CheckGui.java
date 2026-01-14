package me.dw1e.ff.gui.impl;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.gui.Gui;
import me.dw1e.ff.gui.GuiManager;
import me.dw1e.ff.gui.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CheckGui extends Gui {

    private final Map<Integer, Category> categoryById = new HashMap<>();

    public CheckGui(List<Check> checks) {
        super(45, "检查管理");

        for (int i = 0; i < 9; ++i) getInventory().setItem(i, getBackButton());

        int id = 9;

        for (Category category : Category.values()) {
            long total = checks.stream().filter(check -> check.getCategory().equals(category)).count();

            getInventory().setItem(id, new ItemBuilder(Material.BOOK, ChatColor.WHITE + category.getName())
                    .setAmount((int) total).build());

            categoryById.put(id++, category);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int value = event.getSlot();

        GuiManager guiManager = FairFight.INSTANCE.getGuiManager();

        if (value >= 0 && value < 9) {
            guiManager.getMainGui().openGui(event.getWhoClicked());
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem != null && currentItem.getItemMeta() != null) {
            Category category = categoryById.get(value);
            String displayName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());

            if (category != null && category.getName().equals(displayName))
                guiManager.getTypeGui(category).openGui(event.getWhoClicked());
        }
    }
}