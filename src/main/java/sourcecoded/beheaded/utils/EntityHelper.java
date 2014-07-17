package sourcecoded.beheaded.utils;

import com.google.gson.stream.JsonWriter;
import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import sourcecoded.beheaded.Beheaded;

import java.io.*;
import java.util.*;

public class EntityHelper {

    public static HashMap<String, Class<? extends EntityLiving>> living = new HashMap<String, Class<? extends EntityLiving>>();
    public static HashMap<Class<? extends EntityLiving>, String> livingRev = new HashMap< Class<? extends EntityLiving>, String>();

    public static HashMap<String, Double> dropChances = new HashMap<String, Double>();
    public static double defaultChance = 0.05;

    @SuppressWarnings("unchecked")
    public static void init() {
        Map mapBasic = EntityList.stringToClassMapping;
        Collection en = mapBasic.values();
        Object[] array = en.toArray();
        Object[] arrayKeys = mapBasic.keySet().toArray();

        for (int i = 0; i < en.size(); i++) {
            Class curr = (Class) array[i];

            if (EntityLiving.class.isAssignableFrom(curr)) {
                Class<EntityLiving> livingC = (Class<EntityLiving>) curr;
                living.put((String) arrayKeys[i], livingC);
            }
        }

        //Don't impact the Init Phase
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONify((HashMap<String, Class<? extends EntityLiving>>) living.clone());
            }
        }).start();

        checkTexturesUniversal((HashMap<String, Class<? extends EntityLiving>>) living.clone());

        copy((HashMap<String, Class<? extends EntityLiving>>) living.clone());
    }

    public static void copy(HashMap<String, Class<? extends EntityLiving>> old) {
        livingRev.clear();

        Iterator<Map.Entry<String, Class<? extends EntityLiving>>> it = old.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Class<? extends EntityLiving>> pairs = it.next();

            livingRev.put(pairs.getValue(), pairs.getKey());

            it.remove();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void JSONify(HashMap<String, Class<? extends EntityLiving>> values) {
        String MCPath = ((File)(FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
        File path = new File(MCPath + "/mods/beheaded");
        File actualFile = new File(path + "/entityList.json");

        path.mkdirs();

        try {
            FileWriter writer = new FileWriter(actualFile);
            JsonWriter jwrite = new JsonWriter(writer);

            jwrite.beginObject();

            Iterator<Map.Entry<String, Class<? extends EntityLiving>>> it;
            it = values.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Class<? extends EntityLiving>> pairs = it.next();

                jwrite.name(pairs.getKey());
                jwrite.beginObject();

                jwrite.name("Entity Name");
                jwrite.value(pairs.getKey());

                jwrite.name("Entity Class");
                jwrite.value(String.valueOf(pairs.getValue()));

                jwrite.endObject();
                it.remove();
            }

            jwrite.endObject();
            jwrite.close();
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @Deprecated
    public static void checkTextures(HashMap<String, Class<? extends EntityLiving>> available) {
        Iterator<Map.Entry<String, Class<? extends EntityLiving>>> it = available.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Class<? extends EntityLiving>> pairs = it.next();

            ResourceLocation location = new ResourceLocation("beheaded", String.format("textures/model/head/%s.png", pairs.getKey()));
            IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();

            try {
                List allResources = resourceManager.getAllResources(location);
            } catch (Exception er) {
                living.remove(pairs.getKey());
            }

            it.remove();
        }
    }

    public static void checkTexturesUniversal(HashMap<String, Class<? extends EntityLiving>> available) {
        Iterator<Map.Entry<String, Class<? extends EntityLiving>>> it = available.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Class<? extends EntityLiving>> pairs = it.next();

            InputStream st = Beheaded.class.getResourceAsStream(String.format("/assets/beheaded/textures/model/head/%s.png", pairs.getKey()));

            if (st == null) {
                living.remove(pairs.getKey());
            }

            it.remove();
        }
    }
}
