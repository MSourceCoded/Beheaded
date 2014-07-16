package sourcecoded.beheaded;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sourcecoded.beheaded.utils.EntityHelper;

import java.util.Random;

public class DropLogic {

    @SuppressWarnings("SuspiciousMethodCalls")
    @SubscribeEvent
    public void mobKilled(LivingDeathEvent event) {
        Random rnd = new Random();
        if (event.entity instanceof EntityLiving && !event.entity.worldObj.isRemote) {
            EntityLivingBase entity =  event.entityLiving;
            if (EntityHelper.livingRev.containsKey(entity.getClass())) {
                String name = EntityHelper.livingRev.get(entity.getClass());

                double chance = EntityHelper.defaultChance;

                if (EntityHelper.dropChances.containsKey(name)) {
                    chance = EntityHelper.dropChances.get(name);
                }

                Item item = Registry.Items.itemBeheaded;
                ItemStack stack = new ItemStack(item, 1, 0);

                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("type", name);

                stack.setTagCompound(nbt);

                if (rnd.nextDouble() <= chance)
                    event.entity.entityDropItem(stack, 1F);
            }
        }
    }

}
