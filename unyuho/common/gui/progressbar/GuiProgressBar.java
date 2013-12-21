package unyuho.common.gui.progressbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import unyuho.common.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiProgressBar extends GuiBase
{
	private static final ResourceLocation resource = new ResourceLocation("unyuho","textures/gui/component/Component.png");

	//左端
    private final static int U_MIN = 0;
	//右端
    private final static int U_MAX = 136;

	//色塗り部分
    private final static int V_INCREMENT = 240;
	//デフォルト部分
    private final static int V_DEFAULT = 224;

    //高さ
    private final static int BAR_HEIGHT = 16;


    private IProgressBar container;

    //使わないと思うけど、識別ID
    private int id;

    //無効にしたりとか
    private boolean enabled;

    //描写フラグ
    private boolean drawBar;

    //進行方向
    private EnumVector vector;

    //プログレスバーの表示開始位置
    private int progressbarU;

    //プログレスバーのサイズ
    private int width;

    public GuiProgressBar(IProgressBar container, int progressID, int xPosition, int yPosition)
    {
        this(container, progressID, xPosition, yPosition, 50, EnumVector.RIGHT);
    }

    public GuiProgressBar(IProgressBar container, int progressID, int xPosition, int yPosition, int width, EnumVector vector)
    {
    	super(xPosition, yPosition);

    	this.container = container;
        this.id = progressID;
        this.width = width;
        this.vector = vector;
        this.enabled = true;
        this.drawBar = true;
        zLevel = 100.0F;

        this.progressbarU = U_MAX - width;
    }


    /**
     * プログレスバーの描写
     */
    public void drawScrollBar()
    {
        if (this.drawBar)
        {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            float currentPosition = getCurrentPosition();
            int currentWidth = (int)Math.floor(width * currentPosition);

            if(currentPosition > 0.0F)
            {
                //プログレスバー描写(色塗り部分)
                drawTexturedModalRect(xPosition, yPosition, progressbarU, V_INCREMENT, currentWidth, BAR_HEIGHT, 0);
            }

            if(currentPosition < 1.0F)
            {
                //プログレスバー描写(デフォルト部分)
                drawTexturedModalRect(xPosition, yPosition, progressbarU , V_DEFAULT, width - currentWidth, BAR_HEIGHT, currentWidth);
            }
        }
    }

    public void drawTexturedModalRect(int xPosition, int yPosition, int uPosition, int vPosition, int width, int height, int translate)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        if(EnumVector.RIGHT == vector)
        {
            //右方向へ
            xPosition += translate;
            uPosition += translate;
            double minX = xPosition;
            double maxX = xPosition + width;
            double minY = yPosition;
            double maxY = yPosition + height;
            double minU = uPosition * f;
            double maxU = (uPosition + width) * f;
            double minV = vPosition * f1;
            double maxV = (vPosition + height) * f1;

            tessellator.addVertexWithUV(minX, maxY, (double)zLevel, minU, maxV);
            tessellator.addVertexWithUV(maxX, maxY, (double)zLevel, maxU, maxV);
            tessellator.addVertexWithUV(maxX, minY, (double)zLevel, maxU, minV);
            tessellator.addVertexWithUV(minX, minY, (double)zLevel, minU, minV);

        }
        else if(EnumVector.LEFT == vector)
        {
            xPosition += this.width;
            xPosition -= translate;
            uPosition += translate;
            double minX = xPosition - width;
            double maxX = xPosition;
            double minY = yPosition - height;
            double maxY = yPosition;
            double minU = uPosition * f;
            double maxU = (uPosition + width) * f;
            double minV = vPosition * f1;
            double maxV = (vPosition + height) * f1;

            tessellator.addVertexWithUV(minX, maxY, (double)zLevel, maxU, maxV);
            tessellator.addVertexWithUV(maxX, maxY, (double)zLevel, minU, maxV);
            tessellator.addVertexWithUV(maxX, minY, (double)zLevel, minU, minV);
            tessellator.addVertexWithUV(minX, minY, (double)zLevel, maxU, minV);

        }
        else if(EnumVector.UP == vector)
        {
            yPosition += this.width;
            yPosition -= translate;
            uPosition += translate;
            double minX = xPosition - height;
            double maxX = xPosition;
            double minY = yPosition - width;
            double maxY = yPosition;
            double minU = uPosition * f;
            double maxU = (uPosition + width) * f;
            double minV = vPosition * f1;
            double maxV = (vPosition + height) * f1;

            tessellator.addVertexWithUV(minX, maxY, (double)zLevel, minU, maxV);
            tessellator.addVertexWithUV(maxX, maxY, (double)zLevel, minU, minV);
            tessellator.addVertexWithUV(maxX, minY, (double)zLevel, maxU, minV);
            tessellator.addVertexWithUV(minX, minY, (double)zLevel, maxU, maxV);
        }
        else
        {
            yPosition += translate;
            uPosition += translate;
            double minX = xPosition;
            double maxX = xPosition + height;
            double minY = yPosition;
            double maxY = yPosition + width;
            double minU = uPosition * f;
            double maxU = (uPosition + width) * f;
            double minV = vPosition * f1;
            double maxV = (vPosition + height) * f1;

            tessellator.addVertexWithUV(minX, maxY, (double)zLevel, maxU, maxV);
            tessellator.addVertexWithUV(maxX, maxY, (double)zLevel, maxU, minV);
            tessellator.addVertexWithUV(maxX, minY, (double)zLevel, minU, minV);
            tessellator.addVertexWithUV(minX, minY, (double)zLevel, minU, maxV);
        }

        tessellator.draw();

    }

    //位置計算
    private float getCurrentPosition()
    {
    	int currentValue = container.increment(id);
    	int maxValue = container.getMaximum(id);
    	int minValue = container.getMinimum(id);

    	float currentPosition = (float)currentValue / (float)(maxValue - minValue);
    	if(currentPosition > 1.0F)
    	{
    		currentPosition = 1.0F;
    	}
    	if(currentPosition < 0.0F)
    	{
    		currentPosition = 0.0F;
    	}

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
