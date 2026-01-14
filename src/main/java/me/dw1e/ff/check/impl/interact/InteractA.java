package me.dw1e.ff.check.impl.interact;

import com.comphenix.protocol.wrappers.BlockPosition;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.collision.Cuboid;
import me.dw1e.ff.misc.util.BlockUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import org.bukkit.World;

@CheckInfo(category = Category.INTERACT, type = "A", desc = "检查在空气/液体上放置方块")
public final class InteractA extends Check {

    public InteractA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            if (!wrapper.isPlacedBlock()) return;

            World world = data.getPlayer().getWorld();

            BlockPosition placedBlockPos = wrapper.getBlockPosition();

            Cuboid placedBlockRegion = new Cuboid(world, placedBlockPos);

            boolean adjacentToGhostBlock = false;

            // 检查是否与任何幽灵方块相邻
            for (BlockPosition ghostBlockPos : data.getGhostBlocks()) {
                Cuboid ghostBlockRegion = new Cuboid(world, ghostBlockPos);

                if (BlockUtil.isAdjacent(placedBlockRegion, ghostBlockRegion)) {
                    adjacentToGhostBlock = true;
                    break; // 与任何一个幽灵方块接壤就行
                }
            }

            if (!adjacentToGhostBlock) {
                // 未贴近幽灵方块的直接放置
                if (isAirOrLiquid(placedBlockRegion, 0)) flag();

            } else {
                // 贴近幽灵方块放置, 但周围五格内无正常方块, 也许在使用blink
                if (isAirOrLiquid(placedBlockRegion, 5)) flag("max dist");
            }
        }
    }

    private boolean isAirOrLiquid(Cuboid region, int expand) {
        return region.expandXZ(expand).expandY(expand, expand) // 注意检查的顺序, 到最后再扩展距离
                .getBlocks().stream().allMatch(block -> block.isEmpty() || block.isLiquid());
    }

}
