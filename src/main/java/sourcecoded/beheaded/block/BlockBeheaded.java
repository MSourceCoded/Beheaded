package sourcecoded.beheaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.beheaded.Registry;
import sourcecoded.beheaded.tile.TileBeheaded;
import sourcecoded.beheaded.utils.ItemUtils;

import java.util.Random;

public class BlockBeheaded extends BlockContainer {

    public BlockBeheaded() {
        super(Material.circuits);
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
        this.setHardness(1F);
        this.setBlockTextureName("minecraft:stone");
    }

    public int getRenderType() {
        return -1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int l = iBlockAccess.getBlockMetadata(x, y, z) & 7;

        switch (l)
        {
            case 1:
            default:
                this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                break;
            case 2:
                this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                break;
            case 3:
                this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                break;
            case 4:
                this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                break;
            case 5:
                this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBeheaded();
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Registry.Items.itemBeheaded;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileBeheaded tile = (TileBeheaded) world.getTileEntity(x, y, z);
        String name = tile.headType;

        ItemUtils.dropSkull(name, x, y, z, world);

        super.breakBlock(world, x, y, z, block, meta);
    }

    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

}
