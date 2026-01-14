package me.dw1e.ff.check.impl.inventory;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketWindowClick;
import org.bukkit.Material;

@CheckInfo(category = Category.INVENTORY, type = "C", desc = "检查异常的背包点击速度", maxVL = 5)
public final class InventoryC extends Check {

    private int lastClickedTicks, lastSlot;
    private Material lastClickedItem;

    public InventoryC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketWindowClick) {
            CPacketWindowClick wrapper = (CPacketWindowClick) packet;

            CPacketWindowClick.ClickType clickType = wrapper.getAction();

            Material clickedItem = wrapper.getClickedItem().getType();

            // 排除掉 Shift+双击 可以一键把同类型的物品快速转移的情况
            if ((clickType.isShiftClick() || clickType == CPacketWindowClick.ClickType.DRAG)
                    && (clickedItem == lastClickedItem || clickedItem == Material.AIR)) return;

            int clickedTicks = data.getTick(), slot = wrapper.getSlot();

            int delta = clickedTicks - lastClickedTicks;

            // 就是一个瞬间点击多个物品检查, 点击速度调1tick就绕了

            if (delta == 0 && slot != lastSlot) flag("clickType=" + clickType);

            lastClickedItem = clickedItem;
            lastClickedTicks = clickedTicks;
            lastSlot = slot;
        }
    }
}
