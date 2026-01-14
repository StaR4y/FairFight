package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class SPacketRemoveEntityEffect extends WrappedPacket {

    private final int entityId, effectId;

    public SPacketRemoveEntityEffect(PacketContainer container) {
        entityId = container.getIntegers().read(0);
        effectId = container.getIntegers().read(1);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getEffectId() {
        return effectId;
    }
}
