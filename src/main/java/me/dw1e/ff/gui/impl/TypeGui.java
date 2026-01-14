package me.dw1e.ff.gui.impl;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.config.CheckValue;
import me.dw1e.ff.gui.Gui;
import me.dw1e.ff.gui.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeGui extends Gui {

    private final Map<Integer, Check> checksById = new HashMap<>();

    public TypeGui(String name, List<Check> list) {
        super(45, name);

        for (int i = 0; i < 9; ++i) getInventory().setItem(i, getBackButton());

        int id = 9;

        for (Check check : list) {
            CheckValue checkValue = FairFight.INSTANCE.getCheckManager().getCheckValue(check);

            getInventory().setItem(id, new ItemBuilder(Material.INK_SACK, getName(check))
                    .setDamage(getDurability(checkValue)).setLore(getLore(check)).build());

            checksById.put(id++, check);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int value = event.getSlot();

        if (value >= 0 && value < 9) {
            FairFight.INSTANCE.getGuiManager().getCheckGui().openGui(event.getWhoClicked());
            return;
        }

        ItemStack item = event.getCurrentItem();
        ClickType click = event.getClick();
        Check check = checksById.get(value);

        if (item != null && item.getItemMeta() != null && check != null
                && item.getItemMeta().getDisplayName().equals(getName(check))) {

            CheckValue checkValue = FairFight.INSTANCE.getCheckManager().getCheckValue(check);

            if (click.isLeftClick()) checkValue.setEnabled(!checkValue.isEnabled());
            else if (click.isRightClick()) checkValue.setPunishable(!checkValue.isPunishable());

            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(getLore(check));

            item.setItemMeta(itemMeta);
            item.setDurability(getDurability(checkValue));

            getInventory().setItem(value, item);
        }
    }

    private short getDurability(CheckValue checkValue) {
        return (short) (checkValue.isEnabled() ? (checkValue.isPunishable() ? 10 : 9) : 8);
    }

    private String getName(Check check) {
        return ChatColor.WHITE + check.getCategory().getName() + ChatColor.DARK_GRAY + " ("
                + ChatColor.GRAY + "类型 " + ChatColor.WHITE + check.getType() + ChatColor.DARK_GRAY + ")";
    }

    private List<String> getLore(Check check) {
        CheckValue checkValue = FairFight.INSTANCE.getCheckManager().getCheckValue(check);

        return Arrays.asList(
                "",
                ChatColor.GRAY + "简介: " + ChatColor.WHITE + check.getDescription(),
                "",
                ChatColor.GRAY + "开启: " + (checkValue.isEnabled() ? ChatColor.GREEN + "是" : ChatColor.RED + "否")
                        + ChatColor.DARK_GRAY + " (左键单击)",
                ChatColor.GRAY + "惩罚: " + (checkValue.isPunishable() ? ChatColor.GREEN + "是" : ChatColor.RED + "否")
                        + ChatColor.DARK_GRAY + " (右键单击)",
                ChatColor.GRAY + "阈值: " + ChatColor.WHITE + checkValue.getPunishVL()
        );
    }
}