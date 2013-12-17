package unyuho.guisample.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import unyuho.common.gui.scrollbar.GuiScrollBarHorizontal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScrollSample extends GuiContainer
{
	private static final ResourceLocation recource = new ResourceLocation("unyuho","textures/gui/Sample.png");

	//スクロールバー(縦)
	//private GuiScrollBarVertical scrollBarSample;
	//スクロールバー(横)
	private GuiScrollBarHorizontal scrollBarSample;

    //container
    private ContainerScrollSample container;


    public GuiScrollSample(InventoryPlayer inventoryplayer, World world)
    {
        super(new ContainerScrollSample(inventoryplayer, world));

        this.ySize = 200;
        this.xSize = 175;

        container = (ContainerScrollSample)inventorySlots;
    }

    @Override
    public void initGui()
    {
    	super.initGui();

    	int scrollBarId = 0;
    	int xPosition = guiLeft + 46;
    	int yPosition = guiTop + 48;
    	int size = 104;
    	int minValue = 0;
    	int maxValue = 100;

    	//スクロールバー設定
    	scrollBarSample = new GuiScrollBarHorizontal(container, scrollBarId, xPosition, yPosition, size, minValue , maxValue);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	int value = scrollBarSample.getValue();
        fontRenderer.drawStringWithShadow("値 ", 40, 80, 0x404040);
        fontRenderer.drawString(Integer.toString(value), 60, 80, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //メイン画像
        mc.getTextureManager().bindTexture(recource);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);


        //スクロールバーの描写
        scrollBarSample.drawScrollBar();
    }


    @Override
    public void handleMouseInput()
    {
    	//スクロール
        super.handleMouseInput();

        //ホイールを回した時の移動処理
        if(scrollBarSample.mouseOver())
        {
        	scrollBarSample.scrollTo();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
    	//クリックした場合の移動処理
        boolean flag = Mouse.isButtonDown(0);
        if(scrollBarSample.mouseOver(mouseX, mouseY))
        {
        	scrollBarSample.scrollTo(flag, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, par3);
    }
}
