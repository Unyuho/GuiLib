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

    	//スクロールバー初期設定
    	scrollBarList = new ArrayList<GuiScrollBar>();

    	//プログレスバー初期設定
    	progressBarList = new ArrayList<GuiProgressBar>();
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
    	//クリックした場合の移動処理
    	boolean flag = Mouse.isButtonDown(0);
    	if(flag)
    	{
    		if(currentScrollBar == null)
    		{
    			for(GuiScrollBar scrollBar : scrollBarList)
    			{
    				if( scrollBar.mouseOver() )
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
    		currentScrollBar.scrollTo(flag);
    	}
    	else
    	{
    		//ホイールを回した時の移動処理
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
}
