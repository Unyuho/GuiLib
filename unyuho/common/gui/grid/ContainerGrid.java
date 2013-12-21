package unyuho.common.gui.grid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ContainerGrid extends Container
{
	public static final int SLOT_SIZE = 18;
	private final int INVENTORY_SPACE = 4;
	private final int INVENTORY_WIDTH = 9;

	private int hotbarMin = -1;
	private int hotbarMax = -1;
	private int inventoryMin = -1;
	private int inventoryMax = -1;

	private List<Slot> drawList;

	public ContainerGrid()
	{
		this.drawList = new ArrayList<Slot>();
	}

	/**
	 * プレイヤーのインベントリウェイｗするやつ
	 * 一応、インベントリの数変更するﾓｯﾖにも対応したい感じ
	 * @param inventoryplayer
	 * @param xPosition
	 * @param yPosition
	 */
	protected final void addPlayerInventoryGrid(InventoryPlayer inventoryplayer, int xPosition, int yPosition)
	{
		if(inventoryplayer.mainInventory == null)
		{
			return;
		}

		//使用可能なほう
		int hotbarSize = inventoryplayer.getHotbarSize();
		//わいわいしてる方
		int inventorySize = inventoryplayer.mainInventory.length - hotbarSize;

		/*
		 * わいわいしてる方
		 */
		int inventoryRow = (inventorySize / INVENTORY_WIDTH);

		/*
		 * 使用可能なほう
		 */
		int hotbarRow = (hotbarSize / INVENTORY_WIDTH);
		int ySlotPosition = yPosition + (SLOT_SIZE * inventoryRow) + INVENTORY_SPACE;


		//使用可能なほう
		hotbarMin = getNextIndex();
		hotbarMax = addInventoryGrid(inventoryplayer, xPosition, ySlotPosition, hotbarRow, INVENTORY_WIDTH);

		//わいわいしてる方
		inventoryMin = getNextIndex();
		inventoryMax = addInventoryGrid(inventoryplayer, xPosition, yPosition, inventoryRow, INVENTORY_WIDTH);
	}

	/**
	 * n行m列のSlot追加ウェイｗ
	 * 独自のSlotウェイｗしたい？なんのこったよ？(すっとぼけ)
	 * @param inventoryplayer
	 * @param xPosition
	 * @param yPosition
	 * @param sizeRow
	 * @param sizeColumn
	 * @return	slotNumber
	 */
	protected int addInventoryGrid(InventoryPlayer inventoryplayer, int xPosition, int yPosition, int sizeRow, int sizeColumn)
	{
		int slotNumber = getNextIndex();

		for (int cntRow = 0; cntRow < sizeRow ; cntRow++)
		{
			for (int cntColumn = 0; cntColumn < sizeColumn ; cntColumn++)
			{
				int xSlotPosition = xPosition + cntColumn * SLOT_SIZE;
				int ySlotPosition = yPosition + cntRow * SLOT_SIZE;

				Slot slot = new Slot(inventoryplayer, getNextIndex(), xSlotPosition, ySlotPosition);
				addSlotToContainer(slot);

				slotNumber = slot.slotNumber;

				addDrawList(slot);
			}
		}
		return slotNumber;
	}

	/**
	 * 該当位置にスロットの画像を表示したければ追加して、どうぞ
	 * @param slot
	 */
	public void addDrawList(Slot slot)
	{
		this.drawList.add(slot);
	}

	/**
	 * Slotは闇
	 * @return
	 */
	protected final int getNextIndex()
	{
		return this.inventorySlots.size();
	}

	/**
	 * 描写するslot取得
	 * @return
	 */
	public final List<Slot> getDrawList()
	{
		return drawList;
	}

	/**
	 * Shiftクリック時のやつ、たのむ
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotnumber)
	{
		if(hotbarMin == -1)
		{
			return null;
		}

		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotnumber);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotnumber >= hotbarMin && slotnumber <= hotbarMax)
			{
				if (!this.mergeItemStack(itemstack1, inventoryMin, inventoryMax, false))
				{
					return null;
				}
			}
			else if (slotnumber >= inventoryMin && slotnumber <= inventoryMax)
			{
				if (!this.mergeItemStack(itemstack1, hotbarMin, hotbarMax, false))
				{
					return null;
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onSlotChanged();
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
    }
}
