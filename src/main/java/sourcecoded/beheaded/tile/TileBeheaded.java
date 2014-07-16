package sourcecoded.beheaded.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileBeheaded extends TileEntity {

    public int rotation;
    public String headType = "";

    @Override
    public void writeToNBT(NBTTagCompound tags) {
        super.writeToNBT(tags);
        tags.setByte("rot", (byte)(rotation & 255));
        tags.setString("headType", headType);
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        super.readFromNBT(tags);
        rotation = tags.getByte("rot");
        headType = tags.getString("headType");
    }


    public void setData(int rotation, String headType) {
        this.rotation = rotation;
        this.headType = headType;
        this.markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tags = new NBTTagCompound();
        writeToNBT(tags);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tags);
    }

    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
        markDirty();
    }

}
