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

// Verus AutoClicker A
@CheckInfo(category = Category.AUTO_CLICKER, type = "B", desc = "检查点击的偏差", minVL = -5.0, maxVL = 10)
public final class AutoClickerB extends Check {

    private final Queue<Integer> intervals = new ConcurrentLinkedQueue<>();

    private boolean swung;
    private int ticks;

    public AutoClickerB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (swung && !data.isDigging() && !data.isPlacing()) {
                if (ticks < 8) {
                    intervals.add(ticks);

                    if (intervals.size() >= 40) {
                        double deviation = MathUtil.deviation(intervals);

                        violations += (0.325 - deviation) * 2.0 + 0.675;
                        if (violations < -5.0) violations = -5.0;

                        if (deviation < 0.325) flag(String.format("deviation=%.7f", deviation), 0.0);

                        intervals.clear();
                    }
                }

                ticks = 0;
            }

            swung = false;

            ++ticks;
        } else if (packet instanceof CPacketArmAnimation) swung = true;
    }

}
