package me.dw1e.ff.packet.wrapper.client;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class CPacketAbilities extends WrappedPacket {

    private final boolean invulnerable, flying, allowedFly, instantlyBuild;
    private final float flySpeed, walkSpeed;

    public CPacketAbilities(PacketContainer container) {
        StructureModifier<Boolean> booleans = container.getBooleans();
        StructureModifier<Float> floats = container.getFloat();

        invulnerable = booleans.read(0);
        flying = booleans.read(1);
        allowedFly = booleans.read(2);
        instantlyBuild = booleans.read(3);

        flySpeed = floats.read(0);
        walkSpeed = floats.read(1);
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isAllowedFly() {
        return allowedFly;
    }

    public boolean isInstantlyBuild() {
        return instantlyBuild;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }
}
