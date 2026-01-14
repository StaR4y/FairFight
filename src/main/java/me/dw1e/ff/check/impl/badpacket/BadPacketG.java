package me.dw1e.ff.check.impl.badpacket;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketArmAnimation;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockDig;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "G", desc = "在破坏方块时不摇摆手臂", maxVL = 3)
public final class BadPacketG extends Check {

    private boolean dug, swung;

    public BadPacketG(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketArmAnimation) swung = true;

        else if (packet instanceof CPacketBlockDig && ((CPacketBlockDig) packet).getPlayerDigType()
                == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) dug = true;

        else if (packet instanceof CPacketFlying) {
            if (dug && !swung) flag();

            dug = swung = false;
        }
    }

}
