package unyuho.common.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import unyuho.common.gui.GuiComponentContainer;
import unyuho.guisample.SampleMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class GuiTabManager extends GuiScreen
{
	private static final ResourceLocation resource = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

	//タブの基本設定
	private static final int TAB_U = 28;
	private static final int TAB_WIDTH = 28;
	//選択してないやつ
	private static final int TAB_V = 0;
	private static final int TAB_HEIGHT = 28;
	//選択してるやつ
	private static final int CURRENTTAB_V = 32;
	private static final int CURRENTTAB_HEIGHT = 32;
	//選択してないやつの反転
	private static final int REVERSE_TAB_V = 64;
	//選択してるやつの反転
	private static final int REVERSE_CURRENTTAB_V = 96;

	//間隔
	private static final int OFFSET_X = 6;

	//タブ描写位置
	private int xPositionTab;
	private int yPositionTab;
	//描写対象のサイズ
	private int xGuiPosition;
	private int yGuiPosition;
	private int xSizeGui;
	private int ySizeGui;

	//GUI開くためのやつ
	private int guiID;
    private EntityPlayer player;
    private World world;
    private int x;
    private int y;
    private int z;

    private GuiComponentContainer currentGui;
    private Map<Integer, Integer> mapPosition;
    private Map<Integer, Icon> mapIcon;
    private Map<Integer, ResourceLocation> mapResourceLocation;

    public GuiTabManager(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        this.guiID = guiID;
        this.player = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        mapPosition = new HashMap<Integer, Integer>();
        mapIcon = new HashMap<Integer, Icon>();
        mapResourceLocation = new HashMap<Integer, ResourceLocation>();
    }

    /**
     * タブを追加(index順で並ぶのだ)
     * アイコンは描写されないゾ
     * @param guiID
     * @param index マイナスなら下なー
     * @param gui
     */
    public void addTabGui(int guiID, int tabIndex, GuiComponentContainer gui)
    {
    	addTabGui(guiID, tabIndex, gui, null, null);
    }

    /**
     * タブを追加(index順で並ぶのだ)
     * @param guiID
     * @param tabIndex	 マイナスなら下なー
     * @param gui
     * @param resource	どの画像なのだー？
     * @param icon		アイコンなー
     */
    public void addTabGui(int guiID, int tabIndex, GuiComponentContainer gui, ResourceLocation resource, Icon icon)
    {
    	mapPosition.put(guiID, tabIndex);
    	mapIcon.put(guiID, icon);
    	mapResourceLocation.put(guiID, resource);
    	if(this.guiID == guiID)
    	{
    		currentGui = gui;
    	}
    }



    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
		currentGui.drawScreen(mouseX, mouseY, par3);

		//タブの描写位置計算
		xPositionTab = currentGui.getGuiLeft() + OFFSET_X;
		yPositionTab = currentGui.getGuiTop() - TAB_HEIGHT;
		xGuiPosition = currentGui.getGuiLeft();
		yGuiPosition = currentGui.getGuiTop();
		xSizeGui = currentGui.getXSize();
		ySizeGui = currentGui.getYSize();

		//タブ描写
		drawAllTab();

		//アイコン描写
		drawAllTabIcon();

		//GUI切り替え確認
		checkTabClick(mouseX,mouseY);
    }

	/**
	 * みんな違って、みんな良い
	 * @param par3
	 * @param i
	 * @param j
	 */
	private void drawAllTab()
	{
		//タブ描写
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);

		RenderHelper.disableStandardItemLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Iterator<Integer> iterator = mapPosition.keySet().iterator();
		while(iterator.hasNext())
		{
			int guiID = iterator.next();
			int index = mapPosition.get(guiID);
			Icon icon = mapIcon.get(guiID);
			if(this.guiID == guiID)
			{
				zLevel = 1.0F;
				drawCurrentTab(index, icon);
			}
			else
			{
				zLevel = 0.0F;
				drawTab(index, icon);
			}
		}
		zLevel = 0.0F;

		RenderHelper.enableStandardItemLighting();
	}

	/**
	 * ウェイｗしてるタブ
	 * @param tabIndex
	 */
	private void drawCurrentTab(int tabIndex, Icon icon)
	{
		int xPosition = getTabPositionX(tabIndex);
		int yPosition = getTabPositionY(tabIndex);

		if(tabIndex > 0)
		{
			drawTexturedModalRect(xPosition, yPosition, TAB_U, CURRENTTAB_V, TAB_WIDTH, CURRENTTAB_HEIGHT);
		}
		else
		{
			//被せる分だけ上に変更
			yPosition -= (CURRENTTAB_HEIGHT - TAB_HEIGHT);
			drawTexturedModalRect(xPosition, yPosition, TAB_U, REVERSE_CURRENTTAB_V, TAB_WIDTH, CURRENTTAB_HEIGHT);
		}
	}

	/**
	 * ウェイｗしてないタブ
	 * @param tabIndex
	 */
	private void drawTab(int tabIndex, Icon icon)
	{
		int xPosition = getTabPositionX(tabIndex);
		int yPosition = getTabPositionY(tabIndex);

		if(tabIndex > 0)
		{
			drawTexturedModalRect(xPosition, yPosition, TAB_U, TAB_V, TAB_WIDTH, TAB_HEIGHT);
		}
		else
		{
			drawTexturedModalRect(xPosition, yPosition, TAB_U, REVERSE_TAB_V, TAB_WIDTH, TAB_HEIGHT);
		}
	}

	/**
	 * アイコンウェイｗ
	 */
	private void drawAllTabIcon()
	{
		RenderHelper.disableStandardItemLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		zLevel = 2.0F;

		Iterator<Integer> iterator = mapPosition.keySet().iterator();
		while(iterator.hasNext())
		{
			int guiID = iterator.next();
			int index = mapPosition.get(guiID);
			Icon icon = mapIcon.get(guiID);
			ResourceLocation resource = mapResourceLocation.get(guiID);
			if(icon != null && resource != null)
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
				drawIcon(index, icon);
			}
		}
		zLevel = 0.0F;

		RenderHelper.enableStandardItemLighting();
	}

	private void drawIcon(int tabIndex, Icon icon)
	{
		int xPosition = getIconPositionX(tabIndex);
		int yPosition = getIconPositionY(tabIndex);
		drawTexturedModelRectFromIcon(xPosition, yPosition, icon, icon.getIconWidth(), icon.getIconHeight());
	}


	/**
	 * タブ押してるか
	 * @param mouseX
	 * @param mouseY
	 */
	private void checkTabClick(int mouseX, int mouseY)
	{
		if(Mouse.isButtonDown(0))
		{
			Iterator<Integer> iterator = mapPosition.keySet().iterator();
			while(iterator.hasNext())
			{
				int guiID = iterator.next();
				int index = mapPosition.get(guiID);
				if(mouseOver(mouseX, mouseY, index))
				{
					player.openGui(SampleMod.instance, guiID, world, x, y, z);
					break;
				}
			}
		}
	}

	/**
	 * マウスがｵｯｽｵｯｽしてるか
	 * @param mouseX
	 * @param mouseY
	 * @param tabIndex
	 * @return
	 */
	private boolean mouseOver(int mouseX, int mouseY, int tabIndex)
	{
		int xPosition = getTabPositionX(tabIndex);
		int yPosition = getTabPositionY(tabIndex);
		if (mouseY < yPosition  || mouseY > yPosition + TAB_HEIGHT)
		{
			return false;
		}

		if (mouseX < xPosition || mouseX > xPosition + TAB_WIDTH)
		{
			return false;
		}
		return true;
	}


	/**
	 * タブの位置取得
	 * @param tabIndex
	 * @return
	 */
	private int getTabPositionX(int tabIndex)
	{
		return xGuiPosition + OFFSET_X + (OFFSET_X + TAB_WIDTH) * (Math.abs(tabIndex) - 1);
	}

	/**
	 * タブの位置取得
	 * @param tabIndex
	 * @return
	 */
	private int getTabPositionY(int tabIndex)
	{
		if(tabIndex > 0)
		{
			return yGuiPosition - TAB_HEIGHT;
		}
		else
		{
			return yGuiPosition + ySizeGui;
		}
	}

	/**
	 * アイコンの位置取得
	 * @param tabIndex
	 * @return
	 */
	private int getIconPositionX(int tabIndex)
	{
		return getTabPositionX(tabIndex) + 6;
	}

	/**
	 * アイコンの位置取得
	 * @param tabIndex
	 * @return
	 */
	private int getIconPositionY(int tabIndex)
	{
		int position = getTabPositionY(tabIndex);
		if(tabIndex > 0)
		{
			return position + 8;
		}
		else
		{
			return position + 4;
		}
	}



	//ここからはGUI各位たのむっつってる

    @Override
    public final boolean doesGuiPauseGame()
    {
    	return currentGui.doesGuiPauseGame();
    }

    @Override
    public final void handleInput()
    {
    	currentGui.handleInput();
    }

    @Override
    public final void handleKeyboardInput()
    {
    	currentGui.handleKeyboardInput();
    }

    @Override
    public final void handleMouseInput()
    {
    	currentGui.handleMouseInput();
    }

    @Override
    public final void updateScreen()
    {
    	currentGui.updateScreen();
    }

    @Override
    public final void setWorldAndResolution(Minecraft par1Minecraft, int width, int height)
    {
    	currentGui.setWorldAndResolution(par1Minecraft, width, height);
    }

    @Override
    public final void onGuiClosed()
    {
    	currentGui.onGuiClosed();
    }
}
