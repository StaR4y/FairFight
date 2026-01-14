package me.dw1e.ff.check.impl.killaura;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketEntityAction;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import me.dw1e.ff.packet.wrapper.client.CPacketUseEntity;

@CheckInfo(category = Category.KILL_AURA, type = "G", desc = "检查在攻击的同时发送状态数据包", maxVL = 3)
public final class KillAuraG extends Check {

    private boolean sent;

    public KillAuraG(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketUseEntity && sent) flag();
        else if (packet instanceof CPacketEntityAction) sent = true;
        else if (packet instanceof CPacketFlying) sent = false;
    }

}
