package unyuho.guisample.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import unyuho.common.gui.progressbar.IProgressBar;
import unyuho.common.gui.scrollbar.IScrollable;

public class ContainerProgressSample extends Container implements IProgressBar
{
	private int count;
    private int value;

    public ContainerProgressSample(InventoryPlayer inventoryplayer,World world)
    {
    	int slotIndex = 0;

        for (int cnt = 0; cnt < 9; cnt++)
        {
            addSlotToContainer(new Slot(inventoryplayer, slotIndex++, 8 + cnt * 18, 176));
        }

        for (int cntY = 0; cntY < 3; cntY++)
        {
            for (int cntX = 0; cntX < 9; cntX++)
            {
                addSlotToContainer(new Slot(inventoryplayer, slotIndex++, 8 + cntX * 18, 118 + cntY * 18));
            }
        }

        count = 0;
        value = 0;
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
    	//他のユーザーのスロットも更新する場合
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    /**
     * 2tick毎に1増えるだけ
     * @return
     */
    public int getValue()
    {
    	count = (count == Integer.MAX_VALUE ? 0 : count+1);
    	if(count % 2 == 0)
    	{
    		value = (value == getMaximum() ? getMinimum() : value+1);
    	}

    	return value;
    }

	@Override
	public int increment(int progressID)
	{
		//進捗状況的なアレ
		int value = getValue();
		return value;
	}

	@Override
	public int getMinimum()
	{
		return 0;
	}

	@Override
	public int getMaximum()
	{
		return 100;
	}
}
