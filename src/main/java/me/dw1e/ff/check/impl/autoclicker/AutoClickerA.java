package me.dw1e.ff.check.impl.autoclicker;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketArmAnimation;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;

// Verus AutoClicker X
@CheckInfo(category = Category.AUTO_CLICKER, type = "A", desc = "检查CPS长时间大于20", minVL = -1.0, maxVL = 15)
public final class AutoClickerA extends Check {

    private int swings, done;
    private boolean place;

    public AutoClickerA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying) {
            if (done++ >= 19) {
                if (swings > 20) flag("swings=" + swings, (swings - 20) / 2.0);
                else decreaseVL(0.1);

                swings = done = 0;
            }

            place = false;
        } else if (packet instanceof CPacketArmAnimation && !data.isDigging() && !place) {
            ++swings;
        } else if (packet instanceof CPacketBlockPlace) {
            place = true;
        }
    }

}
