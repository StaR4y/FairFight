package me.dw1e.ff.misc;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public final class ServerTickTask implements Runnable {

    private BukkitTask task;
    private int ticks;
    private long lastRespond, respond, lastAlert;

    public ServerTickTask() {
        lastRespond = respond = System.currentTimeMillis();
    }

    public void enable() {
        task = Bukkit.getScheduler().runTaskTimer(FairFight.INSTANCE.getPlugin(), this, 1L, 1L);
    }

    public void disable() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void run() {
        ++ticks;

        long now = System.currentTimeMillis();

        lastRespond = respond;
        respond = now;

        Collection<PlayerData> dataMap = FairFight.INSTANCE.getDataManager().getDataMap().values();

        dataMap.forEach(data -> {
            data.setBypass(data.getPlayer().hasPermission("fairfight.bypass")); // 缓存绕过权限

            data.confirmConnection(); // 验证玩家是否响应确认包
        });

        if (isLagging() && now - lastAlert > 15000L) {
            lastAlert = now;

            long delay = respond - lastRespond;

            String msg = FairFight.PREFIX + ChatColor.RED + " 检测到服务器卡顿(延迟: " + delay + "ms), 已临时暂停检查!";

            dataMap.stream().filter(PlayerData::isAlerts).forEach(data -> data.getPlayer().sendMessage(msg));
        }
    }

    public int getTicks() {
        return ticks;
    }

    public boolean isLagging() {
        return respond - lastRespond > 750L;
    }
}
