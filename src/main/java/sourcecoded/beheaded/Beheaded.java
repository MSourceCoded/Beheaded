package sourcecoded.beheaded;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.beheaded.command.GiveMobhead;
import sourcecoded.beheaded.utils.ConfigUtils;
import sourcecoded.beheaded.utils.EntityHelper;
import sourcecoded.core.SourceCodedCore;
import sourcecoded.core.configuration.SourceConfig;
import sourcecoded.core.configuration.gui.SourceConfigGuiFactory;
import sourcecoded.core.version.VersionChecker;

@Mod(modid = Beheaded.MODID, version = Beheaded.VERSION, dependencies = "required-after:sourcecodedcore")
public class Beheaded {

    public static final String MODID = "beheaded";
    public static final String VERSION = "@VERSION@";

    public static SourceConfig configuration;

    public static VersionChecker checker;

    @Mod.Instance(Beheaded.MODID)
    public static Beheaded instance;

    @SidedProxy(clientSide="sourcecoded.beheaded.client.ClientProxy", serverSide="sourcecoded.beheaded.CommonProxy")
    public static CommonProxy proxy;

    public static SourceConfigGuiFactory guiFactory;

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

        if (!SourceCodedCore.isDevEnv && configuration.getBool(ConfigUtils.VERSION_CATEGORY, ConfigUtils.VERSION_ENABLED))
            checker = new VersionChecker(Beheaded.MODID, "https://raw.githubusercontent.com/MSourceCoded/Beheaded/master/version/{MC}.txt", Beheaded.VERSION, configuration.getBool(ConfigUtils.VERSION_CATEGORY, ConfigUtils.VERSION_AUTO), configuration.getBool(ConfigUtils.VERSION_CATEGORY, ConfigUtils.VERSION_SILENT));

        MinecraftForge.EVENT_BUS.register(new DropLogic());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {
        ConfigUtils.dropChancesRefresh(configuration);

        if (checker != null)
            checker.check();

        guiFactory = SourceConfigGuiFactory.create(Beheaded.MODID, instance, configuration);
        guiFactory.inject();
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent ev) {
        ev.registerServerCommand(new GiveMobhead());
    }

}
