package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketSteerVehicle;

import java.util.stream.Stream;

@CheckInfo(category = Category.BAD_PACKET, type = "I", desc = "无效的载具数据包", maxVL = 1)
public final class BadPacketI extends Check {

    public BadPacketI(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketSteerVehicle) {
            CPacketSteerVehicle wrapper = (CPacketSteerVehicle) packet;

            float side = Math.abs(wrapper.getSideValue()), forward = Math.abs(wrapper.getForwardValue());

            // 不操作时=0.0; 移动时=0.98; 刚下车且按住方向键=0.29400003(高版本不会有这个数, 这是老版本特有的)

            if (Stream.of(side, forward).anyMatch(v -> v != 0.0F && v != 0.29400003F && v != 0.98F))
                flag("side=" + side + ", forward=" + forward);
        }
    }

}
