package curseking.command;

import curseking.biome.BiomeRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CommandFindForgottenDeityBiome extends CommandBase {

    @Override
    public String getName() {
        return "findForgottenBiome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/findForgottenBiome - Finds the nearest GraveForgottenDeity biome";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) return;

        BlockPos start = sender.getPosition();
        Biome targetBiome = BiomeRegistry.Grave;

        int maxRadius = 320; // in chunks (320 * 16 = 5120 blocks)
        BlockPos found = null;

        int startChunkX = start.getX() >> 4;
        int startChunkZ = start.getZ() >> 4;

        outerLoop:
        for (int radius = 0; radius <= maxRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    // Only check the edge of the current square ring
                    if (Math.abs(dx) != radius && Math.abs(dz) != radius) continue;

                    int chunkX = startChunkX + dx;
                    int chunkZ = startChunkZ + dz;
                    int blockX = (chunkX << 4) + 8; // center of chunk
                    int blockZ = (chunkZ << 4) + 8;
                    BlockPos checkPos = new BlockPos(blockX, start.getY(), blockZ);

                    Biome biome = world.getBiome(checkPos);
                    if (biome == targetBiome) {
                        found = checkPos;
                        break outerLoop;
                    }
                }
            }
        }

        if (found != null) {
            sender.sendMessage(new TextComponentString(
                    String.format("Found Forgotten Deity biome at: X=%d, Z=%d", found.getX(), found.getZ())));
        } else {
            sender.sendMessage(new TextComponentString("Biome not found within " + (maxRadius * 16) + " blocks."));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
