package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.Argument;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class ReloadCommand extends Argument {

    public ReloadCommand() {
        super("reload");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        FairFight.INSTANCE.reloadConfig();

        sender.sendMessage(FairFight.PREFIX + ChatColor.GREEN + " 已重载配置文件");

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}