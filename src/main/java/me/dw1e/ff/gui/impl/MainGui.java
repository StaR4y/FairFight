package me.dw1e.ff.gui.impl;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.gui.Gui;
import me.dw1e.ff.gui.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;

public final class MainGui extends Gui {

    public MainGui() {
        super(27, "FairFight");

        getInventory().setItem(10, new ItemBuilder(Material.PAPER, ChatColor.WHITE + "重载配置")
                .setLore(Collections.singletonList(ChatColor.GRAY + "点击重新加载配置文件")).build());

        getInventory().setItem(13, new ItemBuilder(Material.BOOK, ChatColor.WHITE + "检查管理")
                .setLore(Collections.singletonList(ChatColor.GRAY + "点击打开检查管理页")).build());

        getInventory().setItem(16, new ItemBuilder(Material.WATER_BUCKET, ChatColor.WHITE + "重置VL")
                .setLore(Collections.singletonList(ChatColor.GRAY + "点击重置所有玩家的违规次数")).build());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int value = event.getSlot();

        HumanEntity clicker = event.getWhoClicked();

        if (value == 10) {
            FairFight.INSTANCE.reloadConfig();

            clicker.sendMessage(FairFight.PREFIX + ChatColor.GREEN + " 已重载配置文件");

            clicker.closeInventory();
        } else if (value == 13) FairFight.INSTANCE.getGuiManager().getCheckGui().openGui(clicker);
        else if (value == 16) {
            FairFight.INSTANCE.getDataManager().getDataMap().values().forEach(PlayerData::resetVL);

            clicker.sendMessage(FairFight.PREFIX + ChatColor.GREEN + " 已重置所有玩家的违规次数");

            clicker.closeInventory();
        }
    }
}