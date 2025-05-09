package curseking.eventhandlers;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import curseking.network.CurseSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Handler implements IMessageHandler<CurseSyncPacket, IMessage> {
    @Override
    public IMessage onMessage(CurseSyncPacket message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            EntityPlayer player = Minecraft.getMinecraft().player;
            ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
            if (data != null && message.data != null) {
                data.deserializeNBT(message.data);
            }
        });
        return null;
    }
}
