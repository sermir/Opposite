package util;

import screens.items.ItemBox;
import screens.items.ItemBoxes;

/**
 * User: SMironov Date: 19.04.2004 Time: 16:21:01
 * @author Mediaspectrum, Inc.
 */
public class FireAhead {
	public static boolean fire(ItemBoxes boxes, int x, int y, boolean isUser) {
		boolean success;
		//check x
		success = go(true, x, boxes.getX(y), isUser);
		success = success | go(false, x, boxes.getX(y), isUser);

		//check y
		success = success | go(true, y, boxes.getY(x), isUser);
		success = success | go(false, y, boxes.getY(x), isUser);

		success = success | goXY(boxes, true, true, x, y, isUser);
		success = success | goXY(boxes, true, false, x, y, isUser);
		success = success | goXY(boxes, false, true, x, y, isUser);
		success = success | goXY(boxes, false, false, x, y, isUser);
		return success;
	}

	private static boolean go(boolean rightDirection, int initPos, ItemBox[] box, boolean isUser) {
		boolean success = false;
		int i;

		int value = isUser ? ItemBox.BLACK_STATE : ItemBox.WHITE_STATE;
		int checkValue = isUser ? ItemBox.WHITE_STATE : ItemBox.BLACK_STATE;

		if (rightDirection)
			for (i = initPos + 1; i < 7 && box[i].curState == checkValue; i++) {
			}
		else
			for (i = initPos - 1; i > 0 && box[i].curState == checkValue; i--) {
			}

		if ((i > initPos + 1 && box[i].curState == value && rightDirection) ||
				(i < initPos - 1 && box[i].curState == value && !rightDirection)) {
			success = true;
			if (rightDirection)
				for (i = initPos + 1; i < 8 && box[i].curState == checkValue; i++)
					box[i].curState = value;
			else
				for (i = initPos - 1; i >= 0 && box[i].curState == checkValue; i--)
					box[i].curState = value;
		}
		return success;
	}

	private static boolean goXY(ItemBoxes boxes, boolean xUpDirection, boolean yUpDirection, int x, int y, boolean isUser) {
		boolean success = false;
		int i;

		int value = isUser ? ItemBox.BLACK_STATE : ItemBox.WHITE_STATE;
		int checkValue = isUser ? ItemBox.WHITE_STATE : ItemBox.BLACK_STATE;

		if (xUpDirection && yUpDirection)
			for (i = 1; x + i < 7 && y + i < 7 && boxes.boxes[x + i][y + i].curState == checkValue; i++) {
			}
		else if (xUpDirection && !yUpDirection)
			for (i = 1; x + i < 7 && y - i > 0 && boxes.boxes[x + i][y - i].curState == checkValue; i++) {
			}
		else if (!xUpDirection && yUpDirection)
			for (i = 1; x - i > 0 && y + i < 7 && boxes.boxes[x - i][y + i].curState == checkValue; i++) {
			}
		else
			for (i = 1; x - i > 0 && y - i > 0 && boxes.boxes[x - i][y - i].curState == checkValue; i++) {
			}

		if (i > 1 && ((xUpDirection && yUpDirection && boxes.boxes[x + i][y + i].curState == value) ||
				(xUpDirection && !yUpDirection && boxes.boxes[x + i][y - i].curState == value) ||
				(!xUpDirection && yUpDirection && boxes.boxes[x - i][y + i].curState == value) ||
				(!xUpDirection && !yUpDirection && boxes.boxes[x - i][y - i].curState == value)
				)) {
			success = true;
			if (xUpDirection && yUpDirection)
				for (i = 1; x + i < 8 && y + i < 8 && boxes.boxes[x + i][y + i].curState == checkValue; i++)
					boxes.boxes[x + i][y + i].curState = value;
			else if (xUpDirection && !yUpDirection)
				for (i = 1; x + i < 8 && y - i >= 0 && boxes.boxes[x + i][y - i].curState == checkValue; i++)
					boxes.boxes[x + i][y - i].curState = value;
			else if (!xUpDirection && yUpDirection)
				for (i = 1; x - i >= 0 && y + i < 8 && boxes.boxes[x - i][y + i].curState == checkValue; i++)
					boxes.boxes[x - i][y + i].curState = value;
			else
				for (i = 1; x - i >= 0 && y - i >= 0 && boxes.boxes[x - i][y - i].curState == checkValue; i++)
					boxes.boxes[x - i][y - i].curState = value;
		}
		return success;
	}
}
