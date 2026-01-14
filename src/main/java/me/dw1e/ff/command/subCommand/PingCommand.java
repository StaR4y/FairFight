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

public final class PingCommand extends Argument {

    public PingCommand() {
        super("ping");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = FairFight.PREFIX;

        if (args.length != 2) {
            sender.sendMessage(prefix + ChatColor.RED + " 用法: /" + label + " ping <玩家>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[1]);

        if (player == null) {
            sender.sendMessage(prefix + ChatColor.RED + " 没有找到名为 " + args[1] + " 的玩家!");
            return false;
        }

        PlayerData data = FairFight.INSTANCE.getDataManager().getData(player.getUniqueId());

        sender.sendMessage(prefix + ChatColor.GRAY + " 玩家 " + ChatColor.WHITE + player.getName()
                + ChatColor.GRAY + " 当前的延迟为: " + ChatColor.WHITE + data.getTransPing() + ChatColor.GRAY + "ms");

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return args.length != 2 ? null : StringUtil.filterStartingWith(getOnlinePlayers(), args[1]);
    }
}