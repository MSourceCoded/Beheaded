package sourcecoded.beheaded;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.beheaded.command.GiveMobhead;
import sourcecoded.beheaded.utils.ConfigUtils;
import sourcecoded.beheaded.utils.EntityHelper;
import sourcecoded.core.configuration.SourceConfig;

@Mod(modid = Beheaded.MODID, version = Beheaded.VERSION, dependencies = "required-after:sourcecodedcore")
public class Beheaded {

    public static final String MODID = "beheaded";
    public static final String VERSION = "@VERSION@";

    public static SourceConfig configuration;

    @SidedProxy(clientSide="sourcecoded.beheaded.client.ClientProxy", serverSide="sourcecoded.beheaded.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        configuration = new SourceConfig(ev.getSuggestedConfigurationFile());
        configuration.saveConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        proxy.registerProxy();



        EntityHelper.init();
        Registry.Items.registerAll();
        Registry.Blocks.registerAll();
        Registry.TileEntities.registerAll();

        ConfigUtils.dropChanceInit(configuration);

        MinecraftForge.EVENT_BUS.register(new DropLogic());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {
        ConfigUtils.dropChancesRefresh(configuration);
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent ev) {
        ev.registerServerCommand(new GiveMobhead());
    }

}
