package me.dw1e.ff.check.impl.velocity;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.VELOCITY, type = "D", desc = "检查水平方向击退的修改(模拟)")
public final class VelocityD extends Check {

    private final Buffer buffer = new Buffer(5);

    public VelocityD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            int ticks = data.getTickSinceVelocity();

            if (ticks > 8 || data.isFlying() || data.isInVehicle() || data.isInWeb()
                    || data.isOnSlime() || data.isClimbing() || data.isPushedByPiston()
                    || data.getTickSinceInLiquid() < 2
                    || data.getTickSinceNearWall() < 3
            ) return;

            double offset = data.getEmulationProcessor().getMinDistance();
            double limit = data.isOffsetMotion() ? 0.03 : 1E-7;

            if (offset > limit) {
                if (buffer.add() > 3) flag(String.format("ticks=%s, offset=%.7f", ticks, offset));

            } else buffer.reduce(0.05);
        }
    }

}
