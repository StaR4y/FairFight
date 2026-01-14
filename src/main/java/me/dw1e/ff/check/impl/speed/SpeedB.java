package me.dw1e.ff.check.impl.speed;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.SPEED, type = "B", desc = "检查在空中切换移动方向", maxVL = 15)
public final class SpeedB extends Check {

    private final Buffer buffer = new Buffer(5);

    public SpeedB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isPosition()) {
            if (data.getTickSinceClientGround() < 3 || data.isFlying() || data.isInVehicle() || data.isInLiquid()
                    || data.getTickSincePushedByPiston() < 2
                    || data.getTickSinceNearWall() < 2
            ) return;

            double deltaX = data.getDeltaX(), lastDeltaX = data.getLastDeltaX();
            double deltaZ = data.getDeltaZ(), lastDeltaZ = data.getLastDeltaZ();

            if (data.getTickSinceVelocity() == 1) {
                lastDeltaX = data.getVelocityX();
                lastDeltaZ = data.getVelocityZ();
            }

            if (data.getTickSinceAttack() == 1) {
                lastDeltaX *= 0.6F;
                lastDeltaZ *= 0.6F;
            }

            boolean xSwitched = (deltaX > 0.0 && lastDeltaX < 0.0) || (deltaX < 0.0 && lastDeltaX > 0.0);
            boolean zSwitched = (deltaZ > 0.0 && lastDeltaZ < 0.0) || (deltaZ < 0.0 && lastDeltaZ > 0.0);

            double diffX = Math.abs(Math.abs(deltaX) - Math.abs(lastDeltaX));
            double diffZ = Math.abs(Math.abs(deltaZ) - Math.abs(lastDeltaZ));

            if (xSwitched && diffX > 0.05) {
                if (buffer.add() > 1.25) flag(String.format("x, diff=%.7f", diffX));

            } else if (zSwitched && diffZ > 0.05) {
                if (buffer.add() > 1.25) flag(String.format("z, diff=%.7f", diffZ));

            } else buffer.reduce(0.05);
        }
    }

}
