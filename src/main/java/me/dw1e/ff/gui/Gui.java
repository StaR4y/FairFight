package me.dw1e.ff.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public abstract class Gui implements InventoryHolder {

    private final ItemStack backButton;
    private final Inventory inventory;

    public Gui(int size, String title) {
        inventory = Bukkit.createInventory(this, size, title);

        backButton = new ItemBuilder(Material.STAINED_GLASS_PANE, ChatColor.RED + "返回").setDamage(14)
                .setLore(Collections.singletonList(ChatColor.GRAY + "点击返回上一页")).build();
    }

    public void openGui(HumanEntity player) {
        if (inventory != null) player.openInventory(inventory);
    }

    public abstract void onClick(InventoryClickEvent event);

    public Inventory getInventory() {
        return inventory;
    }

    protected ItemStack getBackButton() {
        return backButton;
    }
}
