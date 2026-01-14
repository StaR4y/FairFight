package me.dw1e.ff.command.subCommand;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.command.Argument;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class GuiCommand extends Argument {

    public GuiCommand() {
        super("gui");
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(FairFight.PREFIX + ChatColor.RED + " 此命令仅玩家可使用!");
            return false;
        }

        FairFight.INSTANCE.getGuiManager().getMainGui().openGui((Player) sender);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}