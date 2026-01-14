package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketUseEntity;

@CheckInfo(category = Category.BAD_PACKET, type = "C", desc = "自己与自己交互", maxVL = 1)
public final class BadPacketC extends Check {

    public BadPacketC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketUseEntity && ((CPacketUseEntity) packet)
                .getEntityId() == data.getPlayer().getEntityId()) flag();
    }

}
