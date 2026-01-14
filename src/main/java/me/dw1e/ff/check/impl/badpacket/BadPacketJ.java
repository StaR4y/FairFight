package me.dw1e.ff.check.impl.badpacket;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketEntityAction;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "J", desc = "同时发送多个相同的状态数据包", maxVL = 1)
public final class BadPacketJ extends Check {

    private EnumWrappers.PlayerAction lastAction;
    private int count;

    public BadPacketJ(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketEntityAction) {
            CPacketEntityAction wrapper = (CPacketEntityAction) packet;

            EnumWrappers.PlayerAction action = wrapper.getAction();

            boolean exempt = action == EnumWrappers.PlayerAction.STOP_SLEEPING; // 按ESC结束睡觉会触发两次

            if (action == lastAction && !exempt && ++count > 1) flag("action=" + action + ", count=" + count);

            lastAction = wrapper.getAction();
        } else if (packet instanceof CPacketFlying) count = 0;
    }

}
