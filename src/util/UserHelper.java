package util;

import screens.items.ItemBox;
import screens.items.ItemBoxes;

/**
 * User: SMironov Date: 19.04.2004 Time: 14:29:11
 * @author Mediaspectrum, Inc.
 */
public class UserHelper {
	public static ItemBox help(ItemBoxes boxes) {
		//search maximum kill
		int maxX = 0;
		int maxY = 0;

		int maxKill = 0;
		int curKill;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boxes.boxes[i][j].curState != ItemBox.ZERO_STATE) continue;

				curKill = countKill(true, i, boxes.getX(j));
				curKill += countKill(false, i, boxes.getX(j));

				curKill += countKill(true, j, boxes.getY(i));
				curKill += countKill(false, j, boxes.getY(i));

				curKill += countKillXY(true, true, i, j, boxes);
				curKill += countKillXY(true, false, i, j, boxes);
				curKill += countKillXY(false, true, i, j, boxes);
				curKill += countKillXY(false, false, i, j, boxes);

				if (curKill > maxKill) {
					maxKill = curKill;
					maxX = i;
					maxY = j;
				}
			}
		}

		if (maxKill > 0)
			return boxes.boxes[maxX][maxY];
		else
			return null;
	}

	private static int countKill(boolean isIncrease, int initPos, ItemBox[] box) {
		int counter = 0, i;

		if (isIncrease)
			for (i = initPos + 1; i < 7 && box[i].curState == ItemBox.WHITE_STATE; i++) counter++;
		else
			for (i = initPos - 1; i > 0 && box[i].curState == ItemBox.WHITE_STATE; i--) counter++;

		if ((i > initPos + 1 && box[i].curState == ItemBox.BLACK_STATE && isIncrease) ||
				(i < initPos - 1 && box[i].curState == ItemBox.BLACK_STATE && !isIncrease))
			return counter;
		return 0;
	}

	private static int countKillXY(boolean xUpDirection, boolean yUpDirection, int x, int y, ItemBoxes iBoxes) {
		int counter = 0, i;

		if (xUpDirection && yUpDirection)
			for (i = 1; x + i < 7 && y + i < 7 && iBoxes.boxes[x + i][y + i].curState == ItemBox.WHITE_STATE; i++) counter++;
		else if (xUpDirection && !yUpDirection)
			for (i = 1; x + i < 7 && y - i > 0 && iBoxes.boxes[x + i][y - i].curState == ItemBox.WHITE_STATE; i++) counter++;

		else if (!xUpDirection && yUpDirection)
			for (i = 1; x - i > 0 && y + i < 7 && iBoxes.boxes[x - i][y + i].curState == ItemBox.WHITE_STATE; i++) counter++;

		else
			for (i = 1; x - i > 0 && y - i > 0 && iBoxes.boxes[x - i][y - i].curState == ItemBox.WHITE_STATE; i++) counter++;

		if (i > 1 && ((xUpDirection && yUpDirection && iBoxes.boxes[x + i][y + i].curState == ItemBox.BLACK_STATE) ||
				(xUpDirection && !yUpDirection && iBoxes.boxes[x + i][y - i].curState == ItemBox.BLACK_STATE) ||
				(!xUpDirection && yUpDirection && iBoxes.boxes[x - i][y + i].curState == ItemBox.BLACK_STATE) ||
				(!xUpDirection && !yUpDirection && iBoxes.boxes[x - i][y - i].curState == ItemBox.BLACK_STATE)
				))
			return counter;

		return 0;
	}
}
