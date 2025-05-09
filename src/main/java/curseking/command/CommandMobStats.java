package curseking.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CommandMobStats extends CommandBase {

    @Override
    public String getName() {
        return "mobstats";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mobstats - Displays stats of the nearest mob";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        World world = player.world;

        EntityLivingBase nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Entity entity : world.loadedEntityList) {
            if (!(entity instanceof EntityLivingBase) || entity instanceof EntityPlayer) continue;

            double dist = entity.getDistance(player);
            if (dist < minDist) {
                minDist = dist;
                nearest = (EntityLivingBase) entity;
            }
        }

        if (nearest == null) {
            sender.sendMessage(new TextComponentString("No mob found nearby."));
            return;
        }

        float healthAttr = nearest.getMaxHealth();
        IAttributeInstance speedAttr = nearest.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        IAttributeInstance kbAttr = nearest.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        double maxHealth = healthAttr;
        double movementSpeed = speedAttr != null ? speedAttr.getAttributeValue() : -1;
        double knockbackResistance = kbAttr != null ? kbAttr.getAttributeValue() : -1;

        sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Nearest Mob: " + nearest.getName()));
        sender.sendMessage(new TextComponentString("  Max Health: " + maxHealth));
        sender.sendMessage(new TextComponentString("  Movement Speed: " + movementSpeed));
        sender.sendMessage(new TextComponentString("  Attack Damage: " + knockbackResistance));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Allow all players
    }
}
