package me.dw1e.ff.check.impl.timer;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.server.SPacketPosition;

@CheckInfo(category = Category.TIMER, type = "A", desc = "使用'余额平衡'检查游戏时间加速", maxVL = 20)
public final class TimerA extends Check {

    private Long lastTimestamp;
    private long balance = -50L;

    public TimerA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && data.getTick() > 40) {
            long timestamp = packet.getTimestamp();

            if (lastTimestamp != null) {
                balance += 50L;
                balance -= timestamp - lastTimestamp;

                if (balance > 50L) {
                    flag("balance=" + balance);

                    balance = -50L;
                }
            }

            lastTimestamp = timestamp;
        } else if (packet instanceof SPacketPosition) balance -= 50L;
    }

}
