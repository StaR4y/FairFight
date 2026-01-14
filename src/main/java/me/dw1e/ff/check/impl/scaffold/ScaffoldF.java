package me.dw1e.ff.check.impl.scaffold;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.util.BlockUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketArmAnimation;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(category = Category.SCAFFOLD, type = "F", desc = "检查在放置方块时不摇摆手臂", maxVL = 3)
public final class ScaffoldF extends Check {

    private boolean swung, placed;

    public ScaffoldF(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            if (!wrapper.isPlacedBlock()) return;

            Block block = BlockUtil.getBlockAsync(wrapper.getBlockPosition().toLocation(data.getPlayer().getWorld()));

            // 手持方块右键床且睡不了觉时的误判修复
            if (block != null && !block.getType().equals(Material.BED_BLOCK)) placed = true;

        } else if (packet instanceof CPacketArmAnimation) swung = true;
        else if (packet instanceof CPacketFlying) {

            // 冒险模式会误判
            boolean exempt = data.getPlayer().getGameMode().equals(GameMode.ADVENTURE);

            if (placed && !swung && !exempt) flag();

            placed = swung = false;
        }
    }

}
