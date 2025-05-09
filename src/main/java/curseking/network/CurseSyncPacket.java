package curseking.network;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class CurseSyncPacket implements IMessage {
    public NBTTagCompound data;

    // Required constructor
    public CurseSyncPacket() {}

    public CurseSyncPacket(ICurseData curseData) {
        this.data = curseData.serializeNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ByteBufInputStream stream = new ByteBufInputStream(buf);
        try {
            this.data = CompressedStreamTools.readCompressed(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufOutputStream stream = new ByteBufOutputStream(buf);
        try {
            CompressedStreamTools.writeCompressed(this.data, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<CurseSyncPacket, IMessage> {
        @Override
        public IMessage onMessage(CurseSyncPacket message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
                if (data != null) {
                    data.deserializeNBT(message.data);
                }
            });
            return null;
        }
    }
}
