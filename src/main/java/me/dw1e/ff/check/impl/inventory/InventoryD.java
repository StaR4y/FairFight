package me.dw1e.ff.check.impl.inventory;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.INVENTORY, type = "D", desc = "检查在背包关闭后立即开始疾跑", maxVL = 5)
public final class InventoryD extends Check {

    private boolean lastInventoryOpen;

    public InventoryD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            boolean inventoryOpen = data.isInventoryOpen();

            // 1.7&1.8客户端做不到在关闭背包的下一刻立即开始疾跑, 本反作弊不考虑高版本情况
            if (lastInventoryOpen && !inventoryOpen && data.isSprinting() && !data.isInVehicle()) flag();

            lastInventoryOpen = inventoryOpen;
        }
    }
}
