package sourcecoded.beheaded.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sourcecoded.beheaded.Registry;

public class ItemUtils {

    public static void dropSkull(String skullName, int x, int y, int z, World world) {
        Item item = Registry.Items.itemBeheaded;
        ItemStack stack = new ItemStack(item, 1, 0);

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type", skullName);

        stack.setTagCompound(nbt);

        EntityItem entityitem = new EntityItem(world, (double)x, (double)y + 0.5D, (double)z, stack);
        entityitem.delayBeforeCanPickup = 0;
        world.spawnEntityInWorld(entityitem);
    }

    public static void dropSkull(String skullName, EntityPlayer player) {
        Item item = Registry.Items.itemBeheaded;
        ItemStack stack = new ItemStack(item, 1, 0);

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type", skullName);

        stack.setTagCompound(nbt);

        EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack, false);
        entityitem.delayBeforeCanPickup = 0;
        entityitem.func_145797_a(player.getCommandSenderName());
    }

}
