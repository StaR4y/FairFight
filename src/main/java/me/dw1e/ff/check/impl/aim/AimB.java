package me.dw1e.ff.check.impl.aim;

import com.google.common.collect.Lists;
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
import me.dw1e.ff.packet.wrapper.client.CPacketUseEntity;
import org.bukkit.Location;

import java.util.Deque;

// Nova Aim C
@CheckInfo(category = Category.AIM, type = "B", desc = "检查战斗中异常精准的瞄准", maxVL = 20)
public final class AimB extends Check {

    private final Buffer buffer = new Buffer(8);
    private final Deque<Float> offsets = Lists.newLinkedList(), deltas = Lists.newLinkedList();

    private HitboxEntity target;

    public AimB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketUseEntity) {
            CPacketUseEntity wrapper = (CPacketUseEntity) packet;

            target = data.getEntityMap().get(wrapper.getEntityId());
        } else if (packet instanceof CPacketFlying && ((CPacketFlying) packet).isRotation() && target != null) {
            Location from = data.getLastLocation(), to = data.getLocation();

            float yaw = MathUtil.wrapAngleTo180(to.getYaw());

            BoundingBox boundingBox = target.getBox();

            double distX = boundingBox.posX() - from.getX(), distZ = boundingBox.posZ() - from.getZ();

            float generatedYaw = MathUtil.wrapAngleTo180((float) (Math.toDegrees(Math.atan2(distZ, distX)) - 90.0F));

            offsets.add(Math.abs(yaw - generatedYaw));
            deltas.add(Math.abs(to.getYaw() - from.getYaw()));

            if (offsets.size() + deltas.size() == 60) {
                double averageDeltas = MathUtil.mean(deltas), averageOffsets = MathUtil.mean(offsets);
                double deltasDeviation = MathUtil.deviation(deltas), offsetsDeviation = MathUtil.deviation(offsets);

                // averageOffsets原数值3.0, 因为我自己打都能误判, 所以给到1.5
                if (averageDeltas > 2.5 && averageOffsets < 1.5 && deltasDeviation > 1.0 && offsetsDeviation < 3.0) {
                    if (buffer.add() > 3)
                        flag(String.format("avgDeltas=%.3f, avgOffsets=%.3f\ndeltasDev=%.3f, offsetsDev=%.3f",
                                averageDeltas, averageOffsets, deltasDeviation, offsetsDeviation));

                } else buffer.reduce(0.25);

                offsets.clear();
                deltas.clear();

                target = null;
            }
        }
    }

}
