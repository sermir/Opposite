package screens;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * User: SMironov Date: 26.03.2004 Time: 11:42:20
 * @author Mediaspectrum, Inc.
 */
public class MainScreen extends List {
	private final MIDlet midlet;
	private static MainScreen ms;

	private static final String NEW = "Новая игра";
	public static final String RULES = "Правила";
	private static final String LEVEL = "Настройки";

    private MainScreen(MIDlet mid) {
		super("Opposite", Choice.IMPLICIT);
		this.midlet = mid;

		append(NEW, null);
		append(LEVEL, null);
		append(RULES, null);
		Command cmdExit = new Command("Выход", Command.EXIT, 1);
		Command cmdSelect = new Command("Выбрать", Command.OK, 1);
		addCommand(cmdSelect);
		addCommand(cmdExit);
		setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				switch (c.getCommandType()) {
				case Command.EXIT:
					midlet.notifyDestroyed();
					break;
				case Command.OK:
					Display disp = Display.getDisplay(midlet);
					if (getString(getSelectedIndex()).equals(NEW))
						new Board(disp);
					if (getString(getSelectedIndex()).equals(RULES))
						new RulesScreen(disp);
					if (getString(getSelectedIndex()).equals(LEVEL))
						new LevelScreen(disp);
				}
			}
		});
	}

	public static MainScreen getInstance(MIDlet mid) {
		if (ms == null) ms = new MainScreen(mid);
		if (!ms.isShown()) {
			Display disp = Display.getDisplay(ms.midlet);
			disp.setCurrent(ms);
		}
		return ms;
	}
}
