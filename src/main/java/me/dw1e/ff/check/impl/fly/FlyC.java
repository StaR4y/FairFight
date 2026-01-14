package me.dw1e.ff.check.impl.fly;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.FLY, type = "C", desc = "检查相同Y轴运动", maxVL = 20)
public final class FlyC extends Check {

    private int streaks;

    public FlyC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isPosition()) {
            if (data.getTick() < 20 || data.isFlying() || data.isInUnloadedChunk() || data.isInVehicle()
                    || data.getTickSincePushedByPiston() < 3
                    || data.getTickSinceTeleport() < 3
                    || data.getTickSinceClimbing() < 2
                    || data.getTickSinceInLiquid() < 2
            ) return;

            double deltaY = data.getDeltaY(), lastDeltaY = data.getLastDeltaY();

            if (deltaY != 0.0) { // 只在Y轴运动时检查, 不然 streaks(连击) 会断

                // 如果Y轴运动和上一次的一样, 这一般是不可能的
                if (deltaY == lastDeltaY) {

                    // 但是比如说一直上楼梯, 爬梯子什么的就会误判; 这里的受击退豁免是为了防止打ComboFly模式遇到20+cps的对手的预防性检查
                    if (data.getTickSinceNearStep() < 3 || data.getTickSinceVelocity() == 1) return;

                    flag(String.format("+ deltaY=%.7f, streaks=%s", deltaY, ++streaks), Math.min(streaks, 5));
                }

                // 如果Y轴运动等于上一次的下落运动, 一般代表使用 YPort Speed
                else if (deltaY == -lastDeltaY) {
                    if (deltaY <= -3.92F || data.isUnderBlock()
                            || data.getTickSinceUnderBlock() < 2
                            || data.getTickSinceOnSlime() < 2
                            || data.getTickSinceInWeb() < 2
                    ) return;

                    flag(String.format("- deltaY=%.7f, streaks=%s", deltaY, ++streaks), Math.min(streaks, 5));

                }

                // 如果没什么问题就把连击断了
                else streaks = 0;
            }

        }
    }

}
