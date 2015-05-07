package levels;

import screens.items.ItemBox;

import java.util.Vector;

/**
 * User: smironov
 * Date: 18.01.2007
 * Time: 16:35:51
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public class DefaultAppender implements ItemAppender {
    public boolean validate(int countKill, ItemBox ib, Vector vt) {
        if (countKill > 0)
            vt.addElement(ib);
        return true;
    }
}
