package cf.effectcode.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import cf.effectcode.game.client.Controller;
import cf.effectcode.game.client.FileHandler;
import cf.effectcode.game.client.Updater;
import cf.effectcode.game.gui.GuiIngame;
import cf.effectcode.game.gui.GuiMainMenu;
import cf.effectcode.game.render.Canvas;
import cf.effectcode.game.render.Component;
import cf.effectcode.game.render.Gui;
import cf.effectcode.game.render.Sprites;

public class Game extends JFrame {

	public static final String gameName = "Hacker Karl";
	public static final String gameBuild = "b0.2";
	
	public static int highscore, score = 0;
	
	public static Font fontBase = new Font("Arial", Font.PLAIN, 24);
	
	public static Font font() {
		return new Font(fontBase.getFontName(), fontBase.getStyle(), getInstance().getHeight() / 20);
	}
	
	public static int WIDTH = 800, HEIGHT = 500;
	
	private static Game instance = new Game();
	
	private Gui gui;
	
	private boolean setupFinished = false;
	
	public void setup() {
		Canvas.initCanvas();
		Updater.initUpdater();
		Sprites.loadAllSprites();
		Controller.addToFrame(this);
		GuiIngame.setLevel((int)Math.sqrt(Integer.parseInt(FileHandler.readTextFile("save")[0])) / 1337);
		setIconImage(Sprites.icon.get());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setTitle(toString());
		setLocation(center());
		setVisible(true);
		displayGui(new GuiMainMenu());
		onResize();
		setupFinished = true;
	}
	
	private int resizeWidth = WIDTH, resizeHeight = HEIGHT;
	
	public void onResize() {
		gui.reload();
		setTitle(toString() + " [" + getWidth() + "x" + getHeight() + "]");
	}
	
	public void draw(Graphics g) {
		g.setFont(font());
		if(gui != null) {
			if(getWidth() != resizeWidth || getHeight() != resizeHeight) {
				onResize();
				resizeWidth = getWidth();
				resizeHeight = getHeight();
			}
			Controller.update();
			try {
				Point p = getMousePosition();
				Component.mouseX = p.x;
				Component.mouseY = p.y - (isUndecorated() ? 0 : 30);
			} catch (NullPointerException e) {
				Component.mouseX = 0;
				Component.mouseY = 0;
			}
			gui.draw(g);
		}else{
			g.setColor(new Color(0, 255, 150));
			g.setFont(new Font(font().getFontName(), font().getStyle(), 30));
			g.drawString("Loading...", WIDTH / 2 - 30, HEIGHT / 2);
		}
	}
	
	public Point center() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Point((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
	}
	
	public String toString() {
		return gameName + " " + gameBuild;
	}
	
	public Gui getGui() {
		return gui;
	}
	
	public void displayGui(Gui gui) {
		if(this.gui != null) {
			this.gui.dispose();
		}
		this.gui = gui;
	}
	
	public static Game getInstance() {
		return instance;
	}

	public boolean isSetupFinished() {
		return setupFinished;
	}
	
}
