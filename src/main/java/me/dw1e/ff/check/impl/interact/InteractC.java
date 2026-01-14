package me.dw1e.ff.check.impl.interact;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.util.BlockUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockDig;
import org.bukkit.Location;
import org.bukkit.block.Block;

@CheckInfo(category = Category.INTERACT, type = "C", desc = "检查挖掘液体", maxVL = 1)
public final class InteractC extends Check {

    public InteractC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockDig) {
            CPacketBlockDig wrapper = (CPacketBlockDig) packet;

            Block block = BlockUtil.getBlockAsync(new Location(data.getPlayer().getWorld(),
                    wrapper.getBlockPosition().getX(),
                    wrapper.getBlockPosition().getY(),
                    wrapper.getBlockPosition().getZ()
            ));

            // 空气是可能被交互到的(比如一直挖一个在保护区内的草), 但是液体绝对不会

            if (block != null && block.isLiquid()) flag();
        }
    }

}
