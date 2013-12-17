package unyuho.common.gui.progressbar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiProgressBar extends Gui
{
	private static final ResourceLocation resouce = new ResourceLocation("unyuho","textures/gui/progressbar/ProgressBar.png");

    private IProgressBar container;

    //描写位置
    private int xPosition;
    private int yPosition;

    //プログレスバーのサイズ
    private int width;

    //使わないと思うけど、識別ID
    private int id;

    //無効にしたりとか
    private boolean enabled;

    //描写フラグ
    private boolean drawBar;

    //プログレスバーの表示開始位置
    private int progressbarU;

	//棒の部分(X座標)
    private final static int U_BAR = 0;
	//矢印部分の右端
    private final static int U_ARROW = 72;

	//色塗り部分
    private final static int V_INCREMENT = 16;
	//デフォルト部分
    private final static int V_DEFAULT = 0;

    private final static int BAR_HEIGHT = 16;



    public GuiProgressBar(IProgressBar container, int progressID, int xPosition, int yPosition)
    {
        this(container, progressID, xPosition, yPosition, 50);
    }

    public GuiProgressBar(IProgressBar container, int progressID, int xPosition, int yPosition, int width)
    {
        this.id = progressID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.container = container;
        this.enabled = true;
        this.drawBar = true;

        this.progressbarU = U_ARROW - width;
    }


    /**
     * プログレスバーの描写
     */
    public void drawScrollBar()
    {
        if (this.drawBar)
        {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(resouce);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            float currentPosition = getCurrentPosition();
            int currentWidth = (int)Math.floor(width * currentPosition);

            if(currentPosition > 0.0F)
            {
                //プログレスバー描写(色塗り部分)
                drawTexturedModalRect(xPosition, yPosition, progressbarU, V_INCREMENT, currentWidth, BAR_HEIGHT);
            }

            if(currentPosition < 1.0F)
            {
                //プログレスバー描写(デフォルト部分)
                drawTexturedModalRect(xPosition + currentWidth, yPosition, progressbarU + currentWidth , V_DEFAULT, width - currentWidth, BAR_HEIGHT);
            }
        }
    }

    //位置計算
    private float getCurrentPosition()
    {
    	int currentValue = container.increment(id);
    	int maxValue = container.getMaximum();
    	int minValue = container.getMinimum();

    	float currentPosition = (float)currentValue / (float)(maxValue - minValue);
    	if(currentPosition > 1.0F)
    	{
    		currentPosition = 1.0F;
    	}
    	if(currentPosition < 0.0F)
    	{
    		currentPosition = 0.0F;
    	}

    	System.out.println(currentPosition);

    	return currentPosition;
    }

    public void setEnabled(boolean enabled)
    {
    	this.enabled = enabled;
    }

    public int getID()
    {
    	return id;
    }
}
