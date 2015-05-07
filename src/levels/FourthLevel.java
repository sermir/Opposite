package levels;

import screens.items.Coordinate;
import screens.items.ItemBox;
import screens.items.ItemBoxes;

import java.util.Vector;

/**
 * User: smironov
 * Date: 18.01.2007
 * Time: 12:03:12
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public class FourthLevel extends ThirdLevel {
	/**
	 * Static priority
	 * 5 2 4 3 3 4 2 5
	 * 2 0 1 1 1 1 0 2
	 * 4 1 4 2 2 4 1 4
	 * 3 1 2 x x 2 1 3
	 * 3 1 2 x x 2 1 3
	 * 4 1 4 2 2 4 1 4
	 * 2 0 1 1 1 1 0 2
	 * 5 2 4 3 3 4 2 5
	 */
	public FourthLevel() {
		super();
		Vector vt = (Vector) priority.get(new Integer(0));
		Vector second = (Vector) priority.get(new Integer(2));
		for (int i = 0; i < vt.size(); i++) {
			Coordinate ib = (Coordinate) vt.elementAt(i);
			int x = ib.x;
			int y = ib.y;
			if (!((x == 1 && y == 1) || (x == 6 && y == 6) || (x == 1 && y == 6) || (x == 6 && y == 1)))
				second.addElement(ib);
		}
	}

	public ItemBox go(ItemBoxes boxes) {
		checkState(boxes);
		ItemBox ib = super.go(boxes);
		if (ib == null) return null;

		int x = ib.x;
		int y = ib.y;

		//change priority вокруг углов
		Vector vt = (Vector) priority.get(new Integer(4));
		if (x == 0 && y == 0) {
			vt.addElement(new Coordinate(0, 1));
			vt.addElement(new Coordinate(1, 0));
			vt.addElement(new Coordinate(1, 1));
		}
		if (x == 0 && y == 7) {
			vt.addElement(new Coordinate(0, 6));
			vt.addElement(new Coordinate(1, 6));
			vt.addElement(new Coordinate(1, 7));
		}
		if (x == 7 && y == 0) {
			vt.addElement(new Coordinate(6, 1));
			vt.addElement(new Coordinate(6, 0));
			vt.addElement(new Coordinate(7, 1));
		}
		if (x == 7 && y == 7) {
			vt.addElement(new Coordinate(6, 6));
			vt.addElement(new Coordinate(6, 7));
			vt.addElement(new Coordinate(7, 6));
		}
		return ib;
	}

	protected void checkState(ItemBoxes boxes) {
		//проверяем стенки
		checkWall(boxes.getY(0));
		checkWall(boxes.getY(7));
		checkWall(boxes.getX(0));
		checkWall(boxes.getX(7));
		//проверяем вокруг углов и выставляем им статус
		if (boxes.boxes[0][1].curState == ItemBox.ZERO_STATE || boxes.boxes[0][6].curState == ItemBox.ZERO_STATE)
			check(boxes.getY(0));
		if (boxes.boxes[1][0].curState == ItemBox.ZERO_STATE || boxes.boxes[6][0].curState == ItemBox.ZERO_STATE)
			check(boxes.getX(0));
		if (boxes.boxes[1][7].curState == ItemBox.ZERO_STATE || boxes.boxes[6][7].curState == ItemBox.ZERO_STATE)
			check(boxes.getX(7));
		if (boxes.boxes[7][1].curState == ItemBox.ZERO_STATE || boxes.boxes[7][6].curState == ItemBox.ZERO_STATE)
			check(boxes.getY(7));
	}

	protected void checkWall(ItemBox[] bb) {
		Vector first = (Vector) priority.get(new Integer(1));
		Vector third = (Vector) priority.get(new Integer(3));
		Vector forth = (Vector) priority.get(new Integer(4));

		for (int i = 1; i < bb.length - 1; i++) {
			ItemBox ib = bb[i];
			if (ib.curState == ItemBox.BLACK_STATE) {
				for (int j = i + 2; j < bb.length; j++) {
					ItemBox itemBox = bb[j];
					if (itemBox.curState == ItemBox.ZERO_STATE) {
						changeStatusWall(bb[i - 1], first, third, forth);
					}
					if (itemBox.curState == ItemBox.BLACK_STATE) break;
				}

				for (int j = i - 2; j >= 0; j--) {
					ItemBox itemBox = bb[j];
					if (itemBox.curState == ItemBox.ZERO_STATE) {
						changeStatusWall(bb[i + 1], first, third, forth);
						break;
					}
					if (itemBox.curState == ItemBox.BLACK_STATE) break;
				}
			}
		}

		//увеличивает приоритет для тех укого рядом стоит черная точка
		for (int i = 0; i < bb.length; i++) {
			ItemBox ib = bb[i];
			if (ib.curState == ItemBox.WHITE_STATE) {
				//если сосед справа черный
				if (i < 5 && bb[i + 1].curState == ItemBox.BLACK_STATE) {
					for (int j = i + 1; j < bb.length; j++) {
						ItemBox itemBox = bb[j];
						if (itemBox.curState == ItemBox.ZERO_STATE) {
							forth.addElement(bb[j]);
							break;
						}
					}
				}
				//если сосед слева черный
				if (i > 2 && bb[i - 1].curState == ItemBox.BLACK_STATE) {
					for (int j = i - 1; j >= 0; j--) {
						ItemBox itemBox = bb[j];
						if (itemBox.curState == ItemBox.ZERO_STATE) {
							forth.addElement(bb[j]);
							break;
						}
					}
				}
			}
		}
	}

	protected void changeStatusWall(ItemBox ib, Vector first, Vector third, Vector forth) {
		if (ib.curState == ItemBox.ZERO_STATE) {
			boolean isRemove = third.removeElement(ib);
			isRemove = isRemove || forth.removeElement(ib);
			if (isRemove) first.addElement(ib);
		}
	}

	protected void check(ItemBox[] bb) {
		check(bb, false);
		check(bb, true);
	}

	protected void check(ItemBox[] bb, boolean isBackward) {
		Vector second = (Vector) priority.get(new Integer(2));
		Vector zero = (Vector) priority.get(new Integer(0));

		ItemBox ib = isBackward ? bb[7] : bb[0];
		if (ib.curState == ItemBox.ZERO_STATE) {
			boolean isDangeros = false;
			for (int i = isBackward ? 5 : 3; (isBackward && i >= 0) || (!isBackward && i < bb.length); i = isBackward ? i - 1 : i + 1)
			{
				ItemBox itemBox = bb[i];
				if (itemBox.curState == ItemBox.BLACK_STATE) {
					isDangeros = true;
					break;
				}
				if (itemBox.curState == ItemBox.ZERO_STATE) break;
			}
			if (isDangeros) {
				second.removeElement(bb[isBackward ? 6 : 1]);
				zero.addElement(bb[isBackward ? 6 : 1]);
			}
		}
	}
}
