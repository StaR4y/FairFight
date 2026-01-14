package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.client.CPacketHeldItemSlot;

@CheckInfo(category = Category.BAD_PACKET, type = "P", desc = "同时发送多个切换物品栏位的数据包", maxVL = 3)
public final class BadPacketP extends Check {

    // 用于检查水影新版中 NoSlow 的 Grim 2364 1 8 模式
    // 原理就是利用 反作弊为了防止客户端 在使用物品时切换物品栏位 导致误判的排除

    private int counts;

    public BadPacketP(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketHeldItemSlot) ++counts;
        else if (packet instanceof CPacketFlying) {
            if (counts > 1) flag("counts=" + counts);

            counts = 0;
        }
    }

}
