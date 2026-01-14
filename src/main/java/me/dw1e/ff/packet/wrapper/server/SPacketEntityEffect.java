package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class SPacketEntityEffect extends WrappedPacket {

    private final int entityId, duration;
    private final byte effectId, amplifier, showParticles;

    public SPacketEntityEffect(PacketContainer container) {
        entityId = container.getIntegers().read(0);
        duration = container.getIntegers().read(1);

        effectId = container.getBytes().read(0);
        amplifier = container.getBytes().read(1);
        showParticles = container.getBytes().read(2);
    }

    public int getEntityId() {
        return entityId;
    }

    public byte getEffectId() {
        return effectId;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isShowParticles() {
        return showParticles == 1;
    }
}
