package me.dw1e.ff.check.impl.inventory;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.INVENTORY, type = "A", desc = "检查在背包开启时疾跑/潜行/使用物品", maxVL = 5)
public final class InventoryA extends Check {

    public InventoryA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isPosition()
                && data.isInventoryOpen() && !data.isInVehicle()
                && (data.isSprinting() || data.isSneaking() || data.isUsingItem())) {

            data.closeInventory();
            flag();

            if (data.isSneaking()) data.resetSneak();
            if (data.isUsingItem()) data.randomChangeSlot();
        }
    }
}
