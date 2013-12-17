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

    //スクロールバー用
    /*
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new ContainerScrollSample(player.inventory, world);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        return new GuiScrollSample(player.inventory, world);
    }
    */


    //プログレスバー用
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new ContainerProgressSample(player.inventory, world);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        return new GuiProgressSample(player.inventory, world);
    }
}