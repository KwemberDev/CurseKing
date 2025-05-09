package curseking.command;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Set;

import static curseking.eventhandlers.PlayerEventHandler.sendCurseDataToClient;

public class CommandBless extends CommandBase {

    @Override
    public String getName() {
        return "bless";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/bless <add|get|remove> <player> [blessing_id]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException(getUsage(sender));
        }

        String subcommand = args[0];
        EntityPlayerMP target = getPlayer(server, sender, args[1]);
        ICurseData data = target.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data == null) {
            sender.sendMessage(new TextComponentString("Player has no curse data."));
            return;
        }

        switch (subcommand) {
            case "add":
                if (args.length < 3) throw new WrongUsageException(getUsage(sender));
                String blessId = args[2];

                data.addBlessing(blessId);
                sendCurseDataToClient((EntityPlayerMP) sender);

                sender.sendMessage(new TextComponentString("Added blessing '" + blessId + "' to " + target.getName()));
                break;

            case "remove":
                if (args.length < 3) throw new WrongUsageException(getUsage(sender));
                String removeId = args[2];

                data.removeBlessing(removeId);
                sendCurseDataToClient((EntityPlayerMP) sender);

                sender.sendMessage(new TextComponentString("Removed blessing '" + removeId + "' from " + target.getName()));
                break;

            case "get":
                Set<String> blessings = data.getAllBlessings();
                if (blessings.isEmpty()) {
                    sender.sendMessage(new TextComponentString(target.getName() + " has no blessings."));
                } else {
                    sender.sendMessage(new TextComponentString(target.getName() + " has blessings: " + String.join(", ", blessings)));
                }
                break;

            default:
                throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
