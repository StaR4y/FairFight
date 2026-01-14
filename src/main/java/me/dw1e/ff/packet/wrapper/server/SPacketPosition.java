package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class SPacketPosition extends WrappedPacket {

    private final double x, y, z;
    private final float yaw, pitch;

    public SPacketPosition(PacketContainer container) {
        StructureModifier<Double> doubles = container.getDoubles();
        StructureModifier<Float> floats = container.getFloat();

        x = doubles.read(0);
        y = doubles.read(1);
        z = doubles.read(2);
        yaw = floats.read(0);
        pitch = floats.read(1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
