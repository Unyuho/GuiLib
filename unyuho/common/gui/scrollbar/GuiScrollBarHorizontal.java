package unyuho.common.gui.scrollbar;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiScrollBarHorizontal extends GuiScrollBar
{
    //横用スクロールhorizontal
    private final static int HORIZONTAL_U_BAR = 16;
    private final static int HORIZONTAL_U_BAR_END = 120;
    private final static int HORIZONTAL_V_BAR = 0;
    private final static int HORIZONTAL_BAR_HEIGHT = 16;
	//横用スライダー
    private final static int HORIZONTAL_U_SLIDER = 123;
    private final static int HORIZONTAL_V_SLIDER = 0;
    private final static int HORIZONTAL_SLIDER_HEIGHT = 14;
    private final static int HORIZONTAL_SLIDER_WIDTH = 16;

    //スクロールバーのサイズ
    private int scrollBarSize;

    public GuiScrollBarHorizontal(IScrollable container, int id, int xPosition, int yPosition)
    {
        this(container, id, xPosition, yPosition, 20);
    }

    public GuiScrollBarHorizontal(IScrollable container, int id, int xPosition, int yPositon, int scrollBarSize)
    {
    	this(container, id, xPosition, yPositon, scrollBarSize, 0, scrollBarSize);
    }

    public GuiScrollBarHorizontal(IScrollable container, int id, int xPosition, int yPositon, int scrollBarSize, int minValue, int maxValue)
    {
    	super(container, id, xPosition, yPositon, scrollBarSize, HORIZONTAL_BAR_HEIGHT, minValue, maxValue);

    	this.scrollBarSize = scrollBarSize;

    	//スクロールバーの最大値設定
    	setScrollSizeX(scrollBarSize - HORIZONTAL_SLIDER_WIDTH - 1);

    	//スクロールバーの表示設定
    	setScrollBar(HORIZONTAL_U_BAR, HORIZONTAL_V_BAR, HORIZONTAL_U_BAR_END, HORIZONTAL_V_BAR, scrollBarSize - 1, HORIZONTAL_BAR_HEIGHT, 1, HORIZONTAL_BAR_HEIGHT);

    	//スライダーの表示設定
    	setSlider(HORIZONTAL_U_SLIDER, HORIZONTAL_V_SLIDER, HORIZONTAL_SLIDER_WIDTH, HORIZONTAL_SLIDER_HEIGHT);
    }

	@Override
	protected float repositionSlider(float sliderX, float sliderY)
	{
		float currentScroll =  sliderX - ((float)HORIZONTAL_SLIDER_WIDTH / 2.0F);
		float maxScroll =  (float)scrollBarSize - (float)HORIZONTAL_SLIDER_WIDTH;

		return currentScroll / maxScroll;
	}

}
