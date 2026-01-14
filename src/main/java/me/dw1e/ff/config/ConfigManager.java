package me.dw1e.ff.config;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.FairFightPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class ConfigManager {

    private FileConfiguration config, checks;
    private File configFile, checksFile;

    public void enable() {
        FairFightPlugin plugin = FairFight.INSTANCE.getPlugin();

        configFile = new File(plugin.getDataFolder(), "config.yml");
        checksFile = new File(plugin.getDataFolder(), "checks.yml");

        if (!configFile.exists()) plugin.saveResource("config.yml", false);
        if (!checksFile.exists()) plugin.saveResource("checks.yml", false);

        loadConfigs();
    }

    public void disable() {
        config = checks = null;
        configFile = checksFile = null;
    }

    public void loadConfigs() {
        config = YamlConfiguration.loadConfiguration(configFile);
        checks = YamlConfiguration.loadConfiguration(checksFile);

        ConfigValue.update();
    }

    public void saveChecks() {
        try {
            checks.save(checksFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 不要保存config.yml这个文件, 会导致你的注释全部消失

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getChecks() {
        return checks;
    }
}
