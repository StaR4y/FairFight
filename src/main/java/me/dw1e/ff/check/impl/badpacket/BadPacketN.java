package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.server.SPacketHeldItemSlot;

@CheckInfo(category = Category.BAD_PACKET, type = "N", desc = "拒绝服务器设置的物品栏位", maxVL = 5)
public final class BadPacketN extends Check {

    private int serverSlot = -1;

    public BadPacketN(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof SPacketHeldItemSlot) {
            int slot = ((SPacketHeldItemSlot) packet).getSlot();

            data.transConfirm(() -> serverSlot = slot);

        } else if (packet instanceof CPacketFlying) {
            int clientSlot = data.getItemSlot();

            // 可能会在服务器大量发送切换物品栏位数据包时误判, 不过一般不会出现这种情况

            if (serverSlot != -1 && clientSlot != serverSlot)
                flag("server=" + serverSlot + ", client=" + clientSlot);

            serverSlot = -1;
        }
    }

}
