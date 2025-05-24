package curseking.blocks;

import curseking.CurseKing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAshenTorch extends BlockTorch {

    public BlockAshenTorch() {
        super();
        setTranslationKey(CurseKing.MODID + ".blockAshenTorch");
        setRegistryName("ashentorch");
        setHardness(0.5F);
        setCreativeTab(CurseKing.CURSEKING_TAB);
        setSoundType(SoundType.WOOD);
        setLightLevel(0.85F);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        EnumFacing facing = state.getValue(FACING);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.7;
        double z = pos.getZ() + 0.5;

        if (facing.getAxis().isHorizontal()) {
            EnumFacing opposite = facing.getOpposite();
            x += 0.27 * opposite.getXOffset();
            z += 0.27 * opposite.getZOffset();
        }

        // Purple color: r=0.6, g=0.1, b=0.7
        world.spawnParticle(
                EnumParticleTypes.REDSTONE,
                x, y, z,
                0.6, 0.1, 0.7 // RGB for purple
        );
    }
}
