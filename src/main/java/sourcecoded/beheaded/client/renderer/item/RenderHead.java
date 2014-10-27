package sourcecoded.beheaded.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sourcecoded.beheaded.tile.TileBeheaded;
import sourcecoded.core.client.renderer.SCRenderManager;
import sourcecoded.core.client.renderer.tile.TESRStaticHandler;

public class RenderHead implements IItemRenderer {

    public ResourceLocation location;
    public static String prefix = "textures/model/head";
    public static ModelSkeletonHead baseModel = new ModelSkeletonHead(0, 0, 64, 32);

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item.stackTagCompound != null && item.stackTagCompound.hasKey("type") && !item.stackTagCompound.getString("type").equals("Unknown");
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper == ItemRendererHelper.ENTITY_ROTATION;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        float rotation = -25;

        String headType = item.stackTagCompound.getString("type");

        if (location == null)
            location = new ResourceLocation("beheaded", String.format("%s/%s.png", prefix, headType));

        if (!location.getResourcePath().endsWith("/" + headType + ".png"))
            location = new ResourceLocation("beheaded", String.format("%s/%s.png", prefix, headType));

        Minecraft.getMinecraft().renderEngine.bindTexture(location);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        //GL11.glTranslatef(0 + 0.5F, 0, 0 + 0.5F);

        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
            GL11.glTranslatef(0.5F, 0.2F, 0F);

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
            GL11.glTranslatef(0.2F, 0.1F, 0F);

        if (type == ItemRenderType.INVENTORY) {
            GL11.glScalef(16F, 16F, 16F);
            GL11.glTranslatef(0.5F, 0.75F, 0F);
            GL11.glRotatef(150F, 1F, 0F, 0F);
            GL11.glRotatef(15F, 0F, 1F, 0F);
        }

        float f4 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        baseModel.render(null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, f4);
        GL11.glPopMatrix();
    }
}
