package quantumstudios.honed.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import quantumstudios.honed.Honed;
import quantumstudios.honed.network.SPacketBase;
import quantumstudios.honed.te.TileEntityForgingAnvil;

public class SPacketMinigame extends SPacketBase {
    @Override
    public void handle(PacketBuffer data, EntityPlayer player) {
        BlockPos pos = BlockPos.fromLong(data.readLong());
        float score = data.readFloat();

        TileEntity te = player.world.getTileEntity(pos);
        if (te instanceof TileEntityForgingAnvil) {
            ((TileEntityForgingAnvil)te).acceptMinigameScore(score);
        } else {
            Honed.LOGGER.warn("Received score for invalid tile entity");
        }
    }

    public static FMLProxyPacket create(TileEntityForgingAnvil te, float score) {
        PacketBuffer data = buf(SPacketMinigame.class);

        data.writeLong(te.getPos().toLong());
        data.writeFloat(score);

        return build(data);
    }
}
