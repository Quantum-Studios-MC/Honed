package quantumstudios.honed.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import quantumstudios.honed.Tags;

//Serverbound packet
public abstract class SPacketBase {
    public String name() {
        return this.getClass().getSimpleName();
    }

    public abstract void handle(PacketBuffer data, EntityPlayer player);

    protected static PacketBuffer buf(Class<? extends SPacketBase> clazz) {
        return HonedNetworkManager.buf(clazz);
    }
    protected static FMLProxyPacket build(PacketBuffer buf) {
        return new FMLProxyPacket(buf, Tags.MOD_ID);
    }
}
