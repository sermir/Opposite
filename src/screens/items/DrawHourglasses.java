package screens.items;

import screens.Board;

/**
 * User: SMironov Date: 05.04.2004 Time: 16:51:09
 * @author Mediaspectrum, Inc.
 */
public class DrawHourglasses extends Thread {
	private final Board b;
	private boolean isGo = true;

	private static DrawHourglasses dh;

	private DrawHourglasses(Board b) {
		this.b = b;
	}

	public static synchronized void startHorglasses(Board b) {
		if (dh == null || !dh.isAlive()) {
			dh = new DrawHourglasses(b);
			dh.start();
		}
	}

	public static void stopHorglasses() {
		if (dh != null) dh.isGo = false;
	}

	public void run() {
		while (isGo) {
			b.isHourglas = true;
			b.repaint(b.getWidth() - 30, b.getHeight() / 2 - 15, b.getWidth(), b.getHeight() / 2 + 15);
			b.serviceRepaints();
			b.imageNumber++;
			if (b.imageNumber > 7) b.imageNumber = 0;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		b.isHourglas = false;
		b.repaint();
	}
}
