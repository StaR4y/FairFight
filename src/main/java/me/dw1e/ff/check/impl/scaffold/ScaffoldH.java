package me.dw1e.ff.check.impl.scaffold;

import com.comphenix.protocol.wrappers.BlockPosition;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.SCAFFOLD, type = "H", desc = "检查搭高速度过快", maxVL = 20)
public final class ScaffoldH extends Check {

    private int ticks, lastX, lastY, lastZ, buffer;

    public ScaffoldH(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            if (!wrapper.isPlacedBlock() || data.getDeltaY() <= 0.0 || data.isFlying()) return;

            BlockPosition blockPos = wrapper.getBlockPosition();

            int x = blockPos.getX(), y = blockPos.getY(), z = blockPos.getZ();

            if (lastX == x && y > lastY && lastZ == z) {
                // 跳的越高可在脚下放置的方块就越多, 所以限制时间也相应的减少一些
                int limit = 7 - data.getJumpEffect();

                if (ticks < limit) {
                    // 原地搭高不会误判, 但是在全速移动时可能会有一些
                    if (++buffer > 2) flag("ticks=" + ticks + "/" + limit);

                } else buffer = 0;

                ticks = 0;
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        } else if (packet instanceof CPacketFlying) ++ticks;
    }

}
