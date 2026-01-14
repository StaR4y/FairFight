package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketKeepAlive;

@CheckInfo(category = Category.BAD_PACKET, type = "D", desc = "发送相同的心跳包id", maxVL = 1)
public final class BadPacketD extends Check {

    // 一般会在水影老版本的PingSpoof时出现

    private int lastId = -1;

    public BadPacketD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketKeepAlive && data.getTick() > 40) {
            int id = ((CPacketKeepAlive) packet).getId();

            if (id == lastId) flag("id=" + id);

            lastId = id;
        }
    }

}
