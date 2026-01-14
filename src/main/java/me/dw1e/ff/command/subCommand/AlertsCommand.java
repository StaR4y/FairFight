package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.Argument;
import me.dw1e.ff.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public final class AlertsCommand extends Argument {

    public AlertsCommand() {
        super("alerts");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 此命令仅玩家可使用!");
            return false;
        }

        Player player = (Player) sender;

        PlayerData data = FairFight.INSTANCE.getDataManager().getData(player.getUniqueId());

        if (data == null) return false;

        data.setAlerts(!data.isAlerts());

        sender.sendMessage(FairFight.PREFIX + ChatColor.GRAY + " 警报现已"
                + (data.isAlerts() ? ChatColor.GREEN + "开启" : ChatColor.RED + "关闭") + ChatColor.WHITE + "!");

        if (data.isAlerts())
            player.setMetadata("FAIR_FIGHT_ALERTS", new FixedMetadataValue(FairFight.INSTANCE.getPlugin(), true));
        else player.removeMetadata("FAIR_FIGHT_ALERTS", FairFight.INSTANCE.getPlugin());

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}