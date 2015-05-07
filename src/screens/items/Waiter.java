package screens.items;

/**
 * User: SMironov Date: 29.04.2004 Time: 17:39:57
 * @author Mediaspectrum, Inc.
 */
public class Waiter extends Thread {
	private final long time;
	private final Cursor c;
	public Waiter(long time, Cursor c) {
		this.time = time;
		this.c = c;
		start();
	}

	public void run() {
		while (System.currentTimeMillis() - time < 2000) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		DrawHourglasses.stopHorglasses();
		c.isShow = true;
	}
}
