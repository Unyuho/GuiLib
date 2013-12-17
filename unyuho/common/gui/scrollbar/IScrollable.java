package unyuho.common.gui.scrollbar;

/**
 * Container辺りにウェイｗしてくれたのむ
 * @author Unyuho
 *
 */
public interface IScrollable
{
	/**
	 * スクロール時にウェイｗする感じでたのむ
	 * @param scrollID
	 * @param value
	 */
    public abstract void scrollPerformed(int scrollID, int value);
}
