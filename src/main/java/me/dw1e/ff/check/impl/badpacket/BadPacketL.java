package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.client.CPacketHeldItemSlot;

@CheckInfo(category = Category.BAD_PACKET, type = "L", desc = "在放置的同时切换物品栏位", maxVL = 3)
public final class BadPacketL extends Check {

    private boolean sent;

    public BadPacketL(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketHeldItemSlot && sent) flag();
        else if (packet instanceof CPacketBlockPlace) sent = true;
        else if (packet instanceof CPacketFlying) sent = false;
    }

}
