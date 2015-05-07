package screens;

import util.LevelStore;
import util.MainReturner;

import javax.microedition.lcdui.*;

/**
 * User: SMironov Date: 31.03.2004 Time: 17:34:42
 *
 * @author Mediaspectrum, Inc.
 */
class LevelScreen extends Canvas {
	private final LevelStore ls;
	private int level;
	public static final int LEVEL_NUMBER = 4;

	public LevelScreen(Display disp) {
		new MainReturner(this);
		ls = new LevelStore();
		level = ls.getLevel();
		addCommand(new Command("", Command.OK, 1));
		setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (c.getCommandType() == Command.OK) {
					ls.setNewLevel(level);
					MainScreen.getInstance(null);
				}
				if (c.getCommandType() == Command.BACK) MainScreen.getInstance(null);
			}
		});
		disp.setCurrent(this);
	}

	protected void paint(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.translate(0, 0);
		g.setColor(0);

		int gapWidht = (getWidth() - 50) / (LEVEL_NUMBER + 1);
		int gapHeight = getHeight() / (LEVEL_NUMBER + 1);
		for (int i = 0; i < LEVEL_NUMBER; i++) {
			if (i < level)
				g.fillRect(gapWidht * (1 + i) + 10 * i, gapHeight * (LEVEL_NUMBER - i), 10, getHeight());
			else
				g.drawRect(gapWidht * (1 + i) + 10 * i, gapHeight * (LEVEL_NUMBER - i), 10, getHeight());
		}
	}

	protected void keyPressed(int keyCode) {
		int game = getGameAction(keyCode);
		switch (game) {
			case Canvas.RIGHT:
				level = level < LEVEL_NUMBER ? level + 1 : level;
				break;
			case Canvas.LEFT:
				level = level > 1 ? level - 1 : level;
				break;
		}
		repaint();
	}
}
