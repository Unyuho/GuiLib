package unyuho.guisample.gui;

import java.io.DataOutputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import unyuho.common.gui.packet.ComponentPacketHandler;
import unyuho.common.gui.packet.IPacketReceive;
import unyuho.common.gui.scrollbar.IScrollable;

public class ContainerScrollSample extends Container implements IScrollable, IPacketReceive
{
    private int valueHorizontal;
    private int valueVertical;

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
}
