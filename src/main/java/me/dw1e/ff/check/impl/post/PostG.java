package me.dw1e.ff.check.impl.post;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.client.CPacketUseEntity;

@CheckInfo(category = Category.POST, type = "G", desc = "UseEntity", maxVL = 10)
public final class PostG extends Check {

    private final Buffer buffer = new Buffer(5);

    private long lastFlying, lastPacket;
    private boolean sent;

    public PostG(PlayerData data) {
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
        } else if (packet instanceof CPacketUseEntity) {
            long now = packet.getTimestamp(), delay = now - lastFlying;

            if (delay < 10L) {
                lastPacket = now;
                sent = true;

            } else buffer.reduce(0.1);
        }
    }

}
