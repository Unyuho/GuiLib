package unyuho.common.gui.grid;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotDrawFrame extends Slot
{
	private boolean draw;
    public SlotDrawFrame(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
    {
    	super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
    	draw = true;
    }


    public void setDisplay(boolean draw)
    {
    	this.draw = draw;
    }

    public boolean isDraw()
    {
    	return draw;
    }
}
