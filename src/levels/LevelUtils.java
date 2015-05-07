package levels;

import screens.items.ItemBox;
import screens.items.ItemBoxes;

import java.util.Vector;

/**
 * User: smironov
 * Date: 18.01.2007
 * Time: 16:01:15
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public final class LevelUtils {
    public static Vector available(ItemBoxes boxes, ItemAppender ia) {
        int curKill;
        Vector vt = new Vector(60);
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

                if (!ia.validate(curKill, boxes.boxes[i][j], vt)) return vt;
            }
        }
        return vt;
    }

    private static int countKill(boolean isIncrease, int initPos, ItemBox[] box) {
        int counter = 0, i;

        if (isIncrease)
            for (i = initPos + 1; i < 7 && box[i].curState == ItemBox.BLACK_STATE; i++) counter++;
        else
            for (i = initPos - 1; i > 0 && box[i].curState == ItemBox.BLACK_STATE; i--) counter++;

        if ((i > initPos + 1 && box[i].curState == ItemBox.WHITE_STATE && isIncrease) ||
                (i < initPos - 1 && box[i].curState == ItemBox.WHITE_STATE && !isIncrease))
            return counter;
        return 0;
    }

    private static int countKillXY(boolean xUpDirection, boolean yUpDirection, int x, int y, ItemBoxes iBoxes) {
        int counter = 0, i;

        if (xUpDirection && yUpDirection)
            for (i = 1; x + i < 7 && y + i < 7 && iBoxes.boxes[x + i][y + i].curState == ItemBox.BLACK_STATE; i++)
                counter++;
        else if (xUpDirection && !yUpDirection)
            for (i = 1; x + i < 7 && y - i > 0 && iBoxes.boxes[x + i][y - i].curState == ItemBox.BLACK_STATE; i++)
                counter++;

        else if (!xUpDirection && yUpDirection)
            for (i = 1; x - i > 0 && y + i < 7 && iBoxes.boxes[x - i][y + i].curState == ItemBox.BLACK_STATE; i++)
                counter++;

        else
            for (i = 1; x - i > 0 && y - i > 0 && iBoxes.boxes[x - i][y - i].curState == ItemBox.BLACK_STATE; i++)
                counter++;

        if (i > 1 && ((xUpDirection && yUpDirection && iBoxes.boxes[x + i][y + i].curState == ItemBox.WHITE_STATE) ||
                (xUpDirection && !yUpDirection && iBoxes.boxes[x + i][y - i].curState == ItemBox.WHITE_STATE) ||
                (!xUpDirection && yUpDirection && iBoxes.boxes[x - i][y + i].curState == ItemBox.WHITE_STATE) ||
                (!xUpDirection && !yUpDirection && iBoxes.boxes[x - i][y - i].curState == ItemBox.WHITE_STATE)
        ))
            return counter;

        return 0;
    }
}
