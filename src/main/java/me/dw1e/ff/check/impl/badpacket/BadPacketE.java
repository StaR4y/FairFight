package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "E", desc = "超过20ticks未更新位置", maxVL = 3)
public final class BadPacketE extends Check {

    private int streaks;

    public BadPacketE(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            CPacketFlying wrapper = (CPacketFlying) packet;

            if (wrapper.isPosition() || data.isInVehicle()) { // 乘坐载具时不会更新位置
                streaks = 0;
                return;
            }

            // 一些情况下会有一点点的误判, 所以如果稍微晚了1到2ticks的话只增加少量的VL
            if (++streaks > 20) flag("streaks=" + streaks, streaks > 22 ? 1.0 : 0.1);
        }
    }

}
