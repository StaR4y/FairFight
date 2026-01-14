package me.dw1e.ff.check.impl.scaffold;

import com.comphenix.protocol.wrappers.BlockPosition;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.util.PlayerUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

@CheckInfo(category = Category.SCAFFOLD, type = "C", desc = "检查放置的方块朝向与视角朝向一致", maxVL = 10)
public final class ScaffoldC extends Check {

    public ScaffoldC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            BlockPosition blockLoc = wrapper.getBlockPosition();

            if (blockLoc == null) return;

            Location playerLoc = data.getLocation();

            boolean below = blockLoc.getY() < playerLoc.getY();

            BlockFace direction = PlayerUtil.getDirection(playerLoc.getYaw());

            // 通常情况为玩家放置的方块的面等于玩家朝向面的反方向(斜搭除外)
            // 如果方块面与放置面相同则说明大概率在使用不转头模式的自动搭路
            if (below && wrapper.getBlockFace() == direction) flag("direction=" + direction);
        }
    }

}
