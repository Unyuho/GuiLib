package unyuho.guisample.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import unyuho.common.gui.GuiComponentContainer;
import unyuho.common.gui.scrollbar.GuiScrollBarHorizontal;
import unyuho.common.gui.scrollbar.GuiScrollBarVertical;
import unyuho.guisample.SampleMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScrollSample extends GuiComponentContainer
{
	private static final ResourceLocation recource = new ResourceLocation("unyuho","textures/gui/Sample.png");

    //container
    private ContainerScrollSample container;

    private EntityPlayer entityplayer;
    private World world;
    private int x;
    private int y;
    private int z;

    public GuiScrollSample(InventoryPlayer inventoryplayer, World world, EntityPlayer entityplayer, int x, int y, int z)
    {
        super(new ContainerScrollSample(inventoryplayer, world));

        this.entityplayer = entityplayer;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        this.ySize = 200;
        this.xSize = 175;

        container = (ContainerScrollSample)inventorySlots;
    }

    @Override
    public void initGui()
    {
    	super.initGui();

    	//スクロールバー設定(横)
    	int scrollBarId = EnumKey.SAMPLESCROLLHORIZONTAL.ordinal();
    	int xPosition = guiLeft + 10;
    	int yPosition = guiTop + 10;
    	int size = 100;
    	int minValue = 0;
    	int maxValue = 100;
    	GuiScrollBarHorizontal scrollBarSampleHorizontal = new GuiScrollBarHorizontal(container, scrollBarId, xPosition, yPosition, size, minValue , maxValue);

    	//必須
    	addScrollBar(scrollBarSampleHorizontal);


    	//スクロールバー設定(縦)
    	scrollBarId = EnumKey.SAMPLESCROLLVERTICAL.ordinal();
    	xPosition = guiLeft + 10;
    	yPosition = guiTop + 30;
    	size = 50;
    	minValue = 0;
    	maxValue = 50;
    	GuiScrollBarVertical scrollBarSampleVertical = new GuiScrollBarVertical(container, scrollBarId, xPosition, yPosition, size, minValue , maxValue);

    	//必須
    	addScrollBar(scrollBarSampleVertical);


    	buttonList.add(new GuiButton(1, guiLeft - 50 , guiTop + 20 , 50 , 20, "progress"));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	int value = getScrollValue( EnumKey.SAMPLESCROLLHORIZONTAL.ordinal() );
        fontRenderer.drawString(Integer.toString(value), 115, 13, 0x404040);

    	value = getScrollValue( EnumKey.SAMPLESCROLLVERTICAL.ordinal() );
        fontRenderer.drawString(Integer.toString(value), 12, 85, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	//たのむ
    	super.drawGuiContainerBackgroundLayer(f, i, j);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //メイン画像
        mc.getTextureManager().bindTexture(recource);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	super.actionPerformed(par1GuiButton);

    	entityplayer.openGui(SampleMod.instance, 1, world, x, y, z);
    }
}
