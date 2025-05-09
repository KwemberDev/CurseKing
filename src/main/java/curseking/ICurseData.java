package curseking;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Set;

public interface ICurseData {
    boolean hasCurse(String curseId);
    void addCurse(String curseId);
    void removeCurse(String curseId);
    Set<String> getAllCurses();

    boolean hasBlessing(String blessingId);
    void addBlessing(String blessingId);
    void removeBlessing(String blessingId);
    Set<String> getAllBlessings();

    NBTTagCompound serializeNBT();
    void deserializeNBT(NBTTagCompound tag);

    void copyFrom(ICurseData oldData);
}
