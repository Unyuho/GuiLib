package unyuho.common.gui.packet;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "ComponentHelper", name = "ComponentHelper", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"onPacket"}, packetHandler = ComponentPacketHandler.class)
public class ComponentHelper
{
	@Instance("ComponentHelper")
	public static ComponentHelper instance;
}
