package me.dw1e.ff.check.impl.aim;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.AIM, type = "C", desc = "检查圆滑瞄准", maxVL = 10)
public final class AimC extends Check {

    private final Buffer buffer = new Buffer(10);

    public AimC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isRotation()) {
            float deltaYaw = data.getDeltaYaw(), deltaPitch = data.getDeltaPitch();

            if ((deltaPitch % 1 == 0 || deltaYaw % 1 == 0) && deltaPitch != 0 && deltaYaw != 0) {
                if (buffer.add() > 4) flag("deltaPitch=" + deltaPitch + ", deltaYaw=" + deltaYaw);

            } else buffer.reduce(0.25);
        }
    }

}
