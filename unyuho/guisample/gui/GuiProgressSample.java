package unyuho.guisample.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import unyuho.common.gui.progressbar.GuiProgressBar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiProgressSample extends GuiContainer
{
	private static final ResourceLocation recource = new ResourceLocation("unyuho","textures/gui/Sample.png");

	private GuiProgressBar progressBarSample;

    //container
    private ContainerProgressSample container;


    public GuiProgressSample(InventoryPlayer inventoryplayer, World world)
    {
        super(new ContainerProgressSample(inventoryplayer, world));

        this.ySize = 200;
        this.xSize = 175;

        container = (ContainerProgressSample)inventorySlots;
    }

    @Override
    public void initGui()
    {
    	super.initGui();

    	int scrollBarId = 0;
    	int xPosition = guiLeft + 46;
    	int yPosition = guiTop + 48;
    	int size = 50;
    	int minValue = 0;
    	int maxValue = 100;

    	//スクロールバー設定
    	progressBarSample = new GuiProgressBar(container, scrollBarId, xPosition, yPosition, size);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //メイン画像
        mc.getTextureManager().bindTexture(recource);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //スクロールバーの描写
        progressBarSample.drawScrollBar();
    }
}
