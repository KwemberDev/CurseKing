package curseking.blocks;

import curseking.CurseKing;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class SmallCrucifix extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final Random random = new Random();

    public SmallCrucifix() {
        super(Material.ANVIL);
        setTranslationKey("smallcrucifix");
        setRegistryName("smallcrucifix");
        setHardness(2.0F);
        setResistance(5.0F);
        setCreativeTab(CurseKing.CURSEKING_TAB);
        setSoundType(SoundType.METAL);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        SmallCrucifix.EnumVariant variant;
        EnumFacing facing = null;

        if (placer != null) {
            variant = placer.isSneaking() ? SmallCrucifix.EnumVariant.SLANTED : SmallCrucifix.EnumVariant.UPRIGHT;
            facing = placer.getHorizontalFacing();
        } else {
            boolean var = random.nextBoolean();
            variant = var ? SmallCrucifix.EnumVariant.UPRIGHT : SmallCrucifix.EnumVariant.SLANTED;
            int rand = random.nextInt(4);
            switch (rand) {
                case 0:
                    facing = EnumFacing.NORTH;
                    break;
                case 1:
                    facing = EnumFacing.SOUTH;
                    break;
                case 2:
                    facing = EnumFacing.WEST;
                    break;
                case 3:
                    facing = EnumFacing.EAST;
                    break;
            }
        }

        IBlockState upper = getDefaultState()
                .withProperty(VARIANT, variant)
                .withProperty(FACING, facing);
        world.setBlockState(pos, upper, 2);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return new AxisAlignedBB(0, 0, 0.4, 1, 0.999, 0.6);
            case WEST:
            case EAST:
                return new AxisAlignedBB(0.4, 0, 0, 0.6, 1, 1);
            default:
                return FULL_BLOCK_AABB;
        }
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        SmallCrucifix.EnumVariant variant = (meta & 2) == 0 ? SmallCrucifix.EnumVariant.UPRIGHT : SmallCrucifix.EnumVariant.SLANTED;

        // FACING: extract bits 2 and 3
        int facingBits = (meta >> 2) & 3;
        EnumFacing[] horizontalFacings = {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
        EnumFacing facing = horizontalFacings[facingBits % 4];

        return getDefaultState()
                .withProperty(VARIANT, variant)
                .withProperty(FACING, facing);
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        if (state.getValue(VARIANT) == SmallCrucifix.EnumVariant.SLANTED) meta |= 2;

        // FACING: 2 bits (NORTH=0, SOUTH=1, WEST=2, EAST=3)
        meta |= (state.getValue(FACING).getHorizontalIndex() << 2);

        return meta;
    }

    public static final PropertyEnum<SmallCrucifix.EnumVariant> VARIANT = PropertyEnum.create("variant", SmallCrucifix.EnumVariant.class);

    public enum EnumVariant implements IStringSerializable {
        UPRIGHT("upright"),
        SLANTED("slanted");

        private final String name;

        EnumVariant(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos)
                && worldIn.getBlockState(pos.up()).getBlock().isReplaceable(worldIn, pos.up());
    }
}
