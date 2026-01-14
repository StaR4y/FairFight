package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public class SPacketEntity extends WrappedPacket {

    private final int entityId;
    private final byte x, y, z, yaw, pitch;
    private final boolean onGround, rotation;

    public SPacketEntity(PacketContainer container) {
        StructureModifier<Byte> bytes = container.getBytes();
        StructureModifier<Boolean> booleans = container.getBooleans();

        entityId = container.getIntegers().read(0);

        x = bytes.read(0);
        y = bytes.read(1);
        z = bytes.read(2);
        yaw = bytes.read(3);
        pitch = bytes.read(4);

        onGround = booleans.read(0);
        rotation = booleans.read(1);
    }

    public int getEntityId() {
        return entityId;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public byte getZ() {
        return z;
    }

    public byte getYaw() {
        return yaw;
    }

    public byte getPitch() {
        return pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isRotation() {
        return rotation;
    }
}
