package unyuho.common.gui.progressbar;

/**
 * Container辺りにウェイｗしてくれたのむ
 * @author Unyuho
 *
 */
public interface IProgressBar
{
	/**
	 * プログレスバーの現在位置を変更するってワケ
	 * @param progressID 複数のプログレスバーをウェイｗする時は分岐してくれっつってる
	 * @return 現在位置
	 */
    public abstract int increment(int progressID);

    /**
     * 範囲の最小値
     * @param minValue 0とか返してちょ
     */
    public abstract int getMinimum(int progressID);

    /**
     * 範囲の最大値
     * @param maxValue 100とか返してちょ
     */
    public abstract int getMaximum(int progressID);
}
