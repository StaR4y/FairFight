package me.dw1e.ff.command;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.subCommand.*;
import me.dw1e.ff.misc.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FairFightCommand implements TabExecutor {

    private final List<Argument> arguments = new ArrayList<>();
    private final List<String> commands = Arrays.asList(
            "alerts", "getvl", "gui", "help", "ping", "reload", "resetvl", "verbose"
    );

    public FairFightCommand() {
        arguments.add(new AlertsCommand());
        arguments.add(new GetVLCommand());
        arguments.add(new GuiCommand());
        arguments.add(new HelpCommand());
        arguments.add(new PingCommand());
        arguments.add(new ReloadCommand());
        arguments.add(new ResetVLCommand());
        arguments.add(new VerboseCommand());

        Collections.sort(arguments);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> emptyList = Collections.emptyList(), completions = new ArrayList<>();

        commands.stream().filter(sub -> sender.hasPermission("fairfight.command." + sub)).forEach(completions::add);

        if (args.length == 1) return StringUtil.filterStartingWith(completions, args[0]);

        for (Argument arg : arguments) {
            if (arg.getName().equalsIgnoreCase(args[0])) {
                List<String> subCompletions = arg.tabComplete(sender, cmd, label, args);

                return subCompletions == null ? emptyList : subCompletions;
            }
        }

        return emptyList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(FairFight.PREFIX + ChatColor.GRAY + " 使用 " + ChatColor.WHITE
                    + "/" + label + " help" + ChatColor.GRAY + " 查看帮助!");
            return false;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("test") && sender.isOp()) {
            try {
                FairFight.TEST = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 无效的数值: " + args[1]);
            }

            sender.sendMessage(FairFight.PREFIX + ChatColor.DARK_AQUA + " 测试数值已设置为: " + FairFight.TEST);

            return false;
        }

        for (Argument arg : arguments) {
            String name = arg.getName();

            if (name.equalsIgnoreCase(args[0])) {
                if (sender.hasPermission("fairfight.command." + name)) return arg.execute(sender, cmd, label, args);

                sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 你没有此命令的使用权限!");
                return false;
            }
        }

        sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 未知子命令: " + args[0]);
        return false;
    }
}
