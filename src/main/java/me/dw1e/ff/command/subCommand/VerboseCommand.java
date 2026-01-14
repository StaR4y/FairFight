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

public final class VerboseCommand extends Argument {

    public VerboseCommand() {
        super("verbose");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 此命令仅玩家可使用!");
            return false;
        }

        Player player = (Player) sender;

        PlayerData data = FairFight.INSTANCE.getDataManager().getData(player.getUniqueId());

        if (data == null) return false;

        data.setVerbose(!data.isVerbose());

        sender.sendMessage(FairFight.PREFIX + ChatColor.GRAY + " 调试模式现已"
                + (data.isVerbose() ? ChatColor.GREEN + "开启" : ChatColor.RED + "关闭") + ChatColor.GRAY + "!");

        if (data.isVerbose())
            player.setMetadata("FAIR_FIGHT_VERBOSE", new FixedMetadataValue(FairFight.INSTANCE.getPlugin(), true));
        else player.removeMetadata("FAIR_FIGHT_VERBOSE", FairFight.INSTANCE.getPlugin());

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}