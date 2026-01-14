package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketEntityAction;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.BAD_PACKET, type = "O", desc = "同时发送'潜行/疾跑'的'开始'和'结束'状态数据包", maxVL = 5)
public final class BadPacketO extends Check {

    // 用于检查Vulcan搭路速度限制的bypass

    private boolean startSneak, stopSneak, startSprint, stopSprint;

    public BadPacketO(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketEntityAction) {
            switch (((CPacketEntityAction) packet).getAction()) {
                case START_SNEAKING:
                    startSneak = true;
                    break;
                case STOP_SNEAKING:
                    stopSneak = true;
                    break;
                case START_SPRINTING:
                    startSprint = true;
                    break;
                case STOP_SPRINTING:
                    stopSprint = true;
                    break;
            }
        } else if (packet instanceof CPacketFlying) {
            if (startSneak && stopSneak) flag("sneak");
            if (startSprint && stopSprint && !data.isNearWall()) flag("sprint");

            startSneak = stopSneak = startSprint = stopSprint = false;
        }
    }

}
