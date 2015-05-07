
import screens.MainScreen;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Date: 24.02.2004 Time: 14:45:44
 * @author Mediaspectrum, Inc.
 */
public class Opposite extends MIDlet {
	protected void startApp() throws MIDletStateChangeException {
		MainScreen.getInstance(this);
	}

	protected void pauseApp() {
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
	}
}
