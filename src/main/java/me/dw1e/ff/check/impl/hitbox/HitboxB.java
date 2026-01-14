package me.dw1e.ff.check.impl.hitbox;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Buffer;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.collision.BoundingBox;
import me.dw1e.ff.misc.collision.HitboxEntity;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketFlying;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@CheckInfo(category = Category.HITBOX, type = "B", desc = "检查碰撞箱", maxVL = 20)
public final class HitboxB extends Check {

    private final Buffer buffer = new Buffer(5);

    public HitboxB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketFlying && data.getTickSinceAttack() == 1) {
            Player target = data.getLastTarget();

            if (target == null) return;

            HitboxEntity hitbox = data.getEntityMap().get(target.getEntityId());

            if (hitbox == null) return;

            BoundingBox box = hitbox.getBox().clone().expand(0.1F, 0.1F, 0.1F);

            if (data.isOffsetMotion()) box.expand(0.03, 0.03, 0.03);

            Location from = data.getLastLocation(), to = data.getLocation();

            // 此检测同样在穿模时会误判
            if (from.toVector().setY(0.0).distance(box.toVector()) < 2.0
                    || data.getLastTarget().isInsideVehicle() || data.isInVehicle()) return;

            Vector direction = new Vector(-Math.sin(Math.toRadians(to.getYaw())),
                    0.0, Math.cos(Math.toRadians(to.getYaw())));

            double invDirX = 1.0 / direction.getX(), invDirZ = 1.0 / direction.getZ();

            boolean invX = invDirX < 0.0, invZ = invDirZ < 0.0;

            double
                    minX = ((invX ? box.getMaxX() : box.getMinX()) - from.getX()) * invDirX,
                    maxX = ((invX ? box.getMinX() : box.getMaxX()) - from.getX()) * invDirX,
                    minZ = ((invZ ? box.getMaxZ() : box.getMinZ()) - from.getZ()) * invDirZ,
                    maxZ = ((invZ ? box.getMinZ() : box.getMaxZ()) - from.getZ()) * invDirZ;

            double expand = Math.max(minX - maxZ, minZ - maxX);

            if (expand > 1E-7) {
                if (buffer.add() > 2) flag(String.format("expand=%.7f", expand));

            } else buffer.reduce(0.01);
        }
    }

}
