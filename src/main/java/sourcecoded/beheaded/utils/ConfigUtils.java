package sourcecoded.beheaded.utils;

import net.minecraft.entity.EntityLiving;
import sourcecoded.core.configuration.SourceConfig;

import java.util.HashMap;

public class ConfigUtils {

    public static String CHANCES_CATEGORY = "DropChances";
    public static String VERSION_CATEGORY = "version";

    public static String CHANCES_PREFIX = "Chances_";
    public static String VERSION_ENABLED = "versionEnabled";
    public static String VERSION_AUTO = "versionAuto";
    public static String VERSION_SILENT = "versionSilent";

    public static void dropChanceInit(SourceConfig c) {
        HashMap<String, Class<? extends EntityLiving>> map = EntityHelper.living;

        for (String name : map.keySet()) {
            c.createProperty(CHANCES_CATEGORY, CHANCES_PREFIX + name, EntityHelper.defaultChance);
        }

        c.createProperty(VERSION_CATEGORY, VERSION_ENABLED, true);
        c.createProperty(VERSION_CATEGORY, VERSION_AUTO, true);
        c.createProperty(VERSION_CATEGORY, VERSION_SILENT, false);

        c.saveConfig();
    }

    public static void dropChancesRefresh(SourceConfig c) {
        c.loadConfig();

        HashMap<String, Class<? extends EntityLiving>> map = EntityHelper.living;

        for (String name : map.keySet()) {
            EntityHelper.dropChances.put(name, c.getDouble(CHANCES_CATEGORY.toLowerCase(), CHANCES_PREFIX + name));
        }

        c.saveConfig();
    }

}
