package levels;

import screens.items.ItemBox;

import java.util.Vector;

/**
 * User: smironov
 * Date: 18.01.2007
 * Time: 16:24:18
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public interface ItemAppender {
    public boolean validate(int countKill, ItemBox ib, Vector vt);
}
