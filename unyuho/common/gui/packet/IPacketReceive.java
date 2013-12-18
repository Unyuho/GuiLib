package unyuho.common.gui.packet;

import java.io.DataOutputStream;

import com.google.common.io.ByteArrayDataInput;

/**
 * Container辺りにウェイｗしてくれたのむ
 * @author Unyuho
 *
 */
public interface IPacketReceive
{
	/**
	 * パケット送信時に呼び出される
	 * 必要なデータを書き込んでくれたのむ
	 * @param dos
	 */
    public abstract void writePacketData(DataOutputStream dos);

	/**
	 * パケット受信時に呼び出される
	 * 必要なデータを読み込んでくれたのむ
	 * @param data
	 */
    public abstract void onPacketData(ByteArrayDataInput data);
}
