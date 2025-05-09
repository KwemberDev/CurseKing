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

// CommandCurse.java
public class CommandCurse extends CommandBase {

    @Override
    public String getName() {
        return "curse";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/curse <add|remove|get> <player> [curse_id]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) throw new WrongUsageException(getUsage(sender));

        EntityPlayerMP target = getPlayer(server, sender, args[1]);
        ICurseData curseData = target.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (curseData == null) {
            throw new CommandException("Curse capability is missing for player. Make sure it's properly attached.");
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "add": {
                if (args.length < 3) throw new WrongUsageException(getUsage(sender));
                String curseId = args[2];
                curseData.addCurse(curseId);
                sendCurseDataToClient((EntityPlayerMP) sender);
                target.sendStatusMessage(new TextComponentString("You have been cursed with: " + curseId), true);
                notifyCommandListener(sender, this, "Added curse '" + curseId + "' to " + target.getName());
                break;
            }

            case "remove": {
                if (args.length < 3) throw new WrongUsageException(getUsage(sender));
                String curseId = args[2];
                curseData.removeCurse(curseId);
                sendCurseDataToClient((EntityPlayerMP) sender);
                target.sendStatusMessage(new TextComponentString("Curse removed: " + curseId), true);
                notifyCommandListener(sender, this, "Removed curse '" + curseId + "' from " + target.getName());
                break;
            }

            case "get": {
                Set<String> curses = curseData.getAllCurses();
                if (curses.isEmpty()) {
                    sender.sendMessage(new TextComponentString(target.getName() + " has no curses."));
                } else {
                    sender.sendMessage(new TextComponentString("Curses on " + target.getName() + ":"));
                    for (String curse : curses) {
                        sender.sendMessage(new TextComponentString(" - " + curse));
                    }
                }
                break;
            }

            default:
                throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Only server operators
    }
}
