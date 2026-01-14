package me.dw1e.ff;

import org.bukkit.plugin.java.JavaPlugin;

public final class FairFightPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        FairFight.INSTANCE.onEnable(this);
    }

    @Override
    public void onDisable() {
        FairFight.INSTANCE.onDisable(this);
    }
}
