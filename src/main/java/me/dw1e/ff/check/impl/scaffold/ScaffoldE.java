package me.dw1e.ff.check.impl.scaffold;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

// Medusa Scaffold D
@CheckInfo(category = Category.SCAFFOLD, type = "E", desc = "检查在搭路时的安全行走", minVL = -3.0)
public final class ScaffoldE extends Check {

    private double lastLastAccel, lastAccel;
    private int streaks, lastFlagTicks;

    public ScaffoldE(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isPosition()) {
            boolean exempt = !data.isBridging() || !data.isClientGround() || data.isSneaking();

            double accel = data.getDeltaXZ() - data.getLastDeltaXZ();

            float attribute = data.getAttributeSpeed();

            // 加速度达到属性速度才能说明玩家长按了移动键而不是点按了一下, 这能减少很多误判
            if (!exempt && accel < 0.0001 && lastAccel >= attribute && lastLastAccel < 0.0001) {
                lastFlagTicks = data.getTick();

                flag(String.format("accel=%.4f, last=%.4f/%s, lastLast=%.4f, streaks=%s",
                        accel, lastAccel, attribute, lastLastAccel, ++streaks), Math.min(streaks, 5));
            }

            // 如果1秒内没再触发, 再开始衰减
            if (data.getTick() - lastFlagTicks > 20) {
                streaks = 0;
                decreaseVL(0.05);
            }

            lastLastAccel = lastAccel;
            lastAccel = accel;
        }
    }

}
