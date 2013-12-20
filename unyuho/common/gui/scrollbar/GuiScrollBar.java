package unyuho.common.gui.scrollbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import unyuho.common.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiScrollBar extends GuiBase
{
	private static final ResourceLocation resouce = new ResourceLocation("unyuho","textures/gui/scrollbar/ScrollBar.png");

    private IScrollable container;

    //スクロールバーのサイズ
    private int width;
    private int height;

    //使わないと思うけど、識別ID
    private int id;

    //無効にしたりとか
    private boolean enabled;

    //描写フラグ
    private boolean drawBar;

    //クリックフラグ
    private boolean wasClicking;

    //スクロールフラグ
    private boolean isScrolling;

    //現在のスライダーの位置
    private float currentScroll;

    private int minValue;
    private int maxValue;

    //スクロールバーの表示関係
    private int scrollBarU;
    private int scrollBarV;
    private int scrollBarWidth;
    private int scrollBarHeight;

    private int scrollBarEndU;
    private int scrollBarEndV;
    private int scrollBarWidthEnd;
    private int scrollBarHeightEnd;

    private int offsetX;
    private int offsetY;


    //スライダーの表示関係
    private int sliderU;
    private int sliderV;
    private int sliderHeight;
    private int sliderWidth;

    //スライダーの最大移動位置
    private int scrollSizeX;
    private int scrollSizeY;

    public GuiScrollBar(IScrollable mainGui, int scrollID, int xPosition, int yPosition)
    {
        this(mainGui, scrollID, xPosition, yPosition, 200, 20);
    }

    public GuiScrollBar(IScrollable mainGui, int scrollID, int xPosition, int yPosition, int width, int height)
    {
    	this(mainGui, scrollID, xPosition, xPosition, width, height, 0, height);
    }

    public GuiScrollBar(IScrollable mainGui, int scrollID, int xPosition, int yPosition, int width, int height, int minValue, int maxValue)
    {
    	super(xPosition, yPosition);

        this.id = scrollID;
        this.width = width;
        this.height = height;
        this.container = mainGui;
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.enabled = true;
        this.drawBar = true;
        this.wasClicking = false;
        this.isScrolling = false;
        this.currentScroll = 0.0F;
        this.scrollSizeY = 0;
        this.scrollSizeY = 0;
        this.offsetX = 0;
        this.offsetY = 0;

        zLevel = 100.0F;
    }

    /**
     * スライダーの最大位置
     * @param scrollSizeX
     */
    protected void setScrollSizeX(int scrollSizeX)
    {
    	this.scrollSizeX = scrollSizeX;
    	this.offsetX = width - 1;
    }

    /**
     * スライダーの最大位置
     * @param scrollSizeX
     */
    protected void setScrollSizeY(int scrollSizeY)
    {
    	this.scrollSizeY = scrollSizeY;
    	this.offsetY = height - 1;
    }

    /**
     * スクロールバーの表示設定
     * @param scrollBarU
     * @param scrollBarV
     * @param scrollBarEndU
     * @param scrollBarEndV
     * @param scrollBarHeight
     * @param scrollBarWidth
     */
    protected void setScrollBar(int scrollBarU, int scrollBarV, int scrollBarEndU, int scrollBarEndV, int scrollBarWidth, int scrollBarHeight, int scrollBarWidthEnd, int scrollBarHeightEnd)
    {
        //スクロールバーの表示関係
        this.scrollBarU = scrollBarU;
        this.scrollBarV = scrollBarV;
        this.scrollBarWidth = scrollBarWidth;
        this.scrollBarHeight = scrollBarHeight;

        this.scrollBarEndU = scrollBarEndU;
        this.scrollBarEndV = scrollBarEndV;
        this.scrollBarHeightEnd = scrollBarHeightEnd;
        this.scrollBarWidthEnd = scrollBarWidthEnd;
    }

    /**
     * スライダーの表示設定
     * @param sliderU
     * @param sliderV
     * @param sliderWidth
     * @param sliderHeight
     */
    protected void setSlider(int sliderU, int sliderV, int sliderWidth, int sliderHeight)
    {
        //スクロールバーの表示関係
        this.sliderU = sliderU;
        this.sliderV = sliderV;
        this.sliderWidth = sliderWidth;
        this.sliderHeight = sliderHeight;
    }

    /**
     * スクロールバーの描写
     */
    public void drawScrollBar()
    {
        if (this.drawBar)
        {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(resouce);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            //スクロールバー描写
            this.drawTexturedModalRect(xPosition, yPosition, scrollBarU, scrollBarV, scrollBarWidth, scrollBarHeight);
            //スクロールバー終了部分
            this.drawTexturedModalRect(xPosition + offsetX, yPosition + offsetY, scrollBarEndU, scrollBarEndV, scrollBarWidthEnd, scrollBarHeightEnd);

            //スライダー部分
            this.drawTexturedModalRect(xPosition + Math.round(currentScroll * scrollSizeX), yPosition + Math.round(currentScroll * scrollSizeY), sliderU, sliderV, sliderWidth, sliderHeight);

        }
    }


    /**
     * スクロールバーの描写範囲内にカーソルが存在するか
     */
    public boolean mouseOver(int mouseX, int mouseY)
    {
        if (mouseX < xPosition || mouseX > xPosition + width)
        {
        	return false;
        }

        if (mouseY < yPosition || mouseY > yPosition + height)
        {
        	return false;
        }

    	return true;
    }

    /**
     * スクロールバーの描写範囲内にカーソルが存在するか
     */
    public boolean mouseOver()
    {
    	Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        int mouseX = Mouse.getX() * width / mc.displayWidth;
        int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

        return mouseOver(mouseX, mouseY);
    }


    /**
     * スライダー位置の変更
     */
    abstract protected float repositionSlider(float sliderX, float sliderY);


    /**
     * スクロールバーの描写範囲内にカーソルが存在するか
     */
    public void scrollTo(boolean bClick)
    {
    	Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        int mouseX = Mouse.getX() * width / mc.displayWidth;
        int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

        scrollTo(bClick, mouseX, mouseY);
    }


    /**
     * スクロール処理
     */
    public void scrollTo(boolean bClick, int mouseX, int mouseY)
    {
    	if(enabled)
    	{
	    	if(!wasClicking && bClick)
	    	{
	    		isScrolling = true;
	    	}

	        if (!bClick)
	        {
	            isScrolling = false;
	        }

	        wasClicking = bClick;

	        if (isScrolling)
	        {
	        	float newScroll = repositionSlider((float)(mouseX - xPosition), (float)(mouseY - yPosition));

	        	changeScroll(newScroll);
	        }
    	}
    }

    /**
     * スクロール処理
     */
    public void scrollTo()
    {
    	if(enabled)
    	{
	    	int scroll = Mouse.getEventDWheel();

	        if (scroll != 0)
	        {
	            if (scroll > 0)
	            {
	            	scroll = 1;
	            }

	            if (scroll < 0)
	            {
	            	scroll = -1;
	            }

	            int length = maxValue - minValue;

	            float newScroll = (float)((double)currentScroll - (double)scroll / (double)length);

	            changeScroll(newScroll);
	        }
    	}
    }

    private void changeScroll(float newScroll)
    {
        if (newScroll < 0.0F)
        {
        	newScroll = 0.0F;
        }

        if (newScroll > 1.0F)
        {
        	newScroll = 1.0F;
        }

        if(newScroll != currentScroll)
        {
        	currentScroll = newScroll;
        	container.scrollPerformed(id, getValue());
        }
    }

    public void setEnabled(boolean enabled)
    {
    	this.enabled = enabled;
    }

    public int getID()
    {
    	return id;
    }

    /**
     * 現在の値を返す
     * @return
     */
    public int getValue()
    {
    	int length = maxValue - minValue;

    	return Math.round(length * currentScroll) + minValue;
    }


    public void setValue(int value)
    {
    	float length = maxValue - minValue;
    	currentScroll = (float)(value - minValue) / length;
    }
}
