package unyuho.guisample.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import unyuho.common.gui.GuiComponentContainer;
import unyuho.common.gui.progressbar.EnumVector;
import unyuho.common.gui.progressbar.GuiProgressBar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiProgressSample extends GuiComponentContainer
{
	private static final ResourceLocation recource = new ResourceLocation("unyuho","textures/gui/Sample.png");

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

    	//スクロールバー設定(右へ進む)
    	GuiProgressBar progressBarSample = new GuiProgressBar(container, scrollBarId, xPosition, yPosition, size, EnumVector.RIGHT);

    	addProgressBar(progressBarSample);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	//superたのむ
    	super.drawGuiContainerBackgroundLayer(f, i, j);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //メイン画像
        mc.getTextureManager().bindTexture(recource);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
