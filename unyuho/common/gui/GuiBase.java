package unyuho.common.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiBase extends Gui
{
    //描写位置
    protected int xPosition;
    protected int yPosition;

    public GuiBase(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * initGuiを許すな
     * @param container
     */
    public final void initGui(GuiBase gui)
    {
        this.xPosition = gui.xPosition;
        this.yPosition = gui.yPosition;
    }
}
