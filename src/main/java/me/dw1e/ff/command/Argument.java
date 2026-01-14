package me.dw1e.ff.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Argument implements Comparable<Argument> {

    private final String name;

    public Argument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean execute(CommandSender sender, Command cmd, String label, String[] args);

    public abstract List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args);

    protected List<String> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    @Override
    public int compareTo(Argument other) {
        return name.compareTo(other.name);
    }
}
