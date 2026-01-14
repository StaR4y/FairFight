package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketHeldItemSlot;
import me.dw1e.ff.packet.wrapper.server.SPacketHeldItemSlot;

@CheckInfo(category = Category.BAD_PACKET, type = "H", desc = "发送相同的切换物品栏数据包", maxVL = 1)
public final class BadPacketH extends Check {

    private int lastSlot = -1;
    private boolean server;

    public BadPacketH(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketHeldItemSlot && data.getTick() > 4) {
            int slot = ((CPacketHeldItemSlot) packet).getSlot();

            // 服务器设置时不会判断你的栏位是否等于我要设置的栏位, 所以排除
            if (slot == lastSlot && !server) flag("slot=" + slot);

            lastSlot = slot;
            server = false;
        } else if (packet instanceof SPacketHeldItemSlot) server = true;
    }

}
