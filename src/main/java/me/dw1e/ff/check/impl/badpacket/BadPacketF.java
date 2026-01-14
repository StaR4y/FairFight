package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockDig;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

// Verus BadPackets X
@CheckInfo(category = Category.BAD_PACKET, type = "F", desc = "快速挖掘", maxVL = 10)
public final class BadPacketF extends Check {

    // 只是一个缺陷检查, 但是水影新版依然中招

    private int stage;

    public BadPacketF(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (stage == 1) stage = 2;
            else stage = 0;

        } else if (packet instanceof CPacketBlockDig) {
            CPacketBlockDig wrapper = (CPacketBlockDig) packet;

            switch (wrapper.getPlayerDigType()) {
                case STOP_DESTROY_BLOCK:
                    stage = 1;
                    break;

                case START_DESTROY_BLOCK:
                    if (stage == 2) flag();

                    stage = 0;
                    break;
            }

        }
    }

}
