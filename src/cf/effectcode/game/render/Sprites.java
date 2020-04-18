package cf.effectcode.game.render;

import cf.effectcode.game.render.Sprite.SpriteNotFoundException;

public final class Sprites {

	public static Sprite icon, monitor, osPB, usbStick, skyBg, smartphone, winicon, file, mouse;
	
	public Sprites() {
		throw new NullPointerException();
	}
	
	public static void loadAllSprites() {
		try {
			icon = new Sprite("icon");
			monitor = new Sprite("computer");
			osPB = new Sprite("pb_os");
			usbStick = new Sprite("usb_stick");
			skyBg = new Sprite("sky");
			smartphone = new Sprite("smartphone");
			winicon = new Sprite("winico");
			file = new Sprite("file");
			mouse = new Sprite("mouse");
		} catch (SpriteNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
}
