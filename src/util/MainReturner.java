package util;

import screens.MainScreen;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

/**
 * User: SMironov Date: 01.04.2004 Time: 11:11:46
 * @author Mediaspectrum, Inc.
 */
public class MainReturner {
	public MainReturner(Displayable d) {
		d.addCommand(new Command("", Command.BACK, 1));
		d.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (c.getCommandType() == Command.BACK) MainScreen.getInstance(null);
			}
		});
	}
}
