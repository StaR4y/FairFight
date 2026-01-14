package me.dw1e.ff.misc.util;

import me.dw1e.ff.misc.math.MathUtil;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public final class PlayerUtil {

    public static int getAmplifier(Player player, PotionEffectType effectType) {
        return player.getActivePotionEffects().stream()
                .filter(effect -> effect.getType().equals(effectType))
                .findFirst().map(effect -> effect.getAmplifier() + 1).orElse(0);
    }

    public static BlockFace getDirection(float yaw) {
        yaw = MathUtil.normalizeYaw(yaw);

        if (0.0F <= yaw && yaw < 45.0F) return BlockFace.WEST;
        else if (45.0F <= yaw && yaw < 135.0F) return BlockFace.NORTH;
        else if (135.0F <= yaw && yaw < 225.0F) return BlockFace.EAST;
        else if (225.0F <= yaw && yaw < 315.0F) return BlockFace.SOUTH;
        else if (315.0F <= yaw && yaw < 360.0F) return BlockFace.WEST;
        else return BlockFace.SELF;
    }

}
