package me.dw1e.ff.check.impl.autoclicker;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.math.MathUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketArmAnimation;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// Verus AutoClicker D
@CheckInfo(category = Category.AUTO_CLICKER, type = "C", desc = "检查点击的平均值", minVL = -1.0, maxVL = 10)
public final class AutoClickerC extends Check {

    private final Queue<Integer> averageTicks = new ConcurrentLinkedQueue<>();

    private boolean swung;
    private int ticks, lastTicks, done;

    public AutoClickerC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (swung && !data.isDigging()) {
                if (ticks < 7) {
                    averageTicks.add(ticks);

                    if (averageTicks.size() > 50) averageTicks.poll();
                }

                if (averageTicks.size() > 40) {
                    double average = MathUtil.mean(averageTicks);

                    if (average < 2.5) {
                        if (ticks > 3 && ticks < 20 && lastTicks < 20) {
                            decreaseVL(0.25);
                            done = 0;

                        } else if (done++ > 600.0 / (average * 1.5)) {
                            flag(String.format("average=%.7f", average));
                            done = 0;
                        }
                    } else done = 0;
                }

                lastTicks = ticks;
                ticks = 0;
            }

            swung = false;

            ++ticks;
        } else if (packet instanceof CPacketArmAnimation) swung = true;
    }

}
