package cf.effectcode.game.client;

import cf.effectcode.game.Game;
import cf.effectcode.game.render.Canvas;

public class Updater extends Thread {
	
	public static long delay = 30L;
	
	public static boolean running = true;	
	
	public static int fpsCounter = 0, fps;
	
	private static Updater instance = new Updater();
	
	public Updater() {
		super(new Runnable() {
			public void run() {
				while(running) {
					try {
						Thread.sleep(delay);
						fpsCounter++;
						Canvas.getCanvas().repaint();
						if(Game.getInstance().isSetupFinished() && !Game.getInstance().isVisible()) {
							Game.getInstance().getGui().dispose();
							System.exit(0);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		});
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000L);
						fps = fpsCounter;
						fpsCounter = 0;
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}).start();
	}
	
	public static void initUpdater() {
		instance.start();
	}
	
	public Updater getUpdater() {
		return instance;
	}
	
}
