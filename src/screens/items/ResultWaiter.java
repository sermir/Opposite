package screens.items;

import screens.MainScreen;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Alert;

/**
 * User: smironov
 * Date: 22.01.2007
 * Time: 16:51:49
 * <p/>
 * $Id$
 *
 * @author Mediaspectrum, Inc.
 */
public class ResultWaiter extends Thread {
	private final long time;
	private final Display dsp;
	private final Alert al;
	private final Cursor c;

	public ResultWaiter(long time, Display dsp, Alert al, Cursor c) {
		this.time = time;
		this.dsp = dsp;
		this.al = al;
		this.c = c;
		start();
	}

	public void run() {
		while (System.currentTimeMillis() - time < 4000) {
			try {
				Thread.sleep(100);
				c.isShow = false;
			} catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		dsp.setCurrent(al, MainScreen.getInstance(null));
	}
}
