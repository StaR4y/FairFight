package me.dw1e.ff.check.impl.velocity;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.VELOCITY, type = "C", desc = "检查跳跃重置的成功率(≥80%)", maxVL = 8)
public final class VelocityC extends Check {

    private int success, failure;

    public VelocityC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (data.getTickSinceVelocity() != 1 || !data.isSprinting() // 疾跑 + 跳跃 才会 +0.2 移速用于抵消部分水平击退
                    || (data.getAttributeJump() - data.getVelocityY() < 1E-5) // 如果跳跃高度和击退高度一样, 无法区分, 跳过
                    || !(data.isLastClientGround() && !data.isClientGround()) // 上次在地面 + 这次不在地面 = 按下空格起跳
            ) return;

            if (data.isJumped()) ++success;
            else ++failure;

            if (success + failure >= 10) {
                double ratio = (double) success / (success + failure);

                // 我觉得一般人按不出80%的跳跃重置成功率, 而且低了这个检查也没意义
                if (ratio >= 0.8) flag("ratio=" + ratio);

                success = failure = 0;
            }
        }
    }

}
