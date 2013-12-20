package unyuho.guisample;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "SampleMod", name = "SampleMod", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SampleMod
{
	@SidedProxy(clientSide = "unyuho.guisample.client.ClientProxy", serverSide = "unyuho.guisample.CommonProxy")
	public static CommonProxy proxy;

    @Instance("SampleMod")
    public static SampleMod instance;

	public static int blockId = 2112;
	public static SampleBlock block = null;

	//Gui切り替え用
	public static int gui = 0;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());

		try
		{
			cfg.load();
			blockId = cfg.getBlock("blcokID", blockId).getInt();
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Massage");
		}
		finally
		{
			cfg.save();
		}

		block = new SampleBlock(blockId);
		GameRegistry.registerBlock(block, "guitest");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LanguageRegistry.addName(block, "Sample Block");
		LanguageRegistry.instance().addNameForObject(block, "ja_JP", "Sample Block");

		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	}
}
