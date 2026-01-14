package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketHeldItemSlot;

@CheckInfo(category = Category.BAD_PACKET, type = "M", desc = "无效的物品栏位", maxVL = 1)
public final class BadPacketM extends Check {

    public BadPacketM(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketHeldItemSlot) {
            int slot = ((CPacketHeldItemSlot) packet).getSlot();

            // 0等于1号位, 1等于2号位, 以此类推

            if (slot < 0 || slot > 8) flag("slot=" + slot);
        }
    }

}
