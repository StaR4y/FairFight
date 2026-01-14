package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.Argument;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ResetVLCommand extends Argument {

    public ResetVLCommand() {
        super("resetvl");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = FairFight.PREFIX;

        if (args.length != 2) {
            sender.sendMessage(prefix + ChatColor.RED + " 用法: /" + label + " resetvl <*/玩家>");
            return false;
        }

        if (args[1].equals("*")) {
            FairFight.INSTANCE.getDataManager().getDataMap().values().forEach(PlayerData::resetVL);

            sender.sendMessage(prefix + ChatColor.GREEN + " 已重置所有玩家的违规次数");

            return true;
        }

        Player player = Bukkit.getPlayerExact(args[1]);

        if (player == null) {
            sender.sendMessage(prefix + ChatColor.RED + " 没有找到名为 " + args[1] + " 的玩家!");
            return false;
        }

        FairFight.INSTANCE.getDataManager().getData(player.getUniqueId()).resetVL();

        sender.sendMessage(prefix + ChatColor.GREEN + " 已将玩家 " + player.getName() + " 的违规次数重置");

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return args.length != 2 ? null : StringUtil.filterStartingWith(getOnlinePlayers(), args[1]);
    }
}