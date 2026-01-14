package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.command.Argument;
import me.dw1e.ff.data.PlayerData;
import me.dw1e.ff.misc.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GetVLCommand extends Argument {

    public GetVLCommand() {
        super("getvl");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = FairFight.PREFIX;

        if (args.length != 2) {
            sender.sendMessage(prefix + ChatColor.RED + " 用法: /" + label + " getvl <玩家>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[1]);

        if (player == null) {
            sender.sendMessage(prefix + ChatColor.RED + " 没有找到名为 " + args[1] + " 的玩家!");
            return false;
        }

        PlayerData data = FairFight.INSTANCE.getDataManager().getData(player.getUniqueId());

        sender.sendMessage(prefix + ChatColor.GRAY + " 玩家 " + ChatColor.WHITE + player.getName() + ChatColor.GRAY + " 当前的违规次数统计:");

        String line = ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH.toString() + "---------------";

        sender.sendMessage(line);

        boolean hasViolation = false;

        for (Category category : Category.values()) {
            Map<String, Integer> subCategoryViolations = new HashMap<>();

            int totalVL = 0;

            for (Check check : data.getChecks()) {
                if (check.getCategory() != category) continue;

                int vl = (int) Math.round(check.getViolations());
                if (vl <= 0) continue;

                subCategoryViolations.merge(check.getType(), vl, Integer::sum);
                totalVL += vl;
            }

            hasViolation |= totalVL > 0;

            if (!subCategoryViolations.isEmpty()) {
                StringBuilder subLine = new StringBuilder();

                boolean firstEntry = true;

                for (Map.Entry<String, Integer> entry : subCategoryViolations.entrySet()) {
                    if (!firstEntry) subLine.append(ChatColor.GRAY).append("\n");

                    subLine.append(ChatColor.GRAY).append(" - ")
                            .append(ChatColor.DARK_GRAY).append("(")
                            .append(ChatColor.GRAY).append("类型 ")
                            .append(ChatColor.WHITE).append(entry.getKey())
                            .append(ChatColor.DARK_GRAY).append(") [")
                            .append(ChatColor.GRAY).append("x")
                            .append(ChatColor.WHITE).append(entry.getValue())
                            .append(ChatColor.DARK_GRAY).append("]");

                    firstEntry = false;
                }

                sender.sendMessage(ChatColor.WHITE + category.getName() + ChatColor.DARK_GRAY + " ["
                        + ChatColor.GRAY + "x" + ChatColor.WHITE + totalVL + ChatColor.DARK_GRAY + "]");

                sender.sendMessage(subLine.toString());
            }
        }

        if (!hasViolation) sender.sendMessage(ChatColor.GREEN + "该玩家当前无任何违规");

        sender.sendMessage(line);

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return args.length != 2 ? null : StringUtil.filterStartingWith(getOnlinePlayers(), args[1]);
    }
}