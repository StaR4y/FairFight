package me.dw1e.ff.packet.wrapper.client;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.dw1e.ff.packet.wrapper.WrappedPacket;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public final class CPacketBlockPlace extends WrappedPacket {

    private final BlockPosition blockPosition;
    private final int face;
    private final ItemStack itemStack;
    private final float facingX, facingY, facingZ;

    public CPacketBlockPlace(PacketContainer container) {
        blockPosition = container.getBlockPositionModifier().read(0);
        face = container.getIntegers().read(0);
        itemStack = container.getItemModifier().read(0);

        StructureModifier<Float> floats = container.getFloat();

        facingX = floats.read(0);
        facingY = floats.read(1);
        facingZ = floats.read(2);
    }

    public BlockFace getBlockFace() {
        switch (face) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                return BlockFace.SELF;
        }
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public int getFace() {
        return face;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public float getFacingX() {
        return facingX;
    }

    public float getFacingY() {
        return facingY;
    }

    public float getFacingZ() {
        return facingZ;
    }

    public boolean isUseItem() {
        return itemStack != null
                && blockPosition.getX() == -1
                && (blockPosition.getY() == -1 || blockPosition.getY() == 255)
                && blockPosition.getZ() == -1
                && facingX == 0.0F
                && facingY == 0.0F
                && facingZ == 0.0F
                && face == 255;
    }

    public boolean isPlacedBlock() {
        return !isUseItem()
                && blockPosition != null
                && !itemStack.getType().equals(Material.AIR)
                && itemStack.getType().isBlock();
    }
}
