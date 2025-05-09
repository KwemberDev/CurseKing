package curseking;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CurseDataStorage implements Capability.IStorage<ICurseData> {

    @Override
    public NBTBase writeNBT(Capability<ICurseData> capability, ICurseData instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ICurseData> capability, ICurseData instance, EnumFacing side, NBTBase nbt) {
        instance.deserializeNBT((NBTTagCompound) nbt);
    }
}

