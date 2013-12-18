package unyuho.common.gui.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ComponentPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
    	if(packet.channel.equals("onPacket"))
    	{
    		onPacketDataReceive(manager, packet, player);
    	}
    }

    /**
     * クライアントからのパケット受信時
     * @param manager
     * @param packet
     * @param player
     */
    private void onPacketDataReceive(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
    	ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);

		EntityPlayerMP entityplayer = (EntityPlayerMP)player;
		Container container = entityplayer.openContainer;
		if(container instanceof IPacketReceive)
		{
			IPacketReceive containerPacket = (IPacketReceive)container;
			containerPacket.onPacketData(data);
		}
    }


    /**
     * クライアントからサーバーへパケット送信
     * @param container
     * @return
     */
    public static void sentPacketToServer(IPacketReceive container)
    {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		container.writePacketData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
		packet.channel = "onPacket";

		FMLClientHandler.instance().sendPacket(packet);
    }

}
