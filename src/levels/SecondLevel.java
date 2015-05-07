package levels;

import screens.items.ItemBox;
import screens.items.ItemBoxes;

import java.util.Random;
import java.util.Vector;

/**
 * User: SMironov Date: 19.04.2004 Time: 17:37:14
 *
 * @author Mediaspectrum, Inc.
 */
public class SecondLevel extends FirstLevel {
    public ItemBox go(ItemBoxes boxes) {
        Vector vt = LevelUtils.available(boxes, new DefaultAppender());

        if (vt.size() > 0) {
            Random r = new Random();
            int rand = Math.abs(r.nextInt());
            int delim = Integer.MAX_VALUE / vt.size();
            return (ItemBox) vt.elementAt(rand / delim);
        } else
            return null;
    }
}
