package levels;

import screens.items.ItemBox;
import screens.items.ItemBoxes;
import util.PhoneStep;

import java.util.Vector;

class MaxAppender implements ItemAppender {
    int max;

    public boolean validate(int countKill, ItemBox ib, Vector vt) {
        if (countKill > max) {
            max = countKill;
            vt.addElement(ib);
        }
        return true;
    }
}

/**
 * User: SMironov Date: 19.04.2004 Time: 13:27:59
 * @author Mediaspectrum, Inc.
 */
public class FirstLevel implements PhoneStep {

	public ItemBox go(ItemBoxes boxes) {
        Vector vt = LevelUtils.available(boxes, new MaxAppender());
		if (vt.size() > 0)
			return (ItemBox) vt.firstElement();
		return null;
    }

}
