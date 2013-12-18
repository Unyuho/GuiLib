package unyuho.common.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Mouse;

import unyuho.common.gui.progressbar.GuiProgressBar;
import unyuho.common.gui.scrollbar.GuiScrollBar;
import unyuho.guisample.gui.ContainerScrollSample;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiComponentContainer extends GuiContainer
{
	//スクロールバー
	private GuiScrollBar currentScrollBar;
	private List<GuiScrollBar> scrollBarList;

	//プログレスバー
	private List<GuiProgressBar> progressBarList;

    //container
    private ContainerScrollSample container;


    public GuiComponentContainer(Container container)
    {
        super(container);
    }

    /**
     * スクロールバーの追加
     * @param scrollBar
     */
    public void addScrollBar(GuiScrollBar scrollBar)
    {
    	scrollBarList.add(scrollBar);
    }

    /**
     * プログレスバーの追加
     * @param progressBar
     */
    public void addProgressBar(GuiProgressBar progressBar)
    {
    	progressBarList.add(progressBar);
    }

    @Override
    public void initGui()
    {
    	super.initGui();

    	//スクロールバー初期設定
    	scrollBarList = new ArrayList<GuiScrollBar>();

    	//プログレスバー初期設定
    	progressBarList = new ArrayList<GuiProgressBar>();
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
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

    	//スクロールバー
    	//ホイールを回した時の移動処理
    	if(currentScrollBar == null)
    	{
    		for(GuiScrollBar scrollBar : scrollBarList)
    		{
    			if(scrollBar.mouseOver())
    			{
    				scrollBar.scrollTo();
    				break;
    			}
    		}
    	}
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
    	//スクロールバー
    	//クリックした場合の移動処理
    	boolean flag = Mouse.isButtonDown(0);
    	if(flag)
    	{
    		if(currentScrollBar == null)
    		{
    			for(GuiScrollBar scrollBar : scrollBarList)
    			{
    				if( scrollBar.mouseOver(mouseX, mouseY) )
    				{
    					currentScrollBar = scrollBar;
    					break;
    				}
    			}
    		}
    	}
    	else
    	{
    		currentScrollBar = null;
    	}

    	if(currentScrollBar != null)
    	{
    		currentScrollBar.scrollTo(flag, mouseX, mouseY);
    	}

    	super.drawScreen(mouseX, mouseY, par3);
    }
}
