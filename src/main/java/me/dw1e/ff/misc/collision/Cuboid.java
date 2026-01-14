package me.dw1e.ff.misc.collision;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class Cuboid {

    private final World world;

    private double minX, minY, minZ, maxX, maxY, maxZ;

    public Cuboid(Location location) {
        world = location.getWorld();

        minX = maxX = location.getX();
        minY = maxY = location.getY();
        minZ = maxZ = location.getZ();
    }

    public Cuboid(World world, BlockPosition position) {
        this.world = world;

        minX = position.getX();
        minY = position.getY();
        minZ = position.getZ();

        maxX = minX + 1.0;
        maxY = minY + 1.0;
        maxZ = minZ + 1.0;
    }

    public Cuboid add(Cuboid cuboid) {
        minX += cuboid.minX;
        minY += cuboid.minY;
        minZ += cuboid.minZ;

        maxX += cuboid.maxX;
        maxY += cuboid.maxY;
        maxZ += cuboid.maxZ;

        return this;
    }

    public Cuboid expandX(double x) {
        minX -= x;
        maxX += x;

        return this;
    }

    public Cuboid expandY(double minY, double maxY) {
        this.minY -= minY;
        this.maxY += maxY;

        return this;
    }

    public Cuboid expandZ(double z) {
        minZ -= z;
        maxZ += z;

        return this;
    }

    public Cuboid expandXZ(double xz) {
        minX -= xz;
        minZ -= xz;

        maxX += xz;
        maxZ += xz;

        return this;
    }

    public Cuboid moveY(double minY, double maxY) {
        this.minY += minY;
        this.maxY += maxY;

        return this;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public List<Block> getBlocks() {
        int x1 = (int) Math.floor(minX), x2 = (int) Math.ceil(maxX);
        int y1 = (int) Math.floor(minY), y2 = (int) Math.ceil(maxY);
        int z1 = (int) Math.floor(minZ), z2 = (int) Math.ceil(maxZ);

        List<Block> blocks = new ArrayList<>();

        if (!world.isChunkLoaded(x1 >> 4, z1 >> 4)
                || !world.isChunkInUse(x1 >> 4, z1 >> 4)) return blocks;

        blocks.add(world.getBlockAt(x1, y1, z1));

        for (int i = x1; i < x2; ++i) {
            for (int j = y1; j < y2; ++j) {
                for (int k = z1; k < z2; ++k) {

                    int cx = i >> 4, cz = k >> 4;
                    if (!world.isChunkLoaded(cx, cz) || !world.isChunkInUse(cx, cz)) continue;

                    try {
                        blocks.add(world.getBlockAt(i, j, k));
                    } catch (Exception ignore) {
                    }
                }
            }
        }

        return blocks;
    }

    public boolean contains(Material... materials) {
        if (materials == null || materials.length == 0) return false;

        return getBlocks().stream().map(Block::getType).anyMatch(Arrays.asList(materials)::contains);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkMaterials(Predicate<Material> predicate) {
        return getBlocks().stream().allMatch(block -> predicate.test(block.getType()));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkBlocks(Predicate<Block> predicate) {
        return getBlocks().stream().allMatch(predicate);
    }
}
