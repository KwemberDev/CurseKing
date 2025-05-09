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
        Biome targetBiome = BiomeRegistry.Grave; // Replace with your actual biome reference

        int maxRadius = 5120; // Max search radius in blocks
        int chunkStep = 1;    // Steps in chunk units (1 = 16 blocks)
        BlockPos found = null;

        outerLoop:
        for (int radius = 0; radius < maxRadius; radius += 16 * chunkStep) {
            for (int dx = -radius; dx <= radius; dx += 16 * chunkStep) {
                for (int dz = -radius; dz <= radius; dz += 16 * chunkStep) {

                    // Only check edge of square to avoid redundant checks
                    if (Math.abs(dx) != radius && Math.abs(dz) != radius) continue;

                    BlockPos checkPos = start.add(dx, 0, dz);
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
            sender.sendMessage(new TextComponentString("Biome not found within " + maxRadius + " blocks."));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // OP permission level
    }
}
