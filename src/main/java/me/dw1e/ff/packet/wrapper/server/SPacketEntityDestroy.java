package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class SPacketEntityDestroy extends WrappedPacket {

    private final int[] entities;

    public SPacketEntityDestroy(PacketContainer container) {
        entities = container.getIntegerArrays().read(0);
    }

    public int[] getEntities() {
        return entities;
    }
}
