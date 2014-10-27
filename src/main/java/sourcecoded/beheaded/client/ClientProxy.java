package sourcecoded.beheaded.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import sourcecoded.beheaded.CommonProxy;
import sourcecoded.beheaded.Registry;
import sourcecoded.beheaded.client.renderer.item.RenderHead;
import sourcecoded.beheaded.client.renderer.tile.TESRBeheaded;
import sourcecoded.beheaded.tile.TileBeheaded;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerProxy() {
        registerTileRenderers();
    }

    public void registerTileRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBeheaded.class, new TESRBeheaded());

        MinecraftForgeClient.registerItemRenderer(Registry.Items.itemBeheaded, new RenderHead());
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

}
