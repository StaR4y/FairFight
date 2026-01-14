package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.Argument;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class HelpCommand extends Argument {

    public HelpCommand() {
        super("help");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        String same = ChatColor.WHITE + "  /" + label;

        sender.sendMessage(FairFight.PREFIX + ChatColor.GRAY + " 可用命令:");

        sender.sendMessage(same + " alerts" + ChatColor.GRAY + ": 开启/关闭警报");
        sender.sendMessage(same + " getvl" + ChatColor.GRAY + ": 获取玩家当前的违规记录");
        sender.sendMessage(same + " gui" + ChatColor.GRAY + ": 打开GUI界面");
        sender.sendMessage(same + " ping" + ChatColor.GRAY + ": 查看玩家当前的延迟");
        sender.sendMessage(same + " reload" + ChatColor.GRAY + ": 重载配置文件");
        sender.sendMessage(same + " resetvl" + ChatColor.GRAY + ": 重置玩家违规次数");
        sender.sendMessage(same + " verbose" + ChatColor.GRAY + ": 开启/关闭自己的完整警报");

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}