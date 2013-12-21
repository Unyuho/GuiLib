package unyuho.guisample.gui;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import unyuho.common.gui.grid.ContainerGrid;
import unyuho.common.gui.packet.ComponentPacketHandler;
import unyuho.common.gui.packet.IPacketReceive;
import unyuho.common.gui.scrollbar.IScrollable;

import com.google.common.io.ByteArrayDataInput;

public class ContainerScrollSample extends ContainerGrid implements IScrollable, IPacketReceive
{
    private int valueHorizontal;
    private int valueVertical;

    public ContainerScrollSample(InventoryPlayer inventoryplayer,World world)
    {
		int xPosition = 8;
		int yPosition = 118;

		addPlayerInventoryGrid(inventoryplayer, xPosition, yPosition);

		addInventoryGrid(inventoryplayer, 50, 30, 4, 1);

        valueHorizontal = 0;
        valueVertical = 0;
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
    	if(scrollID == EnumKey.SAMPLESCROLLHORIZONTAL.ordinal())
    	{
    		valueHorizontal = value;
    	}
    	else if(scrollID == EnumKey.SAMPLESCROLLVERTICAL.ordinal())
    	{
    		valueVertical = value;
    	}

    	//任意で同期処理など
    	ComponentPacketHandler.sentPacketToServer(this);
	}

    //クライアント
	@Override
	public void writePacketData(DataOutputStream dos)
	{
		try{
			dos.writeInt(valueHorizontal);
			dos.writeInt(valueVertical);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	//サーバー
	@Override
	public void onPacketData(ByteArrayDataInput data)
	{
		valueHorizontal = data.readInt();
		valueVertical = data.readInt();

		System.out.println("valueVertical : " + valueVertical + " / valueHorizontal : " + valueHorizontal);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer,int slotnumber)
	{
		super.transferStackInSlot(par1EntityPlayer, slotnumber);
		return null;
	}
}
