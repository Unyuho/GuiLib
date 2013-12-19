package unyuho.guisample;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class SampleBlock extends Block
{
    public SampleBlock(int i)
    {
        super(i, Block.planks.blockMaterial);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public Icon getIcon(int par1, int par2)
    {
    	return Block.cloth.getIcon(0, 0);
    }
    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3,int par4, EntityPlayer par5EntityPlayer, int par6, float par7,float par8, float par9)
    {
    	par5EntityPlayer.openGui(SampleMod.instance, SampleMod.gui, par1World, par2, par3, par4);
    	return true;
    }
}