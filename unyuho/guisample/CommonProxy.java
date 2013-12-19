package unyuho.guisample;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import unyuho.guisample.gui.ContainerScrollSample;
import unyuho.guisample.gui.GuiScrollSample;
import unyuho.guisample.gui.ContainerProgressSample;
import unyuho.guisample.gui.GuiProgressSample;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    public World getClientWorld()
    {
        return null;
    }



    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	if(ID == 0)
    	{
    		 //スクロールバー用
    		 return new ContainerScrollSample(player.inventory, world);
    	}
    	else
    	{
    		//プログレスバー用
    		 return new ContainerProgressSample(player.inventory, world);
    	}

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	if(ID == 0)
    	{
    		 //スクロールバー用
    		return new GuiScrollSample(player.inventory, world);
    	}
    	else
    	{
    		//プログレスバー用
            return new GuiProgressSample(player.inventory, world);
    	}
    }
}