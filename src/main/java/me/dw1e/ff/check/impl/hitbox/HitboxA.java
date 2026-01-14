package me.dw1e.ff.check.impl.hitbox;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.collision.BoundingBox;
import me.dw1e.ff.misc.collision.HitboxEntity;
import me.dw1e.ff.misc.math.MathUtil;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CheckInfo(category = Category.HITBOX, type = "A", desc = "检查攻击距离", maxVL = 20)
public final class HitboxA extends Check {

    private final Buffer buffer = new Buffer(5);

    public HitboxA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        // 客户端的位置会在下一次Flying包中更新, 为了精准的距离检查, 写在此处
        if (packet instanceof CPacketFlying && data.getTickSinceAttack() == 1) {
            Player target = data.getLastTarget();

            if (target == null) return;

            // 这就是本反作弊的延迟补偿, 通过玩家响应了trans包后再更新他视角中其它玩家的位置
            HitboxEntity hitbox = data.getEntityMap().get(target.getEntityId());

            if (hitbox == null) return;

            BoundingBox box = hitbox.getBox().clone().expand(0.1F, 0.1F, 0.1F);

            if (data.isOffsetMotion()) box.expand(0.03, 0.03, 0.03);

            Location from = data.getLastLocation(), to = data.getLocation();

            if (from.toVector().setY(0.0).distance(box.toVector()) < 2.0 // 修复玩家穿模时攻击会误判的问题, 顺便限制贴脸对刀降VL
                    || data.getLastTarget().isInsideVehicle() || data.isInVehicle()) return; // 乘坐载具时不会更新位置, 跳过

            double distX = Math.min(Math.abs(from.getX() - box.getMinX()), Math.abs(from.getX() - box.getMaxX()));
            double distZ = Math.min(Math.abs(from.getZ() - box.getMinZ()), Math.abs(from.getZ() - box.getMaxZ()));

            double distance = MathUtil.hypot(distX, distZ);

            if (Math.abs(to.getPitch()) != 90.0F) distance /= Math.cos(Math.toRadians(to.getPitch()));

            double excess = distance - (data.isInstantlyBuild() ? 4.0 : 3.0);

            if (excess > 0.5) { // 拉的太大, 直接flag, 动态VL
                buffer.add();
                flag(String.format("blatant, excess=%.7f", excess), Math.round(excess * 3.0));

            } else if (excess > 0.025) { // 可能会在双方原地不动时卡极限距离打出3.022左右的误判
                // 通过一个快速衰减的缓冲来减少误判
                if (buffer.add() > 1) flag(String.format("excess=%.7f", excess));

            } else buffer.reduce(0.025);
        }
    }

}