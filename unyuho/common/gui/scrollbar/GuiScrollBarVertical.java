package unyuho.common.gui.scrollbar;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiScrollBarVertical extends GuiScrollBar
{
    //縦用スクロール
    private final static int VERTICAL_U_BAR = 0;
    private final static int VERTICAL_V_BAR = 0;
    private final static int VERTICAL_V_BAR_END = 104;
    private final static int VERTICAL_BAR_WIDTH = 16;
	//縦用スライダー
    private final static int VERTICAL_U_SLIDER = 0;
    private final static int VERTICAL_V_SLIDER = 106;
    private final static int VERTICAL_SLIDER_HEIGHT = 16;
    private final static int VERTICAL_SLIDER_WIDTH = 14;

    //スクロールバーのサイズ
    private int scrollBarSize;

    public GuiScrollBarVertical(IScrollable container, int id, int xPosition, int yPosition)
    {
        this(container, id, xPosition, yPosition, 20);
    }

    public GuiScrollBarVertical(IScrollable container, int id, int xPosition, int yPositon, int scrollBarSize)
    {
    	this(container, id, xPosition, yPositon, scrollBarSize, 0, scrollBarSize);
    }

    public GuiScrollBarVertical(IScrollable container, int id, int xPosition, int yPositon, int scrollBarSize, int minValue, int maxValue)
    {
    	super(container, id, xPosition, yPositon, VERTICAL_BAR_WIDTH, scrollBarSize, minValue, maxValue);

    	this.scrollBarSize = scrollBarSize;

    	//スクロールバーの最大値設定
    	setScrollSizeY(scrollBarSize - VERTICAL_SLIDER_HEIGHT - 1);

    	//スクロールバーの表示設定
    	setScrollBar(VERTICAL_U_BAR, VERTICAL_V_BAR, VERTICAL_U_BAR, VERTICAL_V_BAR_END, VERTICAL_BAR_WIDTH, scrollBarSize - 1, VERTICAL_BAR_WIDTH, 1);

    	//スライダーの表示設定
    	setSlider(VERTICAL_U_SLIDER, VERTICAL_V_SLIDER, VERTICAL_SLIDER_WIDTH, VERTICAL_SLIDER_HEIGHT);
    }

	@Override
	protected float repositionSlider(float sliderX, float sliderY)
	{
		float currentScroll =  sliderY - ((float)VERTICAL_SLIDER_HEIGHT / 2.0F);
		float maxScroll =  (float)scrollBarSize - (float)VERTICAL_SLIDER_HEIGHT;

		return currentScroll / maxScroll;
	}

}
