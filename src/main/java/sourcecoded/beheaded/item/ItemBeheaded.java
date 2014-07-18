package sourcecoded.beheaded.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sourcecoded.beheaded.Beheaded;
import sourcecoded.beheaded.CreativeTab;
import sourcecoded.beheaded.Registry;
import sourcecoded.beheaded.tile.TileBeheaded;
import sourcecoded.beheaded.utils.EntityHelper;

import java.util.List;

public class ItemBeheaded extends ItemArmor {

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    public ItemBeheaded() {
        super(ArmorMaterial.CLOTH, 0, 0);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTab.beheaded);
        this.setUnlocalizedName("beheaded");
        this.setMaxStackSize(64);
        this.setTextureName("beheaded:headBase");
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
        if (itemStack.stackTagCompound == null || !itemStack.stackTagCompound.hasKey("type")) {
            return null;
        }

        String headType = itemStack.stackTagCompound.getString("type");
        return String.format("%s:textures/model/head/%s.png", Beheaded.MODID, headType);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return 16777215;
    }

    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (par1ItemStack.stackTagCompound == null)
            par1ItemStack.stackTagCompound = new NBTTagCompound();

        if (!par1ItemStack.stackTagCompound.hasKey("type"))
            par1ItemStack.stackTagCompound.setString("type", "Unknown");

        if (!par1ItemStack.stackTagCompound.getString("type").equals("Unknown") && !EntityHelper.living.containsKey(par1ItemStack.stackTagCompound.getString("type")))
            par1ItemStack.stackTagCompound.setString("type", "Unknown");
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xo, float yo, float zo) {
        if (itemstack.stackTagCompound == null)
            return false;
        if (itemstack.stackTagCompound.getString("type").equals("Unknown"))
            return false;

        if (side == 0)
            return false;
        else if (!world.getBlock(x, y, z).getMaterial().isSolid())
            return false;
        else {
            if (side == 1)
                ++y;

            if (side == 2)
                --z;

            if (side == 3)
                ++z;

            if (side == 4)
                --x;

            if (side == 5)
                ++x;

            if (!player.canPlayerEdit(x, y, z, side, itemstack))
                return false;
            else if (!Registry.Blocks.blockBeheaded.canPlaceBlockAt(world, x, y, z))
                return false;
            else {
                world.setBlock(x, y, z, Registry.Blocks.blockBeheaded, side, 2);

                int r = 0;
                if (side == 1)
                    r = MathHelper.floor_double((double) (player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;

                TileEntity tileentity = world.getTileEntity(x, y, z);

                if (tileentity != null && tileentity instanceof TileBeheaded) {
                    String s = itemstack.stackTagCompound.getString("type");

                    ((TileBeheaded) tileentity).setData(r, s);
                }

                --itemstack.stackSize;
                return true;
            }
        }
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("type") && !par1ItemStack.stackTagCompound.getString("type").equals("Unknown"))
            return String.format("Mob Skull %s[%s]", EnumChatFormatting.GOLD, par1ItemStack.stackTagCompound.getString("type"));
        else
            return String.format("Mob Skull %s[Unknown]", EnumChatFormatting.RED);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("type")) {
            if (par1ItemStack.stackTagCompound.getString("type").equals("Unknown")) {
                par3List.add("This 'Unknown' is usually caused");
                par3List.add("by a Mod Update breaking textures.");
            }
        }
    }

}
