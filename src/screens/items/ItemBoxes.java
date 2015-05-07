package screens.items;


/**
 * The class contains arrays of <link>screens.items.ItemBox</link> and implements some arrays methods and helper method
 * for getting <link>screens.items.ItemBox</link>
 */
public class ItemBoxes {
	public final ItemBox[][] boxes;

	public ItemBoxes() {
		this.boxes = new ItemBox[8][8];
	}

    /**
	 * return column of screens.items.ItemBox for given Y
     * @return X
     * @param y axis
	 */
	public ItemBox[] getX(int y) {
		ItemBox[] bb = new ItemBox[8];
		for (int i = 0; i < 8; i++)
			bb[i] = boxes[i][y];
		return bb;
	}

	/**
	 * return row of screens.items.ItemBox for given X
     * @return Y
     * @param x axis
     */
	public ItemBox[] getY(int x) {
		return boxes[x];
	}

	/**
	 * calculate number of black circles
     * @return count of black bullons
     */
	public int blackNumber() {
		return culculateNumber(false);
	}

	/**
	 * calculate number of white circles
     * @return count of white bullons
     */
	public int whiteNumber() {
		return culculateNumber(true);
	}

	private int culculateNumber(boolean isWhite) {
		int cc = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if ((isWhite && boxes[i][j].curState == ItemBox.WHITE_STATE) ||
						(!isWhite && boxes[i][j].curState == ItemBox.BLACK_STATE))
					cc++;
		return cc;
	}
}
