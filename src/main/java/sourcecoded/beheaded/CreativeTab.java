package sourcecoded.beheaded;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab {

    public static CreativeTabs beheaded = new CreativeTabs("beheaded") {
        @Override
        public Item getTabIconItem() {
            return Items.skull;
        }
    };
}
