package screens;

import levels.FirstLevel;
import levels.SecondLevel;
import levels.ThirdLevel;
import levels.FourthLevel;
import screens.items.*;
import util.*;

import javax.microedition.lcdui.*;
import java.io.IOException;

/**
 * Date: 02.03.2004 Time: 17:54:45
 * @author Mediaspectrum, Inc.
 */
public class Board extends Canvas {
	private final Display dsp;
	private final int cellW;
	private final int cellH;
	private final ItemBoxes iBoxes = new ItemBoxes();
	private final Cursor c;
	private Image[] images;
	public int imageNumber;
	public boolean isHourglas;
	public boolean isFinish;

	public Board(Display disp) {
		dsp = disp;
		cellH = Math.min(getWidth() / 10, getHeight() / 8);
		cellW = cellH;

		for (int j = 0; j < 8; j++) {
			for (int k = 0; k < 8; k++) {
				if (k == 3 && (j == 3 || j == 4))
					iBoxes.boxes[j][k] = new ItemBox(j, k, ItemBox.WHITE_STATE, cellW, cellH);
				else if (k == 4 && (j == 3 || j == 4))
					iBoxes.boxes[j][k] = new ItemBox(j, k, ItemBox.BLACK_STATE, cellW, cellH);
				else
					iBoxes.boxes[j][k] = new ItemBox(j, k, ItemBox.ZERO_STATE, cellW, cellH);
			}
		}
		c = new Cursor(cellW, cellH);
		new MainReturner(this);

		try {
			images = new Image[]{
				Image.createImage("/hourglas0.png"),
				Image.createImage("/hourglas1.png"),
				Image.createImage("/hourglas2.png"),
				Image.createImage("/hourglas3.png"),
				Image.createImage("/hourglas4.png"),
				Image.createImage("/hourglas5.png"),
				Image.createImage("/hourglas6.png"),
				Image.createImage("/hourglas7.png")
			};
		} catch (IOException e) {
			e.printStackTrace();
		}

		dsp.setCurrent(this);
	}

	protected void paint(Graphics g) {
		if (isHourglas) {
			drawHourglas(g);
			return;
		}

		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.translate(0, 0);
		g.setColor(0);
		g.drawRect(0, 0, cellW * 8, getHeight() - 1);

		for (int j = 0; j < 8; j++) {
			for (int k = 0; k < 8; k++) {
				iBoxes.boxes[j][k].paint(g);
			}
		}

		c.paint(g);

		//set black and white number
		int lenght = Math.min(cellW - 4, cellH - 4);
		int px = getWidth() - 30;
		int pyB = 10;
		int pyW = getHeight() - 15;
		g.translate(0, 0);
		//draw black
		g.fillArc(px, pyB, lenght, lenght, 0, 360);
		g.drawString(normalizeString(iBoxes.blackNumber()), px + 20, pyB + 11, Graphics.BOTTOM | Graphics.HCENTER);
		//draw white
		g.drawArc(px, pyW, lenght, lenght, 0, 360);
		g.drawString(normalizeString(iBoxes.whiteNumber()), px + 20, pyW + 12, Graphics.BOTTOM | Graphics.HCENTER);
	}

	private void drawHourglas(Graphics g) {
		int px = getWidth() - 15;
		g.drawImage(images[imageNumber], px, getHeight() / 2 + 15, Graphics.BOTTOM | Graphics.HCENTER);
	}

	private String normalizeString(int count) {
		StringBuffer sb = new StringBuffer();
		return count < 10 ? sb.append("0").append(count).toString() : sb.append(count).toString();
	}

	protected void keyPressed(int keyCode) {
		if (isHourglas) return;

		int game = getGameAction(keyCode);
		int x = c.x;
		int y = c.y;
		switch (game) {
		case Canvas.UP:
			y--;
			break;
		case Canvas.DOWN:
			y++;
			break;
		case Canvas.RIGHT:
			x++;
			break;
		case Canvas.LEFT:
			x--;
			break;
		case Canvas.FIRE:
//			try {
				DrawHourglasses.startHorglasses(this);
				c.isShow = false;
				pressFire(true);
			/*} finally {
//				DrawHourglasses.stopHorglasses();
//				c.isShow = true;
			}*/
			return;
		}

		switch (keyCode) {
		case Canvas.KEY_NUM1://left and up
			x--;
			y--;
			break;
		case Canvas.KEY_NUM3://right and up
			x++;
			y--;
			break;
		case Canvas.KEY_NUM7://left and down
			x--;
			y++;
			break;
		case Canvas.KEY_NUM9://right and down
			x++;
			y++;
			break;
		}

		if (x < 0 || x > 7 || y < 0 || y > 7 || iBoxes.boxes[x][y].curState == ItemBox.ZERO_STATE) {
			c.x = getCoordinate(x);
			c.y = getCoordinate(y);
			repaint();
		} else {
			c.x = getCoordinate(x);
			c.y = getCoordinate(y);
			keyPressed(keyCode);
		}
	}

	private int getCoordinate(int k) {
		k = k < 0 ? 7 : k;
		k = k > 7 ? 0 : k;
		return k;
	}

	private void pressFire(boolean isUser) {
		if (isFinish) return;
		long time = System.currentTimeMillis();
		int x = c.x;
		int y = c.y;
		if (iBoxes.boxes[x][y].curState != ItemBox.ZERO_STATE) {
			DrawHourglasses.stopHorglasses();
			c.isShow = true;
			return;
		}

		if (FireAhead.fire(iBoxes, x, y, isUser)) {
			iBoxes.boxes[x][y].curState = isUser ? ItemBox.BLACK_STATE : ItemBox.WHITE_STATE;
			if (isUser) {
				//repaint user step
				repaint(0, 0, cellW * 8, getHeight() - 1);
				serviceRepaints();

				phoneFire();
				while (UserHelper.help(iBoxes) == null && phoneFire()) {
				}

				new Waiter(time, c);
			}


			ItemBox ib = UserHelper.help(iBoxes);
			if (ib == null && !phoneFire()) {
				DrawHourglasses.startHorglasses(this);
				StringBuffer sb = new StringBuffer();
				sb.append("\nчёрных = ").append(iBoxes.blackNumber()).append("\nбелых = ").append(iBoxes.whiteNumber());
				if (iBoxes.whiteNumber() < iBoxes.blackNumber())
					sb.insert(0, "Ты победил!!!");
				else if (iBoxes.whiteNumber() > iBoxes.blackNumber())
					sb.insert(0, "Ты проиграл!!!");
				else
					sb.insert(0, "Ничья!!!");
				c.isShow = false;
				Alert al = new Alert("Результат", sb.toString(), null, AlertType.INFO);
				al.setTimeout(Alert.FOREVER);
				isFinish = true;
				repaint();
				new ResultWaiter(time, dsp, al, c);
			} else if (ib != null) {
				c.x = ib.x;
				c.y = ib.y;
				repaint();
			}
		} else {
			DrawHourglasses.stopHorglasses();
			c.isShow = true;
		}
	}

	private boolean phoneFire() {
		LevelStore ls = new LevelStore();
		PhoneStep ps;
		switch (ls.getLevel()) {
		case 2:
			ps = new SecondLevel();
			break;
		case 3:
			ps = new ThirdLevel();
			break;
		case 4:
			ps = new FourthLevel();
			break;
		default:
			ps = new FirstLevel();
			break;
		}
		ItemBox ib = ps.go(iBoxes);
		if (ib != null) {
			c.x = ib.x;
			c.y = ib.y;
			pressFire(false);
			return true;
		}
		return false;
	}
}
