package me.dw1e.ff.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class FairFightFlagEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final String category, type, info, checkDesc;
    private final double vl;
    private final int maxVL;

    public FairFightFlagEvent(Player player, String category, String type, String info, double vl, int maxVL, String checkDesc) {
        super(player);
        this.category = category;
        this.type = type;
        this.info = info;
        this.vl = vl;
        this.maxVL = maxVL;
        this.checkDesc = checkDesc;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public double getVL() {
        return vl;
    }

    public int getMaxVL() {
        return maxVL;
    }

    public String getCheckDescription() {
        return checkDesc;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
