package curseking.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
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

public class BlockCrucifix extends Block {

    public static final PropertyEnum<BlockDoor.EnumDoorHalf> HALF = PropertyEnum.create("half", BlockDoor.EnumDoorHalf.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final Random random = new Random();
    private static final AxisAlignedBB UPRIGHT_BOTTOM_AABB = new AxisAlignedBB((double) 4 / 16, (double) 0, (double) 4 / 16, (double) 12 / 16, (double) 1, (double) 12 / 16);
    private static final AxisAlignedBB UPRIGHT_TOP_AABB = new AxisAlignedBB((double) 4 / 16, (double) 0, (double) 4 / 16, (double) 12 / 16, (double) 2, (double) 12 / 16);
    private static final AxisAlignedBB SLANTED_BOTTOM_AABB = new AxisAlignedBB((double) 0, (double) 0, (double) 4 / 16, (double) 1, (double) 1, (double) 12 / 16);
    private static final AxisAlignedBB SLANTED_TOP_AABB = new AxisAlignedBB((double) 0, (double) 0, (double) 4 / 16, (double) 1, (double) 2, (double) 12 / 16);

    public BlockCrucifix() {
        super(Material.WOOD);
        setTranslationKey("crucifix");
        setRegistryName("crucifix");
        setHardness(2.0F);
        setResistance(5.0F);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setSoundType(SoundType.METAL);
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
        EnumVariant variant;
        EnumFacing facing = null;

        if (placer != null) {
            variant = placer.isSneaking() ? EnumVariant.SLANTED : EnumVariant.UPRIGHT;
            facing = placer.getHorizontalFacing().getOpposite();
        } else {
            boolean var = random.nextBoolean();
            variant = var ? EnumVariant.UPRIGHT : EnumVariant.SLANTED;
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

        IBlockState lower = getDefaultState()
                .withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER)
                .withProperty(VARIANT, variant)
                .withProperty(FACING, facing);

        IBlockState upper = getDefaultState()
                .withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER)
                .withProperty(VARIANT, variant)
                .withProperty(FACING, facing);

        world.setBlockState(pos, lower, 2);
        world.setBlockState(pos.up(), upper, 2);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        BlockPos otherHalf = (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) ? pos.down() : pos.up();
        IBlockState otherState = world.getBlockState(otherHalf);
        if (otherState.getBlock() == this && otherState.getValue(HALF) != state.getValue(HALF)) {
            world.setBlockToAir(otherHalf);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        boolean isTop = state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER;
        boolean isSlanted = state.getValue(VARIANT) == EnumVariant.SLANTED;
        EnumFacing facing = state.getValue(FACING);

        AxisAlignedBB baseAABB = isTop
                ? SLANTED_TOP_AABB
                : SLANTED_BOTTOM_AABB;

        if (!isSlanted) {
            return isTop ? UPRIGHT_TOP_AABB : UPRIGHT_BOTTOM_AABB;
        }

        // Rotate the AABB based on facing
        switch (facing) {
            case NORTH:
                return baseAABB;
            case SOUTH:
                return new AxisAlignedBB(
                        1 - baseAABB.maxX, baseAABB.minY, 1 - baseAABB.maxZ,
                        1 - baseAABB.minX, baseAABB.maxY, 1 - baseAABB.minZ
                );
            case WEST:
                return new AxisAlignedBB(
                        baseAABB.minZ, baseAABB.minY, 1 - baseAABB.maxX,
                        baseAABB.maxZ, baseAABB.maxY, 1 - baseAABB.minX
                );
            case EAST:
                return new AxisAlignedBB(
                        1 - baseAABB.maxZ, baseAABB.minY, baseAABB.minX,
                        1 - baseAABB.minZ, baseAABB.maxY, baseAABB.maxX
                );
            default:
                return baseAABB;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, VARIANT, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        BlockDoor.EnumDoorHalf half = (meta & 1) == 0 ? BlockDoor.EnumDoorHalf.LOWER : BlockDoor.EnumDoorHalf.UPPER;
        EnumVariant variant = (meta & 2) == 0 ? EnumVariant.UPRIGHT : EnumVariant.SLANTED;

        // FACING: extract bits 2 and 3
        int facingBits = (meta >> 2) & 3;
        EnumFacing[] horizontalFacings = {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
        EnumFacing facing = horizontalFacings[facingBits % 4];

        return getDefaultState()
                .withProperty(HALF, half)
                .withProperty(VARIANT, variant)
                .withProperty(FACING, facing);
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) meta |= 1;
        if (state.getValue(VARIANT) == EnumVariant.SLANTED) meta |= 2;

        // FACING: 2 bits (NORTH=0, SOUTH=1, WEST=2, EAST=3)
        meta |= (state.getValue(FACING).getHorizontalIndex() << 2);

        return meta;
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);

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
