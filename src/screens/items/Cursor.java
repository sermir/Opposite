package screens.items;

import javax.microedition.lcdui.Graphics;

/**
 * The class implements cursor on board in the game Date: 03.03.2004 Time: 16:30:01
 *
 * @author Serguey Mironov
 */
public class Cursor extends Coordinate {
    private final int cellW;
    private final int cellH;
    public boolean isShow = true;

    public Cursor(int cellW, int cellH) {
        super(2, 2);
        this.cellW = cellW;
        this.cellH = cellH;
    }

    public void paint(Graphics g) {
        int px = x * cellW;
        int py = y * cellH;
        if (isShow) g.fillRect(px, py, cellW, cellH);
    }
}
