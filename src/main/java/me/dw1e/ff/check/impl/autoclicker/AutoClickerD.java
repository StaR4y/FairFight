package me.dw1e.ff.check.impl.autoclicker;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

@CheckInfo(category = Category.AUTO_CLICKER, type = "D", desc = "检查右键CPS长时间大于20", minVL = -1.0, maxVL = 15)
public final class AutoClickerD extends Check {

    private int done, places;

    public AutoClickerD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (done++ >= 19) {
                if (places > 20) flag("places=" + places, (places - 20) / 2.0);
                else decreaseVL(0.1);

                places = done = 0;
            }

        } else if (packet instanceof CPacketBlockPlace) {
            ++places;
        }
    }

}
