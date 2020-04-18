package cf.effectcode.game.render;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.Updater;

public class Canvas extends JPanel {
	
	private static Canvas canvas = new Canvas();
	
	private Color bg = new Color(0, 180, 250);
	
	public void setBackground(Color bg) {
		this.bg = bg;
	}
	
	public static Canvas getCanvas() {
		return canvas;
	}
	
	public static void initCanvas() {
		Game.getInstance().add(canvas);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.setBackground(bg);
		Game.getInstance().draw(g);
		g.setColor(new Color(255, 255, 255));
		g.setFont(Game.font());
		String fpsDisplay = "FPS: " + Updater.fps;
		g.drawString(fpsDisplay, Game.getInstance().getWidth() - 40 - Game.font().getSize() * fpsDisplay.length() / 2, 10 + Game.font().getSize());
	}
	
}
