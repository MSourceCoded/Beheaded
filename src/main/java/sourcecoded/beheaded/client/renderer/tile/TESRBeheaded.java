package sourcecoded.beheaded.client.renderer.tile;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sourcecoded.beheaded.tile.TileBeheaded;

public class TESRBeheaded extends TileEntitySpecialRenderer {

    public ResourceLocation location;
    public static String prefix = "textures/model/head";
    public static ModelSkeletonHead baseModel = new ModelSkeletonHead(0, 0, 64, 32);

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float PTT) {
        TileBeheaded tile = (TileBeheaded) te;

        float xF = (float)x;
        float yF = (float)y;
        float zF = (float)z;

        float rotation = (tile.rotation * 360F) / 16F;

        if (location == null)
            location = new ResourceLocation("beheaded", String.format("%s/%s.png", prefix, tile.headType));

        if (!location.getResourcePath().endsWith("/" + tile.headType + ".png"))
            location = new ResourceLocation("beheaded", String.format("%s/%s.png", prefix, tile.headType));

        this.bindTexture(location);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        int meta = tile.getBlockMetadata() & 7;

        if (meta != 1) {
            switch (meta) {
                case 2:
                    GL11.glTranslatef(xF + 0.5F, yF + 0.25F, zF + 0.74F);
                    break;
                case 3:
                    GL11.glTranslatef(xF + 0.5F, yF + 0.25F, zF + 0.26F);
                    rotation = 180.0F;
                    break;
                case 4:
                    GL11.glTranslatef(xF + 0.74F, yF + 0.25F, zF + 0.5F);
                    rotation = 270.0F;
                    break;
                case 5:
                default:
                    GL11.glTranslatef(xF + 0.26F, yF + 0.25F, zF + 0.5F);
                    rotation = 90.0F;
            }
        } else
            GL11.glTranslatef(xF + 0.5F, yF, zF + 0.5F);

        float f4 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        baseModel.render(null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, f4);
        GL11.glPopMatrix();

    }

}