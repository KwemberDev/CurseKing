package curseking;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CurseDataImpl implements ICurseData {

    private final Set<String> activeCurses = new HashSet<>();
    private final Set<String> blessings = new HashSet<>();

    @Override
    public boolean hasCurse(String curseId) {
        return activeCurses.contains(curseId);
    }

    @Override
    public void addCurse(String curseId) {
        activeCurses.add(curseId);
    }

    @Override
    public void removeCurse(String curseId) {
        activeCurses.remove(curseId);
    }

    @Override
    public Set<String> getAllCurses() {
        return Collections.unmodifiableSet(activeCurses);
    }

    @Override
    public boolean hasBlessing(String id) {
        return blessings.contains(id);
    }

    @Override
    public void addBlessing(String id) {
        blessings.add(id);
    }

    @Override
    public void removeBlessing(String id) {
        blessings.remove(id);
    }

    @Override
    public Set<String> getAllBlessings() {
        return Collections.unmodifiableSet(blessings);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList curseList = new NBTTagList();
        for (String curse : activeCurses) {
            curseList.appendTag(new NBTTagString(curse));
        }
        tag.setTag("Curses", curseList);

        NBTTagList blessingList = new NBTTagList();
        for (String blessing : blessings) {
            blessingList.appendTag(new NBTTagString(blessing));
        }
        tag.setTag("Blessings", blessingList);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        activeCurses.clear();
        blessings.clear();

        NBTTagList curseList = nbt.getTagList("Curses", Constants.NBT.TAG_STRING);
        for (int i = 0; i < curseList.tagCount(); i++) {
            activeCurses.add(curseList.getStringTagAt(i));
        }

        NBTTagList blessingList = nbt.getTagList("Blessings", Constants.NBT.TAG_STRING);
        for (int i = 0; i < blessingList.tagCount(); i++) {
            blessings.add(blessingList.getStringTagAt(i));
        }
    }

    public void copyFrom(ICurseData old) {
        this.activeCurses.clear();
        this.activeCurses.addAll(old.getAllCurses());

        this.blessings.clear();
        this.blessings.addAll(old.getAllBlessings());
    }

}
