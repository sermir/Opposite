package screens.items;

/**
 * User: smironov
 * Date: 18.01.2007
 * Time: 12:45:31
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public class Coordinate {
    public int x;
    public int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate c = (Coordinate) o;
        return x == c.x && y == c.y;
    }

    public String toString() {
        return new StringBuffer().append("Coordinate{" + "x=").append(x).
				append(", y=").append(y).append('}').toString();
    }
}
