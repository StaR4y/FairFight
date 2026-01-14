package me.dw1e.ff.check.impl.scaffold;

import com.comphenix.protocol.wrappers.BlockPosition;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;

@CheckInfo(category = Category.SCAFFOLD, type = "A", desc = "检查异常的向下搭路", maxVL = 1)
public final class ScaffoldA extends Check {

    public ScaffoldA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            BlockPosition blockPos = wrapper.getBlockPosition();

            if (blockPos == null) return;

            int face = wrapper.getFace();

            double locY = data.getLocation().getY(), blockY = blockPos.getY();

            if (face == 0 && locY > blockY) flag(String.format("locY=%.7f, blockY=%s", locY, blockY));
        }
    }

}
