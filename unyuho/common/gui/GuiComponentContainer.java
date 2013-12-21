package unyuho.common.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import unyuho.common.gui.grid.ContainerGrid;
import unyuho.common.gui.progressbar.GuiProgressBar;
import unyuho.common.gui.scrollbar.GuiScrollBar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiComponentContainer extends GuiContainer
{
	private static final ResourceLocation resource = new ResourceLocation("unyuho","textures/gui/component/Component.png");

	//スクロールバー
	private GuiScrollBar currentScrollBar;
	private List<GuiScrollBar> scrollBarList;

	//プログレスバー
	private List<GuiProgressBar> progressBarList;

	private ContainerGrid container;

    public GuiComponentContainer(ContainerGrid container)
    {
        super(container);

        this.container = container;

    	//スクロールバー初期設定
    	scrollBarList = new ArrayList<GuiScrollBar>();

    	//プログレスバー初期設定
    	progressBarList = new ArrayList<GuiProgressBar>();
    }

	public final int getGuiLeft()
	{
		return guiLeft;
	}

	public final int getGuiTop()
	{
		return guiTop;
	}

	public final int getXSize()
	{
		return xSize;
	}

	public final int getYSize()
	{
		return ySize;
	}

    /**
     * スクロールバーの追加
     * @param scrollBar
     */
    public final void addScrollBar(GuiScrollBar addScrollBar)
    {
    	for(GuiScrollBar scrollBar : scrollBarList)
    	{
    		if(scrollBar.getID() == addScrollBar.getID())
    		{
    			scrollBar.initGui(addScrollBar);
    			return;
    		}
    	}

    	scrollBarList.add(addScrollBar);
    }

    /**
     * スクロールバー用 getter
     * @param scrollID
     * @return
     */
    public final int getScrollValue(int scrollID)
    {
    	for(GuiScrollBar scrollBar : scrollBarList)
    	{
    		if(scrollID == scrollBar.getID())
    		{
    			return scrollBar.getValue();
    		}
    	}

    	return 0;
    }

    /**
     * スクロールバー用 setter
     * @param scrollID
     * @param value
     */
    public final void setScrollValue(int scrollID, int value)
    {
    	for(GuiScrollBar scrollBar : scrollBarList)
    	{
    		if(scrollID == scrollBar.getID())
    		{
    			scrollBar.setValue(value);
    			break;
    		}
    	}
    }

    /**
     * プログレスバーの追加
     * @param progressBar
     */
    public final void addProgressBar(GuiProgressBar addProgressBar)
    {
    	for(GuiProgressBar progressBar : progressBarList)
    	{
    		if(progressBar.getID() == addProgressBar.getID())
    		{
    			progressBar.initGui(addProgressBar);
    			return;
    		}
    	}
    	progressBarList.add(addProgressBar);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	//スロットの描写
    	List<Slot> slotList = container.getDrawList();
    	if(!slotList.isEmpty())
    	{
        	Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GL11.glTranslatef(guiLeft, guiTop, 0);

            zLevel = 1.0F;
    		for(Slot slot : slotList)
    		{
    			drawTexturedModalRect(slot.xDisplayPosition, slot.yDisplayPosition, 224, 96, container.SLOT_SIZE, container.SLOT_SIZE);
    		}
    		zLevel = 0.0F;

    		GL11.glPopMatrix();
    	}

    	//スクロールバーの描写
		for(GuiScrollBar scrollBar : scrollBarList)
		{
			scrollBar.drawScrollBar();
		}


		//プログレスバーの描写
		for(GuiProgressBar progressBar : progressBarList)
		{
			progressBar.drawScrollBar();
		}
    }

    @Override
    public void handleMouseInput()
    {
		super.handleMouseInput();

		//スクロールバー判定
		GuiScrollBar tempScroll = getCurrentScrollBar();

		boolean flg = Mouse.isButtonDown(0);
		if(flg)
		{
			if(currentScrollBar == null && tempScroll != null)
			{
				currentScrollBar = tempScroll;
    		}

			//クリックした場合の移動処理
			if(currentScrollBar != null)
			{
	    		currentScrollBar.scrollTo(flg);
	    	}
		}
		else
		{
			currentScrollBar = null;

			//ホイールを回した時の移動処理
			if(tempScroll != null)
	    	{
	    		tempScroll.scrollTo();
	    	}
		}
    }

    /**
     *
     * @return
     */
    private GuiScrollBar getCurrentScrollBar()
    {
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();
		int mouseX = Mouse.getX() * width / mc.displayWidth;
		int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

		for(GuiScrollBar scrollBar : scrollBarList)
		{
			if( scrollBar.mouseOver(mouseX, mouseY) )
			{
				return scrollBar;
			}
		}
		return null;
    }
}
