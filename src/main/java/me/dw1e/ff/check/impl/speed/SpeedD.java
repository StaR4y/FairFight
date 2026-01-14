package me.dw1e.ff.check.impl.speed;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.SPEED, type = "D", desc = "检查使用物品时不减移速", minVL = -3.0, maxVL = 15)
public final class SpeedD extends Check {

    private boolean didSlotChangeLastTick, flaggedLastTick; // 借鉴Grim, Grim模式绕过的修复详见 BadPacket P

    public SpeedD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (didSlotChangeLastTick) {
                didSlotChangeLastTick = false;
                flaggedLastTick = false;
            }

            int dropTicks = data.getTickSinceDroppedItem();
            boolean runEat = data.isEating() && dropTicks >= 3 && dropTicks <= 7; // 允许跑吃

            if (data.isUsingItem() && !data.getEmulationProcessor().isUsing() && !runEat) {

                if (flaggedLastTick) {
                    if (violations > 0.0) data.randomChangeSlot();

                    flag("dropTicks=" + (dropTicks > 20 ? "false" : dropTicks));
                }

                flaggedLastTick = true;
            } else {
                flaggedLastTick = false;
                decreaseVL(0.025);
            }
        }
    }

}
