package me.dw1e.ff.check.impl.killaura;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockDig;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.KILL_AURA, type = "E", desc = "检查一些自动格挡的缺陷", maxVL = 3)
public final class KillAuraE extends Check {

    private boolean placed;

    public KillAuraE(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) placed = false;

        else if (packet instanceof CPacketBlockPlace && ((CPacketBlockPlace) packet).isUseItem()) placed = true;

        else if (packet instanceof CPacketBlockDig
                && ((CPacketBlockDig) packet).getPlayerDigType() == EnumWrappers.PlayerDigType.RELEASE_USE_ITEM
                && placed && data.getTickSinceTeleport() != 1 && !data.isInVehicle()) flag();
    }

}
