package me.dw1e.ff.packet.wrapper.client;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class CPacketSteerVehicle extends WrappedPacket {

    private final float sideValue, forwardValue;
    private final boolean jump, dismount;

    public CPacketSteerVehicle(PacketContainer container) {
        sideValue = container.getFloat().read(0);
        forwardValue = container.getFloat().read(1);

        jump = container.getBooleans().read(0);
        dismount = container.getBooleans().read(1);
    }

    public float getSideValue() {
        return sideValue;
    }

    public float getForwardValue() {
        return forwardValue;
    }

    public boolean isJump() {
        return jump;
    }

    public boolean isDismount() {
        return dismount;
    }
}
