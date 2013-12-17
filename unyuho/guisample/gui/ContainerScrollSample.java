package unyuho.guisample.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import unyuho.common.gui.scrollbar.IScrollable;

public class ContainerScrollSample extends Container implements IScrollable
{
    private int value;

    public ContainerScrollSample(InventoryPlayer inventoryplayer,World world)
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
     * スクロール時のイベント(クライアントのみ)
     * scrollID : スクロールID
     * value	: 現在の値
     */
    @Override
	public void scrollPerformed(int scrollID, int value)
    {
    	this.value = value;

    	//任意で同期処理など
	}

    public int getValue()
    {
    	return value;
    }
}
