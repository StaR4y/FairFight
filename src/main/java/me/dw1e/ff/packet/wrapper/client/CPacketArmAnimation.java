package me.dw1e.ff.packet.wrapper.client;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class CPacketArmAnimation extends WrappedPacket {

    private final long timestamp;

    public CPacketArmAnimation(PacketContainer container) {
        timestamp = container.getLongs().read(0);
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
