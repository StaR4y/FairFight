package me.dw1e.ff.check.impl.badpacket;

import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.check.api.annotations.CheckInfo;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import me.dw1e.ff.packet.wrapper.client.CPacketTransaction;
import me.dw1e.ff.packet.wrapper.server.SPacketTransaction;

import java.util.ArrayDeque;
import java.util.Deque;

@CheckInfo(category = Category.BAD_PACKET, type = "Q", desc = "异常的确认包回复情况", maxVL = 3)
public final class BadPacketQ extends Check {

    // 有可能会因为其它插件发送的 Transaction 包而误判, 这个检测只是打样, 你可以选择不开

    // 等待确认的 Transaction
    private final Deque<Short> pendingTransactions = new ArrayDeque<>();

    public BadPacketQ(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(WrappedPacket packet) {
        if (packet instanceof CPacketTransaction) {
            CPacketTransaction wrapper = (CPacketTransaction) packet;

            short action = wrapper.getActionId();

            // 在没有任何待确认的 Transaction 时却回复了一个
            if (pendingTransactions.isEmpty()) {
                flag("unexpected");
                return;
            }

            short expected = pendingTransactions.peekFirst();

            //data.getPlayer().sendMessage("expected=" + expected + ", received=" + action);

            // 顺序不匹配 (乱序, 跳号)
            if (expected != action) {
                flag("order, expected=" + expected + ", received=" + action);

                pendingTransactions.clear(); // 防止连锁误判
                return;
            }

            pendingTransactions.pollFirst(); // 正常确认

        } else if (packet instanceof SPacketTransaction) {
            SPacketTransaction wrapper = (SPacketTransaction) packet;

            // windowId = 0 代表玩家背包, actionId > 0 代表不是本反作弊发送的确认包
            if (wrapper.getWindowId() != 0 || wrapper.getActionId() > 0) return;

            short action = wrapper.getActionId();

            pendingTransactions.addLast(action);
        }
    }
}
