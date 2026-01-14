package me.dw1e.ff.check.impl.scaffold;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketBlockPlace;
import me.dw1e.ff.packet.wrapper.client.CPacketEntityAction;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

// Vulcan Scaffold K
@CheckInfo(category = Category.SCAFFOLD, type = "D", desc = "检查搭路速度过快", maxVL = 20)
public final class ScaffoldD extends Check {

    private final Map<Integer, Long> placed = new HashMap<>();
    private int lastSneak, blocksPlaced;

    public ScaffoldD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketBlockPlace) {
            CPacketBlockPlace wrapper = (CPacketBlockPlace) packet;

            if (!data.isBridging() || !wrapper.isPlacedBlock() || data.getTick() - lastSneak <= 20) return;

            // 方块中心点
            Vector blockLoc = wrapper.getBlockPosition().toVector().add(new Vector(0.5, 1.0, 0.5));

            // 玩家距离自己放置的方块的距离
            double distance = blockLoc.distance(data.getLocation().toVector());

            // 只计算在脚下放置的方块, 范围是一个圆形, 最边上差不多就是1.13左右
            if (distance < 1.15) placed.put(++blocksPlaced, packet.getTimestamp());

            if (!placed.isEmpty() && packet.getTimestamp() - placed.get(1) >= 1000L) {
                int amount = placed.size(), limit = 5 + Math.round(data.getAttributeSpeed()) * 10;

                if (amount > limit) flag("amount=" + amount + ", limit=" + limit);

                placed.clear();
                blocksPlaced = 0;
            }
        } else if (packet instanceof CPacketEntityAction) {
            CPacketEntityAction wrapper = (CPacketEntityAction) packet;

            // Vulcan搭路速度限制绕过 检测见 BadPacket O
            if (wrapper.getAction() == EnumWrappers.PlayerAction.START_SNEAKING
                    || wrapper.getAction() == EnumWrappers.PlayerAction.STOP_SNEAKING) lastSneak = data.getTick();
        }
    }

}
