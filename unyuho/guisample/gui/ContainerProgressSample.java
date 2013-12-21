package unyuho.guisample.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import unyuho.common.gui.grid.ContainerGrid;
import unyuho.common.gui.progressbar.IProgressBar;

public class ContainerProgressSample extends ContainerGrid implements IProgressBar
{
	private int count;
    private int value;

    public ContainerProgressSample(InventoryPlayer inventoryplayer,World world)
    {
		int xPosition = 8;
		int yPosition = 118;

		addPlayerInventoryGrid(inventoryplayer, xPosition, yPosition);




        count = 0;
        value = 0;
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
    		value = (value == getMaximum(0) ? getMinimum(0) : value+1);
    	}

    	return value;
    }


	//進捗状況的なアレ
	@Override
	public int increment(int progressID)
	{
		//複数使うならIDで分岐たのむっつってる
		if(progressID == EnumKey.SAMPLEPROGRESS.ordinal())
		{
			int value = getValue();
			return value;
		}

		return 0;
	}

	@Override
	public int getMinimum(int progressID)
	{
		//IDは無視してもOK
		return 0;
	}

	@Override
	public int getMaximum(int progressID)
	{
		//IDは無視してもOK
		return 100;
	}
}
