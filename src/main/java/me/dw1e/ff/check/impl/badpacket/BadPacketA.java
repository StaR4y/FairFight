package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "A", desc = "垂直方向视角超过90度", maxVL = 1)
public final class BadPacketA extends Check {

    public BadPacketA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            CPacketFlying wrapper = (CPacketFlying) packet;

            if (!wrapper.isRotation()) return;

            float pitch = wrapper.getPitch();

            if (Math.abs(pitch) > 90.0F) flag("pitch=" + wrapper.getPitch());
        }
    }

}
