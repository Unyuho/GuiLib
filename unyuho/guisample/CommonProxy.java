package unyuho.guisample;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import unyuho.common.gui.GuiTabManager;
import unyuho.guisample.gui.ContainerProgressSample;
import unyuho.guisample.gui.ContainerScrollSample;
import unyuho.guisample.gui.GuiProgressSample;
import unyuho.guisample.gui.GuiScrollSample;
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
    	if(ID == 0 || ID == 2)
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
    	//タブなのだー
    	//開き直す方式なので、そこら辺たのむっつってる

    	GuiTabManager guiTab = new GuiTabManager(ID, player, world,x, y, z);

        //タブ1番目
        int id = 0;
        int tabIndex = 1;
        ResourceLocation resource = TextureMap.locationBlocksTexture;
        Icon icon = Block.plantRed.getIcon(0, 0);

        guiTab.addTabGui(id, tabIndex, new GuiScrollSample(player.inventory, world), resource, icon);

        //下のタブ1番目
        id = 2;
        tabIndex = -1;
        resource = TextureMap.locationItemsTexture;
        icon = Item.bow.getIconFromDamage(0);

        guiTab.addTabGui(id, tabIndex, new GuiScrollSample(player.inventory, world), resource, icon);



        //タブ2番目
        id = 1;
        tabIndex = 2;

        guiTab.addTabGui(id, tabIndex, new GuiProgressSample(player.inventory, world));

        //下のタブ2番目
        id = 3;
        tabIndex = -2;
        resource = TextureMap.locationItemsTexture;
        icon = Item.appleRed.getIconFromDamage(0);

        guiTab.addTabGui(id, tabIndex, new GuiProgressSample(player.inventory, world), resource, icon);


    	return guiTab;
    }
}