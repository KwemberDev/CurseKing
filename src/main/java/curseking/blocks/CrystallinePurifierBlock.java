package curseking.blocks;

import curseking.CurseKing;
import curseking.gui.GuiHandler;
import curseking.tileentities.TileEntityCrystallinePurifier;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystallinePurifierBlock extends Block implements ITileEntityProvider {

    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public CrystallinePurifierBlock() {
        super(Material.ROCK);
        setTranslationKey("crystalline_purifier");
        setRegistryName("crystalline_purifier");
        setHardness(3.5F);
        setCreativeTab(CurseKing.CURSEKING_TAB);
        setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(HALF, EnumBlockHalf.LOWER)
                .withProperty(ACTIVE, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
        }
        // Default full block for lower half
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, EnumFacing side,
                                    float hitX, float hitY, float hitZ) {
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            pos = pos.down();
            state = world.getBlockState(pos);
            if (state.getBlock() != this) return false;
        }
        if (!world.isRemote && state.getValue(HALF) == EnumBlockHalf.LOWER) {
            player.openGui(CurseKing.instance, GuiHandler.CRYSTALLINE_PURIFIER_GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrystallinePurifier();
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

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HALF, ACTIVE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumBlockHalf half = (meta & 8) == 0 ? EnumBlockHalf.LOWER : EnumBlockHalf.UPPER;
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        boolean active = (meta & 4) != 0;
        return getDefaultState().withProperty(HALF, half).withProperty(FACING, facing).withProperty(ACTIVE, active);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) meta |= 8;
        if (state.getValue(ACTIVE)) meta |= 4;
        return meta;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.LOWER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.LOWER ? new TileEntityCrystallinePurifier() : null;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return pos.getY() < world.getHeight() - 1 && super.canPlaceBlockAt(world, pos)
                && world.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer) {
        EnumFacing playerFacing = placer.getHorizontalFacing();
        return getDefaultState().withProperty(FACING, playerFacing).withProperty(HALF, EnumBlockHalf.LOWER);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
                                EntityLivingBase placer, net.minecraft.item.ItemStack stack) {
        world.setBlockState(pos.up(), state.withProperty(HALF, EnumBlockHalf.UPPER), 2);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (state.getValue(HALF) == EnumBlockHalf.LOWER) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityCrystallinePurifier) {
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, (TileEntityCrystallinePurifier) tileentity);
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(world, pos, state);
    }
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumBlockHalf half = state.getValue(HALF);
        if (half == EnumBlockHalf.LOWER) {
            if (world.getBlockState(pos.up()).getBlock() != this) {
                world.setBlockToAir(pos);
            }
        } else {
            if (world.getBlockState(pos.down()).getBlock() != this) {
                world.setBlockToAir(pos);
            }
        }
    }

    public enum EnumBlockHalf implements IStringSerializable {
        LOWER, UPPER;
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    public void setActive(World world, BlockPos pos, boolean active) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return;

        EnumBlockHalf half = state.getValue(HALF);
        BlockPos lowerPos = half == EnumBlockHalf.LOWER ? pos : pos.down();
        BlockPos upperPos = lowerPos.up();

        // Preserve tile entity
        TileEntity te = world.getTileEntity(lowerPos);

        // Update lower half
        IBlockState lowerState = world.getBlockState(lowerPos);
        if (lowerState.getBlock() == this) {
            world.setBlockState(lowerPos, lowerState.withProperty(ACTIVE, active), 2);
            if (te != null) {
                te.validate();
                world.setTileEntity(lowerPos, te);
            }
        }
        // Update upper half
        IBlockState upperState = world.getBlockState(upperPos);
        if (upperState.getBlock() == this) {
            world.setBlockState(upperPos, upperState.withProperty(ACTIVE, active), 2);
        }
    }
}