package me.dw1e.ff.check.impl.scaffold;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;

import java.util.stream.Stream;

@CheckInfo(category = Category.SCAFFOLD, type = "B", desc = "检查无效的位置向量", maxVL = 1)
public final class ScaffoldB extends Check {

    public ScaffoldB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            int face = wrapper.getFace();

            if (face == 6) flag("invalid face");

            float x = wrapper.getFacingX(), y = wrapper.getFacingY(), z = wrapper.getFacingZ();

            if (face != 255 && Stream.of(x, y, z).anyMatch(v -> v < 0.0F || v > 1.0F))
                flag("face=" + face + ", x=" + x + ", y=" + y + ", z=" + z);
        }
    }

}
