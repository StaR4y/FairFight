package me.dw1e.ff.packet.wrapper.client;

import com.comphenix.protocol.events.PacketContainer;
import me.dw1e.ff.packet.wrapper.WrappedPacket;

public final class CPacketCloseWindow extends WrappedPacket {

    private final int windowId;

    public CPacketCloseWindow(PacketContainer container) {
        windowId = container.getIntegers().read(0);
    }

    public int getWindowId() {
        return windowId;
    }
}
