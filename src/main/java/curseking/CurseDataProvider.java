package curseking;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CurseDataProvider implements ICapabilitySerializable<NBTTagCompound> {

    public static final ResourceLocation CURSE_CAP = new ResourceLocation(CurseKing.MODID, "curse_data");

    @CapabilityInject(ICurseData.class)
    public static final Capability<ICurseData> CURSE_DATA_CAP = null;

    private final ICurseData instance = new CurseDataImpl();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CURSE_DATA_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CURSE_DATA_CAP ? CURSE_DATA_CAP.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) CURSE_DATA_CAP.getStorage().writeNBT(CURSE_DATA_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CURSE_DATA_CAP.getStorage().readNBT(CURSE_DATA_CAP, instance, null, nbt);
    }
}
