package me.dw1e.ff.check.impl.post;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketArmAnimation;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.POST, type = "A", desc = "ArmAnimation", maxVL = 10)
public final class PostA extends Check {

    // 这些Post检查都一样, 只是检查的包不同, 均来自Frequency

    private final Buffer buffer = new Buffer(5);

    private long lastFlying, lastPacket;
    private boolean sent;

    public PostA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            long now = packet.getTimestamp(), delay = now - lastPacket;

            if (sent) {
                if (delay > 40L && delay < 100L) {
                    if (buffer.add() > 2) flag("delay=" + delay);
                } else buffer.reduce(0.1);

                sent = false;
            }

            lastFlying = now;
        } else if (packet instanceof CPacketArmAnimation) {
            long now = packet.getTimestamp(), delay = now - lastFlying;

            if (delay < 10L) {
                lastPacket = now;
                sent = true;

            } else buffer.reduce(0.1);
        }
    }

}
