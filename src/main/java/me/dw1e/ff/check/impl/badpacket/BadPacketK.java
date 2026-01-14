package me.dw1e.ff.check.impl.badpacket;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockDig;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "K", desc = "同时发送多个使用物品的数据包", maxVL = 1)
public final class BadPacketK extends Check {

    private int count;

    public BadPacketK(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockDig) {
            CPacketBlockDig wrapper = (CPacketBlockDig) packet;

            if (wrapper.getPlayerDigType() == EnumWrappers.PlayerDigType.RELEASE_USE_ITEM && ++count > 1)
                flag("count=" + count);

        } else if (packet instanceof CPacketFlying) count = 0;
    }

}
