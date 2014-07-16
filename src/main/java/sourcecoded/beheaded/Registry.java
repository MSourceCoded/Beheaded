package sourcecoded.beheaded;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.beheaded.block.BlockBeheaded;
import sourcecoded.beheaded.item.ItemBeheaded;
import sourcecoded.beheaded.tile.TileBeheaded;

public class Registry {

    public static class Items {
        public static ItemBeheaded itemBeheaded = new ItemBeheaded();

        public static void registerAll() {
            GameRegistry.registerItem(itemBeheaded, "itemHead");
        }
    }

    public static class Blocks {
        public static BlockBeheaded blockBeheaded = new BlockBeheaded();

        public static void registerAll() {
            GameRegistry.registerBlock(blockBeheaded, "blockHead");
        }
    }

    public static class TileEntities {
        public static Class<? extends TileEntity> tileBeheaded = TileBeheaded.class;

        public static void registerAll() {
            GameRegistry.registerTileEntity(tileBeheaded, "TEHead");
        }
    }

}
