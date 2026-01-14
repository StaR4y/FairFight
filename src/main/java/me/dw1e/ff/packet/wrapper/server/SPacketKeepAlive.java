package me.dw1e.ff.packet.wrapper.server;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class SPacketKeepAlive extends WrappedPacket {

    private final int id;

    public SPacketKeepAlive(PacketContainer container) {
        id = container.getIntegers().read(0);
    }

    public int getId() {
        return id;
    }
}
