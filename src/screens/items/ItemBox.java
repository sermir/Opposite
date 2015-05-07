package screens.items;

import javax.microedition.lcdui.Graphics;

/**
 * The class implements grid cell on board in the game Date: 02.03.2004 Time: 17:37:42
 * @author Serguey Mironov
 */
public class ItemBox extends Coordinate {
	public int curState;
	private final int cellW;
    private final int cellH;
	public static final int BLACK_STATE = 1;
	public static final int WHITE_STATE = 2;
	public static final int ZERO_STATE = 0;

	public ItemBox(int x, int y, int state, int cellW, int cellH) {
		this.x = x;
		this.y = y;
		this.curState = state;
		this.cellW = cellW;
		this.cellH = cellH;
	}

	public void paint(Graphics g) {
		int px = x * cellW;
		int py = y * cellH;
		g.drawRect(px, py, cellW, cellH);

		int lenght = Math.min(cellW - 4, cellH - 4);
		switch (curState) {
		case ZERO_STATE://blank cell
			break;
		case BLACK_STATE://call with black circle
			g.fillArc(px + 2, py + 2, lenght, lenght, 0, 360);
			break;
		case WHITE_STATE://call with white cirlce
			g.drawArc(px + 2, py + 2, lenght, lenght, 0, 360);
			break;
		}
	}

    public String toString() {
        return new StringBuffer("ItemBox{").
				append("curState=").append(curState).
				append(", cellW=").append(cellW).
				append(", cellH=").append(cellH).
				append('}').toString();
    }
}
