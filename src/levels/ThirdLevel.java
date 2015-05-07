package levels;

import screens.items.Coordinate;
import screens.items.ItemBox;
import screens.items.ItemBoxes;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

class ThirdAppender implements ItemAppender {
    int max;
    Vector av;

    public ThirdAppender(Vector av) {
        this.av = av;
    }

    public boolean validate(int countKill, ItemBox ib, Vector vt) {
        if (countKill == 0 || av.indexOf(ib) == -1) return true;
        if (countKill > max) {
            max = countKill;
            vt.removeAllElements();
            vt.addElement(ib);
        } else if (countKill == max)
            vt.addElement(ib);
        return true;
    }
}

/**
 * User: SMironov Date: 29.04.2004 Time: 14:25:17
 *
 * @author Mediaspectrum, Inc.
 */
public class ThirdLevel extends FirstLevel {
    private final Random r = new Random();
    protected Hashtable priority = new Hashtable(60);

    /**
     * Static priority
     * 5 0 4 3 3 4 0 5
     * 0 0 1 1 1 1 0 0
     * 4 1 4 2 2 4 1 4
     * 3 1 2 x x 2 1 3
     * 3 1 2 x x 2 1 3
     * 4 1 4 2 2 4 1 4
     * 0 0 1 1 1 1 0 0
     * 5 0 4 3 3 4 0 5
     */
    public ThirdLevel() {
        Vector vt = new Vector(4);
        //priority 5
        vt.addElement(new Coordinate(0, 0));
        vt.addElement(new Coordinate(0, 7));
        vt.addElement(new Coordinate(7, 0));
        vt.addElement(new Coordinate(7, 7));
        priority.put(new Integer(5), vt);
        //priority 4
        vt = new Vector(12);
        vt.addElement(new Coordinate(0, 2));
        vt.addElement(new Coordinate(0, 5));
        vt.addElement(new Coordinate(2, 0));
        vt.addElement(new Coordinate(2, 2));
        vt.addElement(new Coordinate(2, 5));
        vt.addElement(new Coordinate(2, 7));
        vt.addElement(new Coordinate(5, 0));
        vt.addElement(new Coordinate(5, 2));
        vt.addElement(new Coordinate(5, 5));
        vt.addElement(new Coordinate(5, 7));
        vt.addElement(new Coordinate(7, 2));
        vt.addElement(new Coordinate(7, 5));
        priority.put(new Integer(4), vt);
        //priority 2 and 3
        vt = new Vector(8);
        Vector vt2 = new Vector(8);
        for (int i = 3; i <= 4; i++) {
            //priority 3
            vt.addElement(new Coordinate(0, i));
            vt.addElement(new Coordinate(7, i));
            vt.addElement(new Coordinate(i, 0));
            vt.addElement(new Coordinate(i, 7));
            //priority 2
            vt2.addElement(new Coordinate(2, i));
            vt2.addElement(new Coordinate(5, i));
            vt2.addElement(new Coordinate(i, 2));
            vt2.addElement(new Coordinate(i, 5));
        }
        priority.put(new Integer(3), vt);
        priority.put(new Integer(2), vt2);
        //priority 1
        vt = new Vector(16);
        for (int i = 2; i <= 5; i++) {
            vt.addElement(new Coordinate(1, i));
            vt.addElement(new Coordinate(6, i));
            vt.addElement(new Coordinate(i, 1));
            vt.addElement(new Coordinate(i, 6));
        }
        priority.put(new Integer(1), vt);
        //priority 0
        vt = new Vector(12);
        vt.addElement(new Coordinate(0, 1));
        vt.addElement(new Coordinate(0, 6));
        vt.addElement(new Coordinate(1, 0));
        vt.addElement(new Coordinate(1, 1));
        vt.addElement(new Coordinate(1, 6));
        vt.addElement(new Coordinate(1, 7));
        vt.addElement(new Coordinate(6, 0));
        vt.addElement(new Coordinate(6, 1));
        vt.addElement(new Coordinate(6, 6));
        vt.addElement(new Coordinate(6, 7));
        vt.addElement(new Coordinate(7, 1));
        vt.addElement(new Coordinate(7, 6));
        priority.put(new Integer(0), vt);
    }

    public ItemBox go(ItemBoxes boxes) {
        Vector vt = LevelUtils.available(boxes, new DefaultAppender());

        if (vt.size() > 0) {
            Vector vtFinal = selectStep(vt);
            if (vtFinal.size() == 1)
                return (ItemBox) vtFinal.firstElement();
            else {
                vtFinal = LevelUtils.available(boxes, new ThirdAppender(vt));
                int rand = Math.abs(r.nextInt());
                int delim = Integer.MAX_VALUE / vtFinal.size();
                return (ItemBox) vtFinal.elementAt(rand / delim);
            }
        } else
            return null;
    }

    /**
     * Static priority
     * 5 0 4 3 3 4 0 5
     * 0 0 1 1 1 1 0 0
     * 4 1 4 2 2 4 1 4
     * 3 1 2 x x 2 1 3
     * 3 1 2 x x 2 1 3
     * 4 1 4 2 2 4 1 4
     * 0 0 1 1 1 1 0 0
     * 5 0 4 3 3 4 0 5
     *
     * @param vt source
     * @return result
     */
    protected Vector selectStep(Vector vt) {
        int i;
        Vector vtFinal = new Vector();

        for (int p = 5; p >= 0; p--) {
            Vector pvt = (Vector) priority.get(new Integer(p));
            for (i = 0; i < vt.size(); i++) {
                ItemBox ib = (ItemBox) vt.elementAt(i);
                if (pvt.indexOf(ib) != -1) vtFinal.addElement(ib);
            }
            if (vtFinal.size() > 0) return vtFinal;
        }
        //search 0 priority
        if (vtFinal.size() == 0)
            for (i = 0; i < vt.size(); i++) {
                ItemBox ib = (ItemBox) vt.elementAt(i);
                vtFinal.addElement(ib);
            }

        return vtFinal;
    }
}
