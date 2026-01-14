package me.dw1e.ff.check.impl.inventory;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.INVENTORY, type = "B", desc = "检查在背包开启时移动视角", maxVL = 5)
public final class InventoryB extends Check {

    public InventoryB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isRotation()
                && !data.isInVehicle() && data.getTickSinceTeleport() > 2 // 传送时误判
                && data.getInventoryOpenTicks() > 1) { // 玩家可以在背包开启的第一刻移动视角

            data.closeInventory();
            flag();
        }
    }
}
