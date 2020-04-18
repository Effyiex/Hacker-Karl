package cf.effectcode.game.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	public static String getCWD() {
		return System.getProperty("user.dir");
	}
	
	public static class SpriteNotFoundException extends Exception {
		
		private String givenLoc;
		
		public SpriteNotFoundException(String givenLoc) {
			this.givenLoc = givenLoc;
		}
		
		public void printStackTrace() {
			System.out.println("Couldn't find a Sprite (" + givenLoc + ")");
		}
		
	}
	
	private BufferedImage img;
	
	public Sprite(String loc) throws SpriteNotFoundException {
		File file = new File(getCWD() + "\\assets\\" + loc + ".png");
		boolean noex = file.exists() && file.isFile();
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			noex = false;
		}
		if(!noex) throw new SpriteNotFoundException(loc);
	}
	
	public BufferedImage get() {
		return img;
	}

}
